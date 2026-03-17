package com.aesirinteractive.angelscript

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

enum class LspPathKind { Bundled, VsCode, Custom, CommandLine }
enum class ClangFormatPathKind { Bundled, Rider, VisualStudio, Custom }

@Service(Service.Level.APP)
@State(name = "AngelscriptSettings", storages = [Storage("angelscript.xml")])
class AngelscriptSettings : PersistentStateComponent<AngelscriptSettings.State> {

    data class State(
        var nodePath: String = "node",
        var lspPath: String = "",
        var lspPathKind: LspPathKind = LspPathKind.Bundled,
        var customCommandLine: String = "",
        var fileExtensions: String = "as",
        var debugHost: String = "127.0.0.1",
        var debugPort: Int = 27099,
        var autoReconnectDebugger: Boolean = true,
        var debugReconnectDelayMs: Long = 10000L,
        var clangFormatPathKind: ClangFormatPathKind = ClangFormatPathKind.Bundled,
        var clangFormatPath: String = "",
        var clangFormatFile: String = ".clang-format-angelscript",
        var logDebugMessages: Boolean = false
    )

    private var state = State()

    override fun getState() = state

    override fun loadState(state: State) {
        this.state = state
    }

    var nodePath: String
        get() = state.nodePath
        set(value) { state.nodePath = value }

    var lspPath: String
        get() = state.lspPath
        set(value) { state.lspPath = value }

    var lspPathKind: LspPathKind
        get() = state.lspPathKind
        set(value) { state.lspPathKind = value }

    var customCommandLine: String
        get() = state.customCommandLine
        set(value) { state.customCommandLine = value }

    var fileExtensions: String
        get() = state.fileExtensions
        set(value) { state.fileExtensions = value }

    var debugHost: String
        get() = state.debugHost
        set(value) { state.debugHost = value }

    var debugPort: Int
        get() = state.debugPort
        set(value) { state.debugPort = value }

    var autoReconnectDebugger: Boolean
        get() = state.autoReconnectDebugger
        set(value) { state.autoReconnectDebugger = value }

    var debugReconnectDelayMs: Long
        get() = state.debugReconnectDelayMs
        set(value) { state.debugReconnectDelayMs = value }

    var clangFormatPathKind: ClangFormatPathKind
        get() = state.clangFormatPathKind
        set(value) { state.clangFormatPathKind = value }

    var clangFormatPath: String
        get() = state.clangFormatPath
        set(value) { state.clangFormatPath = value }

    var clangFormatFile: String
        get() = state.clangFormatFile
        set(value) { state.clangFormatFile = value }

    var logDebugMessages: Boolean
        get() = state.logDebugMessages
        set(value) { state.logDebugMessages = value }

    fun parsedFileExtensions(): Set<String> =
        fileExtensions.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toSet()

    companion object {
        fun getInstance(): AngelscriptSettings =
            ApplicationManager.getApplication().getService(AngelscriptSettings::class.java)
    }
}
