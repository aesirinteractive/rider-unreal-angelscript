package com.aesirinteractive.angelscript

import java.io.IOException
import java.io.OutputStream
import java.net.ConnectException
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.Charset

enum class MessageType(val id: Int) {
    Diagnostics(0),
    RequestDebugDatabase(1),
    DebugDatabase(2),
    StartDebugging(3),
    StopDebugging(4),
    Pause(5),
    Continue(6),
    RequestCallStack(7),
    CallStack(8),
    ClearBreakpoints(9),
    SetBreakpoint(10),
    HasStopped(11),
    HasContinued(12),
    StepOver(13),
    StepIn(14),
    StepOut(15),
    EngineBreak(16),
    RequestVariables(17),
    Variables(18),
    RequestEvaluate(19),
    Evaluate(20),
    GoToDefinition(21),
    BreakOptions(22),
    RequestBreakFilters(23),
    BreakFilters(24),
    Disconnect(25),
    DebugDatabaseFinished(26),
    AssetDatabaseInit(27),
    AssetDatabase(28),
    AssetDatabaseFinished(29),
    FindAssets(30),
    DebugDatabaseSettings(31),
    PingAlive(32),
    DebugServerVersion(33),
    CreateBlueprint(34),
    ReplaceAssetDefinition(35),
    SetDataBreakpoints(36),
    ClearDataBreakpoints(37),
    ScriptRecompiled(38),
    StopPIE(39);

    companion object {
        fun fromId(id: Int): MessageType? = entries.find { it.id == id }
    }
}

data class BreakFilter(val name: String, val title: String)

data class CallStackFrame(val name: String, val sourcePath: String, val line: Int, val moduleName: String)

data class DebugVariable(
    val name: String,
    val value: String,
    val type: String,
    val hasMembers: Boolean
)

class IncomingMessage(val type: Int, private val data: ByteArray) {
    private var cursor = 0

    fun readInt(): Int {
        val buf = ByteBuffer.wrap(data, cursor, 4).order(ByteOrder.LITTLE_ENDIAN)
        cursor += 4
        return buf.int
    }

    fun readByte(): Int {
        val v = data[cursor].toInt() and 0xFF
        cursor += 1
        return v
    }

    fun readBool(): Boolean = readInt() != 0

    fun readString(): String {
        val n = readInt()
        val ucs2 = n < 0
        val count = if (ucs2) -n else n
        if (count == 0) return ""

        val str = if (ucs2) {
            val byteCount = count * 2
            val s = String(data, cursor, byteCount, Charset.forName("UTF-16LE"))
            cursor += byteCount
            s
        } else {
            val s = String(data, cursor, count, Charsets.UTF_8)
            cursor += count
            s
        }
        // Strip null terminator
        return if (str.isNotEmpty() && str.last() == '\u0000') str.dropLast(1) else str
    }

    fun hasMore(): Boolean = cursor < data.size
}

class UnrealDebugClient(val host: String = "127.0.0.1", val port: Int = 27099) {

    // Callbacks — set by AngelscriptProcess before connecting
    var onStopped: ((reason: String, description: String, text: String) -> Unit)? = null
    var onContinued: (() -> Unit)? = null
    var onCallStack: ((frames: List<CallStackFrame>) -> Unit)? = null
    var onVariables: ((variables: List<DebugVariable>) -> Unit)? = null
    var onServerVersion: ((version: Int) -> Unit)? = null
    var onBreakFilters: ((filters: List<BreakFilter>) -> Unit)? = null
    var onEvaluate: ((name: String, value: String, type: String, hasMembers: Boolean) -> Unit)? = null
    var onClosed: (() -> Unit)? = null
    var onBreakpointAdjusted: ((id: Int, path: String, adjustedLine: Int) -> Unit)? = null
    var onScriptRecompiled: (() -> Unit)? = null
    var onMessageLog: ((direction: String, type: String) -> Unit)? = null

    @Volatile var serverVersion: Int = 0
    @Volatile var isConnected: Boolean = false

    private var socket: Socket? = null
    private var outputStream: OutputStream? = null
    private var readerThread: Thread? = null
    private var connectThread: Thread? = null

    private val pendingBuffer = mutableListOf<Byte>()
    private val sendLock = Any()

