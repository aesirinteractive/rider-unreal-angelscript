package com.aesirinteractive.angelscript

import com.intellij.execution.ExecutionManager
import com.intellij.execution.ExecutionResult
import com.intellij.execution.Executor
import com.intellij.execution.RunProfileStarter
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ProgramRunner
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.ui.ColoredTextContainer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.xdebugger.DefaultDebugProcessHandler
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebugProcessStarter
import com.intellij.xdebugger.XDebugSession
import com.intellij.xdebugger.XDebuggerManager
import com.intellij.xdebugger.XExpression
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.breakpoints.XBreakpointHandler
import com.intellij.xdebugger.breakpoints.XBreakpointProperties
import com.intellij.xdebugger.breakpoints.XLineBreakpoint
import com.intellij.xdebugger.breakpoints.XLineBreakpointType
import com.intellij.xdebugger.evaluation.EvaluationMode
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator
import com.intellij.xdebugger.frame.XCompositeNode
import com.intellij.xdebugger.frame.XExecutionStack
import com.intellij.xdebugger.frame.XExecutionStack.XStackFrameContainer
import com.intellij.xdebugger.frame.XNamedValue
import com.intellij.xdebugger.frame.XStackFrame
import com.intellij.xdebugger.frame.XSuspendContext
import com.intellij.xdebugger.frame.XValueChildrenList
import com.intellij.xdebugger.frame.XValueGroup
import com.intellij.xdebugger.frame.XValueNode
import com.intellij.xdebugger.frame.XValuePlace
import com.intellij.xdebugger.frame.presentation.XStringValuePresentation
import com.intellij.xdebugger.impl.XSourcePositionImpl
import com.intellij.xdebugger.impl.breakpoints.LineBreakpointState
import com.intellij.xdebugger.impl.breakpoints.XLineBreakpointImpl
import org.jetbrains.annotations.Nls
import org.jetbrains.concurrency.Promise
import org.jetbrains.concurrency.resolvedPromise
import javax.swing.JComponent
import javax.swing.JPanel

class AngelScriptDebugState(environment: ExecutionEnvironment, runConfiguration: AngelScriptRunConfiguration) : RunProfileState {
    override fun execute(p0: Executor?, p1: ProgramRunner<*>): ExecutionResult? {
        return null
    }
}

class AngelScriptRunConfiguration(
    project: Project,
    factory: ConfigurationFactory
) : RunConfigurationBase<Any>(project, factory, "AngelScript") {

    var scriptPath: String = ""

    override fun getType(): ConfigurationType {
        return AngelscriptConfigurationType()
    }

    override fun getState(
        executor: Executor,
        environment: ExecutionEnvironment
    ): RunProfileState {
        return AngelScriptDebugState(environment, this)
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration?> {
        return AngelScriptRunConfigurationEditor()
    }
}

private class AngelScriptRunConfigurationEditor : SettingsEditor<AngelScriptRunConfiguration>() {
    private val panel = JPanel()
    override fun createEditor() = panel
    override fun resetEditorFrom(s: AngelScriptRunConfiguration) {}
    override fun applyEditorTo(s: AngelScriptRunConfiguration) {}
}

class AngelscriptConfigurationFactory(config: AngelscriptConfigurationType) : ConfigurationFactory(config) {
    override fun createTemplateConfiguration(p0: Project): RunConfiguration {
        return AngelScriptRunConfiguration(p0, this)
    }

    override fun getId() = "AngelscriptConfigurationFactory"
}

class AngelscriptConfigurationType : ConfigurationType {

    override fun getDisplayName() = "AngelScript"

    override fun getConfigurationTypeDescription() = "Angelscript debugger run config"

    override fun getIcon() = AllIcons.RunConfigurations.Application

    override fun getId() = "ANGELSCRIPT_DEBUGGER"

    override fun getConfigurationFactories(): Array<out ConfigurationFactory?> =
        arrayOf(AngelscriptConfigurationFactory(this))
}

class AngelscriptRunProfileStarter(var session: XDebugSession) : RunProfileStarter() {

    override fun executeAsync(environment: ExecutionEnvironment): Promise<RunContentDescriptor?> {
        return resolvedPromise<RunContentDescriptor?>(session.runContentDescriptor)
    }
}

class AngelscriptProgramRunner : ProgramRunner<RunnerSettings> {

    override fun getRunnerId() = "AngelscriptDebugger"

    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        return executorId == DefaultDebugExecutor.EXECUTOR_ID && profile is AngelScriptRunConfiguration
    }

    override fun execute(environment: ExecutionEnvironment) {
        val settings = AngelscriptSettings.getInstance()
        val session = XDebuggerManager.getInstance(environment.project)
            .startSession(environment, AngelscriptDebugProcessStarter(settings.debugHost, settings.debugPort))
        val starter = AngelscriptRunProfileStarter(session)
        ExecutionManager.getInstance(environment.project).startRunProfile(starter, environment)
    }
}

