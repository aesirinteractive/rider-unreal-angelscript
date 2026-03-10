package com.aesirinteractive.angelscript

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.*
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.platform.lsp.api.LspServer
import com.intellij.platform.lsp.api.LspServerManager
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import com.intellij.platform.lsp.api.customization.LspCustomization
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.refactoring.actions.RenameElementAction;
import com.intellij.refactoring.rename.RenameHandler

private val LOG = logger<AngelscriptLspServerSupportProvider>()

class AngelscriptRenameHandler : RenameHandler {
    override fun isAvailableOnDataContext(dataContext: DataContext): Boolean {
        val file = CommonDataKeys.PSI_FILE.getData(dataContext) ?: return false
//        return file.language == AngelscriptLanguage.INSTANCE
        return true
    }

    override fun invoke(
        project: Project,
        editor: Editor?,
        file: PsiFile?,
        dataContext: DataContext?
    ) {
        if (editor == null) {
            return
        }
        val position = editor.caretModel.primaryCaret.logicalPosition
        val ServerManager = LspServerManager.getInstance(project)
        val AngelscriptLs: Collection<LspServer> = ServerManager.getServersForProvider(AngelscriptLspServerSupportProvider::class.java)
//        AngelscriptLs.stream().findFirst().ifPresent { lspServer: LspServer ->
//            lspServer.sendRequestSync { server: Lsp4jServer ->
//                val fut = server.textDocumentService.rename(RenameParams(
//                    TextDocumentIdentifier("test.as"),
//                    Position(position.line, position.column),
//                    "newName"
//                ))
//                fut
//            }

//        }
        NotificationGroupManager.getInstance()
            .getNotificationGroup("AngelScript LSP")
            .createNotification("Custom rename 1", "Custom rename", NotificationType.INFORMATION)
            .notify(project)
    }

    override fun invoke(
        project: Project,
        editor: Array<out PsiElement?>,
        dataContext: DataContext?
    ) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("AngelScript LSP")
            .createNotification("Custom rename 2", "Custom rename", NotificationType.INFORMATION)
            .notify(project)
    }

}

