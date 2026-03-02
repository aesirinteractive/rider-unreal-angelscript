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
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.ColoredTextContainer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.xdebugger.DefaultDebugProcessHandler
import com.intellij.xdebugger.XDebugProcess
import com.intellij.xdebugger.XDebugProcessStarter
import com.intellij.xdebugger.XDebugSession
import com.intellij.xdebugger.XDebuggerManager
import com.intellij.xdebugger.XExpression
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.breakpoints.XBreakpoint
import com.intellij.xdebugger.breakpoints.XBreakpointHandler
import com.intellij.xdebugger.breakpoints.XBreakpointProperties
import com.intellij.xdebugger.breakpoints.XLineBreakpoint
import com.intellij.xdebugger.breakpoints.XLineBreakpointType
import com.intellij.xdebugger.evaluation.EvaluationMode
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider
import com.intellij.xdebugger.frame.XCompositeNode
import com.intellij.xdebugger.frame.XExecutionStack
import com.intellij.xdebugger.frame.XExecutionStack.XStackFrameContainer
import com.intellij.xdebugger.frame.XNamedValue
import com.intellij.xdebugger.frame.XStackFrame
import com.intellij.xdebugger.frame.XSuspendContext
import com.intellij.xdebugger.frame.XValueChildrenList
import com.intellij.xdebugger.frame.XValueNode
import com.intellij.xdebugger.frame.XValuePlace
import com.intellij.xdebugger.frame.presentation.XStringValuePresentation
import com.intellij.xdebugger.impl.breakpoints.LineBreakpointState
import com.intellij.xdebugger.impl.breakpoints.XLineBreakpointImpl
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.concurrency.Promise
import org.jetbrains.concurrency.resolvedPromise
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.JPanel

class AngelScriptDebugState(environment: ExecutionEnvironment, runConfiguration: AngelScriptRunConfiguration) : RunProfileState {
    override fun execute(p0: Executor?, p1: ProgramRunner<*>): ExecutionResult? {
        TODO("Not yet implemented")
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

    override fun getDisplayName() = "Angelscript"

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
        return executorId == DefaultDebugExecutor.EXECUTOR_ID// && profile is AngelScriptRunConfiguration
    }

    override fun execute(environment: ExecutionEnvironment) {
        val session = XDebuggerManager.getInstance(environment.project)
            .startSession(environment, AngelscriptDebugProcessStarter())
        val descriptor = session.runContentDescriptor
        var starter = AngelscriptRunProfileStarter(session)
        ExecutionManager.getInstance(environment.project).startRunProfile(starter, environment)
    }
}

class AngelscriptDebugProcessStarter : XDebugProcessStarter() {
    override fun start(session: XDebugSession): XDebugProcess {
        val res = AngelscriptProcess(session)
        return res
    }


}

class AngelscriptFileType : FileType {
    override fun getName(): @NonNls String {
        return "Angelscript"
    }

    override fun getDescription(): @NlsContexts.Label String {
        return "Angelscript file"
    }

    override fun getDefaultExtension(): @NlsSafe String {
        return ".as"
    }

    override fun getIcon(): Icon? {
        return null
    }

    override fun isBinary(): Boolean {
        return false
    }

}

class AngelscriptDebuggerEditorsProvider : XDebuggerEditorsProvider() {
    override fun getFileType(): FileType {
        return AngelscriptFileType()
    }

    override fun createDocument(project: Project, expression: XExpression, sourcePosition: XSourcePosition?, mode: EvaluationMode): Document {
        return EditorFactory.getInstance().createDocument(expression.expression.toCharArray())

    }

}