class AngelscriptDebugProcessStarter(
    private val host: String = "127.0.0.1",
    private val port: Int = 27099
) : XDebugProcessStarter() {
    override fun start(session: XDebugSession): XDebugProcess {
        return AngelscriptProcess(session, host, port)
    }
}

class AngelscriptDebuggerEditorsProvider : XDebuggerEditorsProvider() {
    override fun getFileType(): FileType {
        return AngelscriptFileType.INSTANCE
    }

    override fun createDocument(project: Project, expression: XExpression, sourcePosition: XSourcePosition?, mode: EvaluationMode): Document {
        return EditorFactory.getInstance().createDocument(expression.expression.toCharArray())
    }
}

class AngelscriptBreakpointHandler(
    private val process: AngelscriptProcess
) : XBreakpointHandler<XLineBreakpoint<AngelscriptBreakpointProperties>>(
    AngelscriptBreakpointType::class.java
) {
    private val breakpoints = mutableListOf<XLineBreakpoint<AngelscriptBreakpointProperties>>()
    private var nextId = 1

    override fun registerBreakpoint(bp: XLineBreakpoint<AngelscriptBreakpointProperties>) {
        breakpoints.add(bp)
        val id = nextId++
        val path = bp.fileUrl.removePrefix("file://")
        val line = bp.line + 1 // XDebugger is 0-based, Unreal expects 1-based
        val module = process.moduleNameForPath(path)
        process.client.sendSetBreakpoint(id, path, line, module)
    }

    override fun unregisterBreakpoint(bp: XLineBreakpoint<AngelscriptBreakpointProperties>, temporary: Boolean) {
        breakpoints.remove(bp)
        val path = bp.fileUrl.removePrefix("file://")
        val module = process.moduleNameForPath(path)
        process.client.sendClearBreakpoints(path, module)
    }

    fun syncAllBreakpoints() {
        nextId = 1
        for (bp in breakpoints) {
            val path = bp.fileUrl.removePrefix("file://")
            val line = bp.line + 1
            val module = process.moduleNameForPath(path)
            process.client.sendSetBreakpoint(nextId++, path, line, module)
        }
    }
}

class AngelscriptProcessHandler : DefaultDebugProcessHandler() {
    override fun destroyProcessImpl() {
        super.destroyProcessImpl()
    }

    override fun detachProcessImpl() {
        super.detachProcessImpl()
    }
}

class AngelscriptSuspendContext(
    private val process: AngelscriptProcess,
    frames: List<CallStackFrame>
) : XSuspendContext() {
    private val stack = AngelscriptStack(process, frames)

    override fun getActiveExecutionStack(): XExecutionStack = stack

    override fun getExecutionStacks(): Array<out XExecutionStack> = arrayOf(stack)
}

class AngelscriptStack(
    private val process: AngelscriptProcess,
    frames: List<CallStackFrame>
) : XExecutionStack("AngelScript") {

    private val stackFrames: List<AngelscriptStackFrame> = frames.mapIndexed { idx, f ->
        AngelscriptStackFrame(idx, f.name, f.sourcePath, f.line, process)
    }

    override fun getTopFrame(): XStackFrame? = stackFrames.firstOrNull()

    override fun computeStackFrames(firstFrameIndex: Int, container: XStackFrameContainer?) {
        if (firstFrameIndex == 0) {
            container?.addStackFrames(stackFrames, true)
        }
    }
}