internal class AngelscriptLspServerSupportProvider : LspServerSupportProvider {
    private class AngelLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "AngelScript") {
        override val lspCustomization: LspCustomization = AngelscriptLspCustomization()

        override fun isSupportedFile(file: VirtualFile) =
            file.extension != null && file.extension in AngelscriptSettings.getInstance().parsedFileExtensions()

        override fun createCommandLine(): GeneralCommandLine {
            val settings = AngelscriptSettings.getInstance()

            if (settings.lspPathKind == LspPathKind.CommandLine) {
                val cmd = settings.customCommandLine
                if (cmd.isBlank()) {
                    val msg = "AngelScript LSP: custom command line is empty — cannot start server"
                    LOG.warn(msg)
                    notifyError("Language server not configured", msg)
                    throw ExecutionException(msg)
                }
                val tokens = ParametersList.parse(cmd)
                LOG.info("AngelScript LSP starting (CommandLine mode): $cmd")
                notifyInfo("AngelScript LSP starting", "Command: $cmd")
                return GeneralCommandLine(tokens.toList())
            }

            val node = settings.nodePath.ifBlank { "node" }
            val lsp = when (settings.lspPathKind) {
                LspPathKind.Bundled -> {
                    val path = extractBundledLspServer()
                    if (path == null) {
                        val msg = "AngelScript LSP: bundled server.js not found in plugin resources — server cannot start"
                        LOG.error(msg)
                        notifyError("Bundled language server not found", msg)
                        throw ExecutionException(msg)
                    }
                    path
                }
                LspPathKind.VsCode -> {
                    val path = findDefaultLspPath()
                    if (path == null) {
                        val vsExt = Path.of(System.getProperty("user.home") ?: "~", ".vscode", "extensions").toString()
                        val msg = "AngelScript LSP: VS Code extension 'hazelight.unreal-angelscript-*' not found under $vsExt"
                        LOG.warn(msg)
                        notifyError("VS Code extension not found", msg)
                        throw ExecutionException(msg)
                    }
                    path
                }
                LspPathKind.Custom -> {
                    val path = settings.lspPath
                    if (path.isBlank()) {
                        val msg = "AngelScript LSP: custom server path is empty — cannot start server"
                        LOG.warn(msg)
                        notifyError("Language server path not configured", msg)
                        throw ExecutionException(msg)
                    }
                    if (!Files.exists(Path.of(path))) {
                        val msg = "AngelScript LSP: custom server path does not exist: $path"
                        LOG.warn(msg)
                        notifyError("Language server not found", msg)
                        throw ExecutionException(msg)
                    }
                    path
                }
                LspPathKind.CommandLine -> "" // handled above
            }

            // Verify node is resolvable (best-effort: check absolute path exists)
            if (node != "node" && !Files.exists(Path.of(node))) {
                val msg = "AngelScript LSP: node binary not found at '$node' — server may fail to start"
                LOG.warn(msg)
                notifyWarning("Node not found", msg)
            }

            LOG.info("AngelScript LSP starting (${settings.lspPathKind}): node='$node' server='$lsp'")
            notifyInfo("AngelScript LSP starting", "Node: $node\nServer: $lsp")
            return GeneralCommandLine(node, lsp, "--stdio")
        }

        private fun extractBundledLspServer(): String? {
            val target = Path.of(PathManager.getPluginsPath(), "AngelscriptLsp", "lsp", "server.js")
            LOG.info("AngelScript LSP: bundled server extraction target: $target")
            if (Files.exists(target) && Files.size(target) > 0) {
                LOG.info("AngelScript LSP: using cached bundled server at $target (${Files.size(target)} bytes)")
                return target.toString()
            }
            val stream = AngelscriptLspServerSupportProvider::class.java.getResourceAsStream("/lsp/server.js")
            if (stream == null) {
                LOG.error("AngelScript LSP: /lsp/server.js not found in plugin JAR resources")
                return null
            }
            Files.createDirectories(target.parent)
            stream.use { Files.copy(it, target, StandardCopyOption.REPLACE_EXISTING) }
            LOG.info("AngelScript LSP: extracted bundled server to $target (${Files.size(target)} bytes)")
            return target.toString()
        }

        private fun findDefaultLspPath(): String? {
            val home = System.getProperty("user.home") ?: return null
            val pattern = "hazelight.unreal-angelscript-*"
            val matcher = FileSystems.getDefault().getPathMatcher("glob:$pattern")
            val base = Path.of(home, ".vscode", "extensions")
            LOG.info("AngelScript LSP: searching for VS Code extension under $base")
            if (!Files.isDirectory(base)) {
                LOG.warn("AngelScript LSP: VS Code extensions directory not found: $base")
                return null
            }
            val candidates = Files.walk(base, 1).filter { matcher.matches(it.fileName) }
            val latest = candidates.max(Comparator.comparingLong { Files.getLastModifiedTime(it).toMillis() })
            return latest.map { dir ->
                val serverPath = "$dir/language-server/dist/server.js"
                LOG.info("AngelScript LSP: found VS Code extension at $dir, server path: $serverPath")
                if (!Files.exists(Path.of(serverPath))) {
                    LOG.warn("AngelScript LSP: extension found at $dir but server.js missing at $serverPath")
                }
                serverPath
            }.orElse(null)
        }

        private fun notifyInfo(title: String, content: String) =
            NotificationGroupManager.getInstance()
                .getNotificationGroup("AngelScript LSP")
                .createNotification(title, content, NotificationType.INFORMATION)
                .notify(project)

        private fun notifyWarning(title: String, content: String) =
            NotificationGroupManager.getInstance()
                .getNotificationGroup("AngelScript LSP")
                .createNotification(title, content, NotificationType.WARNING)
                .notify(project)

        private fun notifyError(title: String, content: String) =
            NotificationGroupManager.getInstance()
                .getNotificationGroup("AngelScript LSP")
                .createNotification(title, content, NotificationType.ERROR)
                .notify(project)
    }

    override fun fileOpened(project: Project, file: VirtualFile, serverStarter: LspServerSupportProvider.LspServerStarter) {
        if (file.extension != null && file.extension in AngelscriptSettings.getInstance().parsedFileExtensions()) {
            serverStarter.ensureServerStarted(AngelLspServerDescriptor(project))
        }
    }

}

class AngelscriptLspCustomization : LspCustomization() {

}

//class AngelscriptNamedElement : PsiElement {
//
//}
