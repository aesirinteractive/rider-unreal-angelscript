package com.aesirinteractive.angelscript

import com.intellij.execution.ExecutionListener
import com.intellij.execution.ExecutionManager
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.runners.ExecutionEnvironmentBuilder
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.xdebugger.XDebuggerManager

/**
 * Startup activity that subscribes [AngelscriptAutoAttachListener] to the project's execution
 * topic so it is notified when debug sessions start.
 */
class AngelscriptAutoAttachStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        project.messageBus.connect().subscribe(
            ExecutionManager.EXECUTION_TOPIC,
            AngelscriptAutoAttachListener()
        )
    }
}

/**
 * Listens for any debug execution starting in the project. When a debug configuration is
 * launched that is NOT an AngelScript configuration, and [AngelscriptSettings.autoAttachDebugger]
 * is enabled, automatically starts an AngelScript debug session to attach to Unreal's debug server.
 */
class AngelscriptAutoAttachListener : ExecutionListener {

    override fun processStarting(executorId: String, environment: ExecutionEnvironment) {
        // Only respond to debug launches
        if (executorId != DefaultDebugExecutor.EXECUTOR_ID) return

        // Don't trigger recursively for AngelScript configs
        if (environment.runProfile is AngelScriptRunConfiguration) return

        val settings = AngelscriptSettings.getInstance()
        if (!settings.autoAttachDebugger) return

        val project = environment.project

        // Run on a slight delay so the primary debug session has time to register first
        com.intellij.openapi.application.ApplicationManager.getApplication().invokeLater {
            startAngelscriptDebugSession(project, settings.debugHost, settings.debugPort)
        }
    }

    private fun startAngelscriptDebugSession(project: Project, host: String, port: Int) {
        try {
            val configType = AngelscriptConfigurationType()
            val factory = configType.configurationFactories.first()!!
            val runConfig = AngelScriptRunConfiguration(project, factory)

            val executor = DefaultDebugExecutor.getDebugExecutorInstance()
            val environment = ExecutionEnvironmentBuilder
                .create(executor, runConfig)
                .build()

            XDebuggerManager.getInstance(project)
                .startSession(environment, AngelscriptDebugProcessStarter(host, port))
        } catch (e: Exception) {
            // Silently ignore if we can't start — the user will see failure in console if it matters
        }
    }
}