class AngelscriptScopeGroup(
    label: String,
    private val scopePath: String,   // e.g. "0:%local%"
    private val process: AngelscriptProcess
) : XValueGroup(label) {
    override fun isAutoExpand() = scopePath in process.expandedPaths

    override fun computeChildren(node: XCompositeNode) {
        process.expandedPaths.add(scopePath)
        process.requestVariables(node, scopePath, last = true)
    }
}

class AngelscriptNamedValue(
    private val name: String,
    private val value: String,
    private val type: String,
    private val hasMembers: Boolean,
    // Full dotted path used to request child variables, e.g. "0:%local%.myVar"
    private val variablePath: String,
    private val process: AngelscriptProcess
) : XNamedValue(name) {
    override fun computePresentation(node: XValueNode, place: XValuePlace) {
        node.setPresentation(null, XStringValuePresentation(value), hasMembers)
    }

    override fun computeChildren(node: XCompositeNode) {
        if (hasMembers) {
            process.expandedPaths.add(variablePath)
            process.requestVariables(node, variablePath, last = true)
        }
    }

//    override fun isAutoExpandNode() = hasMembers && variablePath in process.expandedPaths
}

class AngelscriptEvaluator(
    private val frameIndex: Int,
    private val process: AngelscriptProcess
) : XDebuggerEvaluator() {

    data class PendingEval(val callback: XEvaluationCallback, val expression: String)

    private val pendingEvals = ArrayDeque<PendingEval>()

    override fun evaluate(expression: String, callback: XEvaluationCallback, expressionPosition: XSourcePosition?) {
        synchronized(pendingEvals) {
            pendingEvals.addLast(PendingEval(callback, expression))
        }
        process.client.sendRequestEvaluate(expression, frameIndex)
    }

    override fun getExpressionRangeAtOffset(project: com.intellij.openapi.project.Project, document: com.intellij.openapi.editor.Document, offset: Int, sideEffectsAllowed: Boolean): com.intellij.openapi.util.TextRange? {
        val text = document.charsSequence
        if (offset >= text.length) return null

        // Expand left to start of identifier (letters, digits, underscore, dot for member access)
        var start = offset
        while (start > 0 && isExprChar(text[start - 1])) start--

        // Expand right to end of identifier
        var end = offset
        while (end < text.length && isExprChar(text[end])) end++

        if (start >= end) return null
        // Don't return a range that starts or ends with a dot
        while (start < end && text[start] == '.') start++
        while (end > start && text[end - 1] == '.') end--

        return if (start < end) com.intellij.openapi.util.TextRange(start, end) else null
    }

    private fun isExprChar(c: Char) = c.isLetterOrDigit() || c == '_' || c == '.'

    fun receiveResult(name: String, value: String, type: String, hasMembers: Boolean) {
        val pending = synchronized(pendingEvals) { pendingEvals.removeFirstOrNull() } ?: return
        if (value.isEmpty()) {
            ApplicationManager.getApplication().invokeLater {
                pending.callback.errorOccurred("No result for '${pending.expression}'")
            }
        } else {
            val evalPath = "$frameIndex:${pending.expression}"
            val result = AngelscriptNamedValue(name.ifEmpty { pending.expression }, value, type, hasMembers, evalPath, process)
            ApplicationManager.getApplication().invokeLater {
                pending.callback.evaluated(result)
            }
        }
    }
}