    /**
     * Connect asynchronously with retry. Retries every [retryDelayMs] ms up to [maxRetries] times.
     * Pass [maxRetries] = -1 for unlimited retries (never calls [onFailed]).
     * Calls [onConnected] when the connection succeeds.
     */
    fun connectWithRetry(
        maxRetries: Int = 30,
        retryDelayMs: Long = 2000L,
        onConnected: (() -> Unit)? = null,
        onRetry: ((attempt: Int) -> Unit)? = null,
        onFailed: (() -> Unit)? = null
    ) {
        val unlimited = maxRetries < 0
        connectThread = Thread({
            var attempt = 0
            while (true) {
                attempt++
                if (Thread.currentThread().isInterrupted) return@Thread
                try {
                    val s = Socket(host, port)
                    socket = s
                    outputStream = s.getOutputStream()
                    isConnected = true
                    startReaderThread(s)
                    onConnected?.invoke()
                    return@Thread
                } catch (_: ConnectException) {
                    onRetry?.invoke(attempt)
                    if (!unlimited && attempt >= maxRetries) break
                    try { Thread.sleep(retryDelayMs) } catch (_: InterruptedException) { return@Thread }
                } catch (_: InterruptedException) {
                    return@Thread
                } catch (_: IOException) {
                    onRetry?.invoke(attempt)
                    if (!unlimited && attempt >= maxRetries) break
                    try { Thread.sleep(retryDelayMs) } catch (_: InterruptedException) { return@Thread }
                }
            }
            onFailed?.invoke()
        }, "AngelScript-Connect").also { it.isDaemon = true; it.start() }
    }

    fun disconnect() {
        isConnected = false
        connectThread?.interrupt()
        readerThread?.interrupt()
        try {
            socket?.close()
        } catch (_: IOException) {}
        socket = null
        outputStream = null
        synchronized(pendingBuffer) { pendingBuffer.clear() }
    }

    private fun startReaderThread(s: Socket) {
        readerThread = Thread({
            val inputStream = s.getInputStream()
            val buf = ByteArray(4096)
            try {
                while (!Thread.currentThread().isInterrupted) {
                    val read = inputStream.read(buf)
                    if (read == -1) break
                    processIncoming(buf, read)
                }
            } catch (_: IOException) {
                // socket closed
            } finally {
                isConnected = false
                onClosed?.invoke()
            }
        }, "AngelScript-Reader").also { it.isDaemon = true; it.start() }
    }

    private fun processIncoming(data: ByteArray, length: Int) {
        synchronized(pendingBuffer) {
            for (i in 0 until length) pendingBuffer.add(data[i])

            while (pendingBuffer.size >= 5) {
                val msgLen = readUInt32LE(pendingBuffer, 0).toInt()
                // msgLen is the payload after the 4-byte length field (includes the 1 type byte)
                val totalNeeded = 5 + msgLen
                if (pendingBuffer.size < totalNeeded) break

                val typeId = pendingBuffer[4].toInt() and 0xFF
                val payload = ByteArray(msgLen) { pendingBuffer[5 + it] }

                repeat(totalNeeded) { pendingBuffer.removeAt(0) }

                val msg = IncomingMessage(typeId, payload)
                dispatchMessage(msg)
            }
        }
    }

    private fun readUInt32LE(buf: MutableList<Byte>, offset: Int): Long {
        return ((buf[offset].toLong() and 0xFF) or
                ((buf[offset + 1].toLong() and 0xFF) shl 8) or
                ((buf[offset + 2].toLong() and 0xFF) shl 16) or
                ((buf[offset + 3].toLong() and 0xFF) shl 24))
    }

    private fun dispatchMessage(msg: IncomingMessage) {
        val msgType = MessageType.fromId(msg.type)
        val typeName = msgType?.name ?: "Unknown(${msg.type})"
        if (msgType != MessageType.SetBreakpoint) onMessageLog?.invoke("<--", typeName)
        when (msgType) {
            MessageType.HasStopped -> {
                val reason = msg.readString()
                val description = msg.readString()
                val text = msg.readString()
                onStopped?.invoke(reason, description, text)
            }
            MessageType.HasContinued -> {
                onContinued?.invoke()
            }
            MessageType.CallStack -> {
                val count = msg.readInt()
                val frames = mutableListOf<CallStackFrame>()
                for (i in 0 until count) {
                    val name = msg.readString()
                    val path = msg.readString()
                    val line = msg.readInt()
                    val module = if (serverVersion > 0) msg.readString() else ""
                    frames.add(CallStackFrame(name, path, line, module))
                }
                onCallStack?.invoke(frames)
            }
            MessageType.Variables -> {
                val count = msg.readInt()
                val vars = mutableListOf<DebugVariable>()
                for (i in 0 until count) {
                    val name = msg.readString()
                    val value = msg.readString()
                    val type = msg.readString()
                    val hasMembers = msg.readBool()
                    if (serverVersion >= 2) {
                        // Skip 8-byte address + 1-byte size (not used in UI yet)
                        repeat(9) { msg.readByte() }
                    }
                    vars.add(DebugVariable(name, value, type, hasMembers))
                }
                onVariables?.invoke(vars)
            }
            MessageType.DebugServerVersion -> {
                serverVersion = msg.readInt()
                onServerVersion?.invoke(serverVersion)
            }
            MessageType.BreakFilters -> {
                val count = msg.readInt()
                val filters = mutableListOf<BreakFilter>()
                for (i in 0 until count) {
                    val name = msg.readString()
                    val title = msg.readString()
                    filters.add(BreakFilter(name, title))
                }
                onBreakFilters?.invoke(filters)
            }
            MessageType.Evaluate -> {
                val name = msg.readString()
                val value = msg.readString()
                val type = msg.readString()
                val hasMembers = msg.readBool()
                onEvaluate?.invoke(name, value, type, hasMembers)
            }
            MessageType.SetBreakpoint -> {
                val path = msg.readString()
                val line = msg.readInt()
                val id   = msg.readInt()
                onMessageLog?.invoke("<--", "SetBreakpoint id=$id path=$path line=$line")
                onBreakpointAdjusted?.invoke(id, path, line)
            }
            MessageType.ScriptRecompiled -> {
                onScriptRecompiled?.invoke()
            }
            else -> { /* ignore unknown/unhandled messages */ }
        }
    }