class AngelscriptBreakpointHandler(process: AngelscriptProcess) : XBreakpointHandler<XLineBreakpoint<AngelscriptBreakpointProperties>>(
    AngelscriptBreakpointType::class.java
) {
    var breakpoints: ArrayList<XBreakpoint<*>> = ArrayList()


    override fun registerBreakpoint(p0: XLineBreakpoint<AngelscriptBreakpointProperties>) {
        breakpoints.add(p0)
    }

    override fun unregisterBreakpoint(
        p0: XLineBreakpoint<AngelscriptBreakpointProperties>,
        p1: Boolean
    ) {
        breakpoints.remove(p0)
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

    ) : XSuspendContext() {
    var stack = AngelscriptStack(process, this)
    override fun getActiveExecutionStack(): XExecutionStack? {
        return stack
    }

    override fun getExecutionStacks(): Array<out XExecutionStack?> {
        return arrayOf(stack)
    }
}

class AngelscriptStack(
    private val process: AngelscriptProcess,
    private val suspendContext: AngelscriptSuspendContext,

    ) : XExecutionStack("Angelscript stack") {
    var stackFrames: List<AngelscriptStackFrame> = arrayListOf<AngelscriptStackFrame>(AngelscriptStackFrame(0), AngelscriptStackFrame(1))

    override fun getTopFrame(): XStackFrame? {
        return stackFrames.firstOrNull()
    }

    override fun computeStackFrames(firstFrameIndex: Int, container: XStackFrameContainer?) {
        if (firstFrameIndex == 0) {
            container!!.addStackFrames(stackFrames, true);
        }
    }

}

class AngelscriptNamedValue(
    private val name: String,
    private val value: String,
) : XNamedValue(name) {
    override fun computePresentation(node: XValueNode, place: XValuePlace) {
        node.setPresentation(AllIcons.Nodes.Function, XStringValuePresentation(value), false)
    }

}

class AngelscriptStackFrame(
    private val index: Int,
) : XStackFrame() {
    override fun customizePresentation(component: ColoredTextContainer) {
        val position = this.sourcePosition
        if (position != null) {
            component.append(position.file.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
            component.append(":" + (position.line + 1), SimpleTextAttributes.REGULAR_ATTRIBUTES)
            component.setIcon(AllIcons.Debugger.Frame)
        } else {
            component.append("Frame $index", SimpleTextAttributes.REGULAR_ATTRIBUTES)
        }
    }

    override fun computeChildren(node: XCompositeNode) {
        val childrenList = XValueChildrenList()
        childrenList.add(AngelscriptNamedValue("a", "$index"))
        childrenList.add(AngelscriptNamedValue("b", "true"))
        node.addChildren(childrenList, true)
        super.computeChildren(node)
    }
}

class AngelscriptProcess(
    session: XDebugSession,
) : XDebugProcess(session) {

    override fun sessionInitialized() {
        var suspendContext = AngelscriptSuspendContext(this)
        session.positionReached(suspendContext)
    }

    override fun stopAsync(): Promise<in Any> {
        return super.stopAsync()
    }

    override fun doGetProcessHandler(): ProcessHandler? {
        return AngelscriptProcessHandler()
    }

    override fun checkCanInitBreakpoints(): Boolean {
        return true
    }

    override fun getBreakpointHandlers(): Array<out XBreakpointHandler<*>?> {
        return arrayOf(AngelscriptBreakpointHandler(this))
    }

    override fun logStack(suspendContext: XSuspendContext, session: XDebugSession) {
        super.logStack(suspendContext, session)
    }

    override fun getEditorsProvider(): XDebuggerEditorsProvider {
        return AngelscriptDebuggerEditorsProvider()
    }

    override fun startStepOver(context: XSuspendContext?) {
        TODO("Not yet implemented")
    }

    override fun startStepInto(context: XSuspendContext?) {
        TODO("Not yet implemented")
    }

    override fun resume(context: XSuspendContext?) {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}

class AngelscriptBreakpointProperties : XBreakpointProperties<Any>() {
    override fun getState(): Any? {
        return null
    }

    override fun loadState(p0: Any) {

    }

}

//class AngelscriptBreakpointType : XBreakpointType<XLineBreakpoint<AngelscriptBreakpointProperties>, AngelscriptBreakpointProperties> (
class AngelscriptBreakpointType : XLineBreakpointType<AngelscriptBreakpointProperties>(
    "angelscriptBreakpoint",
    "Angelscript Breakpoint"
) {
    override fun createBreakpointProperties(
        p0: VirtualFile,
        p1: Int
    ): AngelscriptBreakpointProperties? {
        return AngelscriptBreakpointProperties()
    }

    override fun canPutAt(file: VirtualFile, line: Int, project: Project): Boolean {
        return true
    }

    override fun getDisplayText(p0: XLineBreakpoint<AngelscriptBreakpointProperties>?): @Nls String? {
        return "Angelscript breakpoint"
    }

    override fun addBreakpoint(project: Project?, parentComponent: JComponent?): XLineBreakpoint<AngelscriptBreakpointProperties>? {
        return XLineBreakpointImpl<AngelscriptBreakpointProperties>(this, null, AngelscriptBreakpointProperties(), LineBreakpointState());
    }
}

