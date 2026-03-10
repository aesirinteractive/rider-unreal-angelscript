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
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
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
import com.aesirinteractive.angelscript.AngelscriptTypes
import com.aesirinteractive.angelscript.psi.AngelscriptArgList
import com.aesirinteractive.angelscript.psi.AngelscriptAssignmentExpr
import com.aesirinteractive.angelscript.psi.AngelscriptExpr
import com.aesirinteractive.angelscript.psi.AngelscriptFunctionDecl
import com.aesirinteractive.angelscript.psi.AngelscriptPostfixExpr
import com.aesirinteractive.angelscript.psi.AngelscriptVariableAccessExpr
import com.aesirinteractive.angelscript.psi.AngelscriptVariableDecl
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

    sealed class PendingEvalEntry {
        data class Interactive(val callback: XEvaluationCallback, val expression: String) : PendingEvalEntry()
        data class Inline(val name: String, val onResult: (name: String, value: String) -> Unit) : PendingEvalEntry()
    }

    private val pendingEvals = ArrayDeque<PendingEvalEntry>()

    override fun evaluate(expression: String, callback: XEvaluationCallback, expressionPosition: XSourcePosition?) {
        synchronized(pendingEvals) {
            pendingEvals.addLast(PendingEvalEntry.Interactive(callback, expression))
        }
        process.client.sendRequestEvaluate(expression, frameIndex)
    }

    fun evaluateInline(name: String, onResult: (name: String, value: String) -> Unit) {
        synchronized(pendingEvals) {
            pendingEvals.addLast(PendingEvalEntry.Inline(name, onResult))
        }
        process.client.sendRequestEvaluate(name, frameIndex)
    }

    override fun getExpressionRangeAtOffset(project: com.intellij.openapi.project.Project, document: com.intellij.openapi.editor.Document, offset: Int, sideEffectsAllowed: Boolean): com.intellij.openapi.util.TextRange? {
        if (offset >= document.textLength) return null

        val psiFile = com.intellij.psi.PsiDocumentManager.getInstance(project).getPsiFile(document)
            ?: return identifierRangeAt(document.charsSequence, offset)

        val leaf = psiFile.findElementAt(offset) ?: return null
        // Walk upward through the PSI tree to find the innermost PostfixExpr that directly
        // contains the leaf, without crossing any non-Expr boundary.
        //
        // Grammar-Kit's collapse semantics (extends(".*Expr")=Expr) eliminate intermediate
        // expression nodes when a sub-expression has a single child. For example, a simple
        // identifier X inside a.foo(X) has this parent chain:
        //   VariableAccessExpr → Argument → ArgList → PostfixExpr(a.foo(X))
        // getParentOfType would skip past the ArgList and return the outer PostfixExpr,
        // causing hover on X to evaluate "a.foo" instead of X.
        //
        // The same problem applies to any non-Expr PSI node that contains an Expr child:
        //   - ArgList / Argument  in call expressions: a.foo(X)
        //   - ParenthesizedExpr  as a primary with suffixes: (X).foo
        //   - SubscriptSuffix (private/inlined): a[X] — no PSI boundary, handled below
        //
        // Strategy: walk up only through nodes in the Expr collapse set (AngelscriptParser.EXTENDS_SETS_).
        // Stop at the first PostfixExpr (use it) or at any non-Expr node (fall back to identifier range).
        val postfix = run {
            var node: com.intellij.psi.PsiElement? = leaf.parent
            var found: AngelscriptPostfixExpr? = null
            while (node != null) {
                if (node is AngelscriptPostfixExpr) {
                    found = node
                    break
                }
                // Only continue upward while we're inside the Expr collapse set.
                // Any non-Expr node (ArgList, Argument, ParenthesizedExpr-as-primary-with-suffix,
                // statements, declarations, etc.) means the leaf is not directly under a PostfixExpr.
                if (!AngelscriptParser.EXTENDS_SETS_[0].contains(node.node?.elementType)) break
                node = node.parent
            }
            found
        } ?: return identifierRangeAt(document.charsSequence, offset)

        // PostfixExpr children are flat (private rules are inlined by Grammar-Kit):
        //   VariableAccessExpr  DOT  IDENTIFIER  DOT  IDENTIFIER  LBRACK  Expr  RBRACK  ...
        //
        // Strategy: walk the children left-to-right, building up a range segment by segment.
        // Stop extending once we pass a segment whose end offset exceeds the cursor, subject
        // to the rules for each token type.
        val children = postfix.node.getChildren(null)
        val postfixStart = postfix.textRange.startOffset

        // Accumulate the start/end offsets of the expression we'll return.
        // exprStart begins at the PostfixExpr start and is reset when the cursor lands
        // inside a subscript — in that case we evaluate the index sub-expression, not the
        // whole a[...] chain.
        var exprStart = postfixStart
        var exprEnd = postfixStart  // will be set to end of first child below

        // First child is always the PrimaryExpr composite (VariableAccessExpr etc.)
        if (children.isEmpty()) return null
        val primaryChild = children[0]
        exprEnd = primaryChild.startOffset + primaryChild.textLength
        // If cursor is inside the base primary, return just that range immediately
        if (offset < exprEnd) {
            return com.intellij.openapi.util.TextRange(exprStart, exprEnd)
        }

        var i = 1
        while (i < children.size) {
            val child = children[i]
            val childStart = child.startOffset
            val type = child.elementType

            when (type) {
                // DOT: peek ahead to the next IDENTIFIER — that pair forms a FieldSuffix.
                // Extend only if the cursor is on the identifier (not just the dot).
                AngelscriptTypes.DOT -> {
                    val nextIdx = i + 1
                    if (nextIdx >= children.size) break
                    val ident = children[nextIdx]
                    val identEnd = ident.startOffset + ident.textLength
                    if (offset < ident.startOffset) break  // cursor is on the dot itself — return current range
                    exprEnd = identEnd
                    i = nextIdx  // consume the identifier too
                    if (offset < identEnd) break  // cursor was on this identifier — stop here
                }

                // LBRACK / RBRACK: SubscriptSuffix inlined as flat children.
                // The inner Expr is a single PSI child between [ and ].
                // - Hovering [ or ]: return the whole a[...] expression built so far including brackets.
                // - Hovering inside: reset exprStart/exprEnd to the inner expression's range.
                AngelscriptTypes.LBRACK -> {
                    // The inner Expr is children[i+1]; RBRACK is children[i+2] (depth=1 always at
                    // this level since nested subscripts appear as a child PostfixExpr node, not
                    // as further flat tokens here).
                    val innerIdx = i + 1
                    val rbrackIdx = i + 2
                    val rbrack = if (rbrackIdx < children.size && children[rbrackIdx].elementType == AngelscriptTypes.RBRACK)
                        children[rbrackIdx] else null
                    val rbrackEnd = rbrack?.let { it.startOffset + it.textLength } ?: (childStart + 1)

                    when {
                        offset == childStart || (rbrack != null && offset >= rbrack.startOffset && offset < rbrackEnd) -> {
                            // Hovering [ or ] — return the full a[...] range
                            exprEnd = rbrackEnd
                        }
                        rbrack != null && innerIdx < rbrackIdx -> {
                            // Hovering inside the subscript — evaluate the inner expression
                            val inner = children[innerIdx]
                            exprStart = inner.startOffset
                            exprEnd = inner.startOffset + inner.textLength
                        }
                    }
                    break
                }

                // Call suffix (ArgList) or anything else — stop
                else -> break
            }
            i++
        }

        return if (exprEnd > exprStart) com.intellij.openapi.util.TextRange(exprStart, exprEnd) else null
    }

    private fun identifierRangeAt(text: CharSequence, offset: Int): com.intellij.openapi.util.TextRange? {
        if (offset >= text.length || !isIdentChar(text[offset])) return null
        var start = offset
        while (start > 0 && isIdentChar(text[start - 1])) start--
        var end = offset
        while (end < text.length && isIdentChar(text[end])) end++
        return if (start < end) com.intellij.openapi.util.TextRange(start, end) else null
    }

    private fun isIdentChar(c: Char) = c.isLetterOrDigit() || c == '_'

    fun receiveResult(name: String, value: String, type: String, hasMembers: Boolean) {
        val pending = synchronized(pendingEvals) { pendingEvals.removeFirstOrNull() } ?: return
        when (pending) {
            is PendingEvalEntry.Interactive -> {
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
            is PendingEvalEntry.Inline -> {
                if (value.isNotEmpty()) {
                    ApplicationManager.getApplication().invokeLater {
                        pending.onResult(pending.name, value)
                    }
                }
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
            AngelscriptInlineValuesService.getInstance(session.project).clear()
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
            // Collect and evaluate inline variable values for the stopped location
            collectInlineValues(frames)
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

        val settings = AngelscriptSettings.getInstance()
        val autoReconnect = settings.autoReconnectDebugger
        val reconnectDelayMs = settings.debugReconnectDelayMs

        client.onClosed = {
            if (autoReconnect) {
                expandedPaths.clear()
                if (!session.isStopped) {
                    val consoleView = session.consoleView
                    consoleView?.print("Disconnected from Unreal AngelScript debug server. Reconnecting...\n",
                        com.intellij.execution.ui.ConsoleViewContentType.SYSTEM_OUTPUT)
                    startConnecting(autoReconnect, reconnectDelayMs)
                }
            } else {
                ApplicationManager.getApplication().invokeLater {
                    session.stop()
                }
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

        startConnecting(autoReconnect, reconnectDelayMs)
    }

    private fun startConnecting(autoReconnect: Boolean, retryDelayMs: Long) {
        val consoleView = session.consoleView
        val maxRetries = if (autoReconnect) -1 else 30
        client.connectWithRetry(
            maxRetries = maxRetries,
            retryDelayMs = retryDelayMs,
            onConnected = {
                consoleView?.print("Connected to Unreal AngelScript debug server at $host:$port\n",
                    com.intellij.execution.ui.ConsoleViewContentType.SYSTEM_OUTPUT)
                client.sendStartDebugging(2)
                breakpointHandler?.syncAllBreakpoints()
            },
            onRetry = { attempt ->
                if (autoReconnect) {
                    consoleView?.print("Waiting for Unreal Engine... (attempt $attempt)\n",
                        com.intellij.execution.ui.ConsoleViewContentType.SYSTEM_OUTPUT)
                } else {
                    consoleView?.print("Waiting for Unreal Engine... (attempt $attempt/30)\n",
                        com.intellij.execution.ui.ConsoleViewContentType.SYSTEM_OUTPUT)
                }
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
        AngelscriptInlineValuesService.getInstance(session.project).clear()
        client.sendStopDebugging()
        client.disconnect()
    }

    private fun collectInlineValues(frames: List<CallStackFrame>) {
        val topFrame = frames.firstOrNull() ?: return
        val sourcePath = topFrame.sourcePath
        if (sourcePath.isEmpty() || sourcePath.startsWith("::")) return
        val vFile = LocalFileSystem.getInstance().findFileByPath(sourcePath) ?: return
        val stopLine = topFrame.line - 1  // 1-based Unreal → 0-based
        val evaluator = activeEvaluator ?: return
        val service = AngelscriptInlineValuesService.getInstance(session.project)

        ApplicationManager.getApplication().executeOnPooledThread {
            ReadAction.run<Throwable> {
                val psiFile = PsiManager.getInstance(session.project).findFile(vFile) ?: return@run
                val document = FileDocumentManager.getInstance().getDocument(vFile) ?: return@run

                val stopOffset = document.getLineStartOffset(stopLine)
                val stopElement = psiFile.findElementAt(stopOffset)
                val enclosingFunction = PsiTreeUtil.getParentOfType(
                    stopElement, AngelscriptFunctionDecl::class.java
                ) ?: return@run

                // name → 0-based line, deduplicated (first occurrence wins)
                val lineByName = LinkedHashMap<String, Int>()

                // 1. Parameters
                for (param in enclosingFunction.parameterList.parameterDeclList) {
                    val n = param.identifier.text
                    val line = document.getLineNumber(param.identifier.textRange.startOffset)
                    lineByName.putIfAbsent(n, line)
                }

                // 2. Local variable declarations in the function body
                val body = enclosingFunction.compoundStatement ?: return@run
                for (decl in PsiTreeUtil.findChildrenOfType(body, AngelscriptVariableDecl::class.java)) {
                    // VariableItem is a private grammar rule — its IDENTIFIER tokens appear as
                    // direct IDENTIFIER children of the VariableDecl ASTNode (not inside TypeRef,
                    // which is a composite child and won't match the token-type filter).
                    for (nameNode in decl.node.getChildren(AngelscriptTokenSets.IDENTIFIERS).map { it.psi }) {
                        val n = nameNode.text
                        val line = document.getLineNumber(nameNode.textRange.startOffset)
                        lineByName.putIfAbsent(n, line)
                    }
                }

                // 3. Assignments to simple variables (x = ... but not this.x = ...)
                for (assign in PsiTreeUtil.findChildrenOfType(body, AngelscriptAssignmentExpr::class.java)) {
                    val lhs = assign.exprList.firstOrNull() ?: continue
                    val varAccess = findSimpleVariableAccess(lhs) ?: continue
                    val n = varAccess.identifier.text
                    val line = document.getLineNumber(varAccess.identifier.textRange.startOffset)
                    lineByName.putIfAbsent(n, line)
                }

                // Evaluate each name and push the result to the service
                for ((n, line) in lineByName) {
                    evaluator.evaluateInline(n) { _, value ->
                        service.putValue(vFile, line, "$n = $value")
                    }
                }
            }
        }
    }

    private fun findSimpleVariableAccess(expr: AngelscriptExpr): AngelscriptVariableAccessExpr? {
        val postfix = PsiTreeUtil.findChildOfType(expr, AngelscriptPostfixExpr::class.java) ?: return null
        val hasComplexSuffix = postfix.node.getChildren(null).any { child ->
            child.elementType == AngelscriptTypes.DOT ||
            child.elementType == AngelscriptTypes.LBRACK ||
            child.elementType == AngelscriptTypes.COLONCOLON ||
            child.elementType == AngelscriptTypes.PLUSPLUS ||
            child.elementType == AngelscriptTypes.MINUSMINUS
        } || postfix.argListList.isNotEmpty()
        if (hasComplexSuffix) return null
        return PsiTreeUtil.findChildOfType(postfix, AngelscriptVariableAccessExpr::class.java)
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