    // ── Send helpers ──────────────────────────────────────────────────────────

    private fun writeInt(value: Int): ByteArray {
        val buf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
        buf.putInt(value)
        return buf.array()
    }

    private fun writeString(str: String): ByteArray {
        val strBytes = (str + "\u0000").toByteArray(Charsets.ISO_8859_1)
        val lenBuf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
        lenBuf.putInt(strBytes.size)
        return lenBuf.array() + strBytes
    }

    private fun buildSimpleMessage(type: MessageType): ByteArray {
        val buf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN)
        buf.putInt(1) // length = 1 (just the type byte)
        buf.put(type.id.toByte())
        return buf.array()
    }

    private fun buildMessage(type: MessageType, payload: ByteArray): ByteArray {
        val length = 1 + payload.size // type byte + payload
        val header = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN)
        header.putInt(length)
        header.put(type.id.toByte())
        return header.array() + payload
    }

    private fun send(data: ByteArray, logDetail: String? = null) {
        if (data.size >= 5) {
            val typeId = data[4].toInt() and 0xFF
            val typeName = MessageType.fromId(typeId)?.name ?: "Unknown($typeId)"
            onMessageLog?.invoke("-->", if (logDetail != null) "$typeName $logDetail" else typeName)
        }
        synchronized(sendLock) {
            try {
                outputStream?.write(data)
                outputStream?.flush()
            } catch (_: IOException) {}
        }
    }

    // ── Public send methods ───────────────────────────────────────────────────

    fun sendStartDebugging(version: Int = 2) {
        send(buildMessage(MessageType.StartDebugging, writeInt(version)))
    }

    fun sendStopDebugging() = send(buildSimpleMessage(MessageType.StopDebugging))

    fun sendDisconnect() = send(buildSimpleMessage(MessageType.Disconnect))

    fun sendContinue() = send(buildSimpleMessage(MessageType.Continue))

    fun sendPause() = send(buildSimpleMessage(MessageType.Pause))

    fun sendStepOver() = send(buildSimpleMessage(MessageType.StepOver))

    fun sendStepIn() = send(buildSimpleMessage(MessageType.StepIn))

    fun sendStepOut() = send(buildSimpleMessage(MessageType.StepOut))

    fun sendRequestCallStack() = send(buildSimpleMessage(MessageType.RequestCallStack))

    fun sendRequestBreakFilters() = send(buildSimpleMessage(MessageType.RequestBreakFilters))

    fun sendRequestVariables(path: String) {
        send(buildMessage(MessageType.RequestVariables, writeString(path)))
    }

    fun sendRequestEvaluate(expression: String, frameId: Int) {
        val payload = writeString(expression) + writeInt(frameId)
        send(buildMessage(MessageType.RequestEvaluate, payload))
    }

    fun sendSetBreakpoint(id: Int, path: String, line: Int, moduleName: String) {
        val payload = writeString(path) + writeInt(line) + writeInt(id) + writeString(moduleName)
        send(buildMessage(MessageType.SetBreakpoint, payload), "id=$id path=$path line=$line")
    }

    fun sendClearBreakpoints(path: String, moduleName: String) {
        val payload = writeString(path) + writeString(moduleName)
        send(buildMessage(MessageType.ClearBreakpoints, payload), "path=$path")
    }

    fun sendBreakOptions(filters: List<String>) {
        var payload = writeInt(filters.size)
        for (f in filters) payload += writeString(f)
        send(buildMessage(MessageType.BreakOptions, payload))
    }

    fun sendStopPIE() = send(buildSimpleMessage(MessageType.StopPIE))
}