class AngelscriptStackFrame(
    private val index: Int,
    private val functionName: String,
    private val sourcePath: String,
    private val lineNumber: Int,
    private val process: AngelscriptProcess
) : XStackFrame() {
    val evaluator = AngelscriptEvaluator(index, process)

    override fun getEvaluator(): XDebuggerEvaluator = evaluator

    override fun getSourcePosition(): XSourcePosition? {
        if (sourcePath.isEmpty() || sourcePath.startsWith("::")) return null
        val vFile = LocalFileSystem.getInstance().findFileByPath(sourcePath) ?: return null
        // XSourcePosition lines are 0-based, Unreal sends 1-based
        return XSourcePositionImpl.create(vFile, (lineNumber - 1).coerceAtLeast(0))
    }

    override fun customizePresentation(component: ColoredTextContainer) {
        if (sourcePath.isNotEmpty() && !sourcePath.startsWith("::")) {
            val fileName = sourcePath.substringAfterLast('/').substringAfterLast('\\')
            component.append(functionName, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            component.append(" ($fileName:$lineNumber)", SimpleTextAttributes.GRAYED_ATTRIBUTES)
        } else {
            component.append(functionName, SimpleTextAttributes.REGULAR_ATTRIBUTES)
        }
        component.setIcon(AllIcons.Debugger.Frame)
    }

    override fun computeChildren(node: XCompositeNode) {
        val scopes = XValueChildrenList()
        scopes.addTopGroup(AngelscriptScopeGroup("Local", "$index:%local%", process))
        scopes.addTopGroup(AngelscriptScopeGroup("This", "$index:%this%", process))
        scopes.addTopGroup(AngelscriptScopeGroup("Globals", "$index:%module%", process))
        node.addChildren(scopes, true)
    }
}

data class PendingVariableRequest(val node: XCompositeNode, val path: String, val last: Boolean)

class AngelscriptProcess(
    session: XDebugSession,
    val host: String,
    val port: Int
) : XDebugProcess(session) {

    val client = UnrealDebugClient(host, port)

    // Queue of outstanding RequestVariables calls; responses arrive in order
    private val pendingVariableRequests = ArrayDeque<PendingVariableRequest>()

    // Paths the user has explicitly expanded; keyed by variable path e.g. "0:%local%", "0:%local%.myVar"
    val expandedPaths: MutableSet<String> = mutableSetOf()

    // Evaluator of the currently selected frame; updated on positionReached
    @Volatile var activeEvaluator: AngelscriptEvaluator? = null

    private var breakpointHandler: AngelscriptBreakpointHandler? = null

    fun requestVariables(node: XCompositeNode, path: String, last: Boolean) {
        synchronized(pendingVariableRequests) {
            pendingVariableRequests.addLast(PendingVariableRequest(node, path, last))
        }
        client.sendRequestVariables(path)
    }

    fun moduleNameForPath(path: String): String {
        // Convert a file path to a module name (e.g. Script/MyGame/Foo.as → Script.MyGame.Foo)
        return path.substringAfterLast("/Script/")
            .replace('/', '.')
            .replace('\\', '.')
            .removeSuffix(".as")
    }

    override fun sessionInitialized() {
        client.onServerVersion = { version ->
            session.consoleView?.print("AngelScript debug server version: $version\n",
                com.intellij.execution.ui.ConsoleViewContentType.SYSTEM_OUTPUT)
        }

        client.onStopped = { reason, _, _ ->
            client.sendRequestCallStack()
        }

        client.onContinued = {
            ApplicationManager.getApplication().invokeLater {
                session.sessionResumed()
            }
        }

        client.onCallStack = { frames ->
            // Prune expansion state for frame indices that no longer exist
            val maxFrameIdx = frames.size - 1
            expandedPaths.removeAll { path ->
                val frameIdx = path.substringBefore(":").toIntOrNull()
                frameIdx != null && frameIdx > maxFrameIdx
            }
            val context = AngelscriptSuspendContext(this, frames)
            // Top frame (index 0) is the active one for watch evaluation
            activeEvaluator = (context.activeExecutionStack?.topFrame as? AngelscriptStackFrame)?.evaluator
            ApplicationManager.getApplication().invokeLater {
                session.positionReached(context)
            }
        }

        client.onEvaluate = { name, value, type, hasMembers ->
            activeEvaluator?.receiveResult(name, value, type, hasMembers)
        }

        session.addSessionListener(object : com.intellij.xdebugger.XDebugSessionListener {
            override fun stackFrameChanged() {
                val frame = session.currentStackFrame
                activeEvaluator = (frame as? AngelscriptStackFrame)?.evaluator
            }
        })

        client.onVariables = handler@{ variables ->
            val request = synchronized(pendingVariableRequests) {
                pendingVariableRequests.removeFirstOrNull()
            } ?: return@handler
            val childrenList = XValueChildrenList()
            for (v in variables) {
                val childPath = "${request.path}.${v.name}"
                childrenList.add(AngelscriptNamedValue(
                    v.name, v.value, v.type, v.hasMembers, childPath, this
                ))
            }
            ApplicationManager.getApplication().invokeLater {
                request.node.addChildren(childrenList, request.last)
            }
        }

        client.onClosed = {
            ApplicationManager.getApplication().invokeLater {
                session.stop()
            }
        }

        // Add a default watch for GetWorldDebugString() if not already present
//        val defaultExprText = "GetWorldDebugString()"
//        val managerImpl = XDebuggerManager.getInstance(session.project)
//            as? com.intellij.xdebugger.impl.XDebuggerManagerImpl
//        val watchesManager = managerImpl?.watchesManager
//        if (watchesManager != null &&
//            watchesManager.watchExpressions.none { it.expression == defaultExprText }) {
//            val expr = com.intellij.xdebugger.impl.breakpoints.XExpressionImpl(
//                defaultExprText, null, null, EvaluationMode.EXPRESSION
//            )
//            watchesManager.addWatchExpression(
//                session as com.intellij.xdebugger.impl.XDebugSessionImpl,
//                expr, -1, false
//            )
//        }

        val consoleView = session.consoleView
        client.connectWithRetry(
            maxRetries = 30,
            retryDelayMs = 2000L,
            onConnected = {
                consoleView?.print("Connected to Unreal AngelScript debug server at $host:$port\n",
                    com.intellij.execution.ui.ConsoleViewContentType.SYSTEM_OUTPUT)
                client.sendStartDebugging(2)
                breakpointHandler?.syncAllBreakpoints()
            },
            onRetry = { attempt ->
                consoleView?.print("Waiting for Unreal Engine... (attempt $attempt/30)\n",
                    com.intellij.execution.ui.ConsoleViewContentType.SYSTEM_OUTPUT)
            },
            onFailed = {
                consoleView?.print("Failed to connect to Unreal AngelScript debug server at $host:$port\n",
                    com.intellij.execution.ui.ConsoleViewContentType.ERROR_OUTPUT)
                ApplicationManager.getApplication().invokeLater {
                    session.stop()
                }
            }
        )
    }

    override fun doGetProcessHandler(): ProcessHandler = AngelscriptProcessHandler()

    override fun checkCanInitBreakpoints(): Boolean = true

    override fun getBreakpointHandlers(): Array<out XBreakpointHandler<*>> {
        val handler = AngelscriptBreakpointHandler(this)
        breakpointHandler = handler
        return arrayOf(handler)
    }

    override fun getEditorsProvider(): XDebuggerEditorsProvider {
        return AngelscriptDebuggerEditorsProvider()
    }

    override fun startPausing() {
        client.sendPause()
    }

    override fun startStepOver(context: XSuspendContext?) {
        client.sendStepOver()
    }

    override fun startStepInto(context: XSuspendContext?) {
        client.sendStepIn()
    }

    override fun startStepOut(context: XSuspendContext?) {
        client.sendStepOut()
    }

    override fun resume(context: XSuspendContext?) {
        client.sendContinue()
    }

    override fun stop() {
        client.sendStopDebugging()
        client.disconnect()
    }
}

class AngelscriptBreakpointProperties : XBreakpointProperties<Any>() {
    override fun getState(): Any? = null
    override fun loadState(p0: Any) {}
}

class AngelscriptBreakpointType : XLineBreakpointType<AngelscriptBreakpointProperties>(
    "angelscriptBreakpoint",
    "Angelscript Breakpoint"
) {
    override fun createBreakpointProperties(
        p0: com.intellij.openapi.vfs.VirtualFile,
        p1: Int
    ): AngelscriptBreakpointProperties = AngelscriptBreakpointProperties()

    override fun canPutAt(file: com.intellij.openapi.vfs.VirtualFile, line: Int, project: Project): Boolean {
        return file.extension == "as"
    }

    override fun getDisplayText(p0: XLineBreakpoint<AngelscriptBreakpointProperties>?): @Nls String =
        "Angelscript breakpoint"

    override fun addBreakpoint(project: Project?, parentComponent: JComponent?): XLineBreakpoint<AngelscriptBreakpointProperties>? {
        return XLineBreakpointImpl<AngelscriptBreakpointProperties>(this, null, AngelscriptBreakpointProperties(), LineBreakpointState())
    }
}
