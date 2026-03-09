package com.aesirinteractive.angelscript

import com.intellij.execution.configurations.*
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import com.intellij.openapi.application.PathManager
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
                val tokens = ParametersList.parse(settings.customCommandLine)
                NotificationGroupManager.getInstance()
                    .getNotificationGroup("AngelScript LSP")
                    .createNotification("AngelScript LSP starting", "Command: ${settings.customCommandLine}", NotificationType.INFORMATION)
                    .notify(project)
                return GeneralCommandLine(tokens.toList())
            }
            val node = settings.nodePath.ifBlank { "node" }
            val lsp = when (settings.lspPathKind) {
                LspPathKind.Bundled -> extractBundledLspServer() ?: ""
                LspPathKind.VsCode -> findDefaultLspPath() ?: ""
                LspPathKind.Custom -> settings.lspPath
                LspPathKind.CommandLine -> "" // handled above
            }
            NotificationGroupManager.getInstance()
                .getNotificationGroup("AngelScript LSP")
                .createNotification("AngelScript LSP starting", "Node: $node\nServer: $lsp", NotificationType.INFORMATION)
                .notify(project)
            return GeneralCommandLine(node, lsp, "--stdio")
        }

        private fun extractBundledLspServer(): String? {
            val target = Path.of(PathManager.getPluginsPath(), "AngelscriptLsp", "lsp", "server.js")
            if (Files.exists(target) && Files.size(target) > 0) return target.toString()
            val stream = AngelscriptLspServerSupportProvider::class.java.getResourceAsStream("/lsp/server.js")
                ?: return null
            Files.createDirectories(target.parent)
            stream.use { Files.copy(it, target, StandardCopyOption.REPLACE_EXISTING) }
            return target.toString()
        }

        private fun findDefaultLspPath(): String? {
            val home = System.getProperty("user.home") ?: return null
            val pattern = "hazelight.unreal-angelscript-*"
            val matcher = FileSystems.getDefault().getPathMatcher("glob:$pattern")
            val base = Path.of(home, ".vscode", "extensions")
            if (!Files.isDirectory(base)) return null
            val candidates = Files.walk(base, 1)
                .filter {
                    val matches = matcher.matches(it.fileName)
                    matches
                }
            val latest = candidates.max(Comparator.comparingLong { Files.getLastModifiedTime(it).toMillis() })
            return latest.map {
                val res = "$it/language-server/dist/server.js"
                res
            }.orElse(null)
        }
    }

    override fun fileOpened(project: Project, virtualFile: VirtualFile, serverStarter: LspServerSupportProvider.LspServerStarter) {
        if (virtualFile.extension != null && virtualFile.extension in AngelscriptSettings.getInstance().parsedFileExtensions()) {
            serverStarter.ensureServerStarted(AngelLspServerDescriptor(project))
        }
    }

}

class AngelscriptLspCustomization : LspCustomization() {

}

//class AngelscriptNamedElement : PsiElement {
//
//}
