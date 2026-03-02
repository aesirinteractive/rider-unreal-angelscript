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
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor

internal class AngelscriptLspServerSupportProvider : LspServerSupportProvider {
    private class AngelLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "Angelscript") {
        override fun isSupportedFile(file: VirtualFile) = file.extension == "as"
        override fun createCommandLine(): GeneralCommandLine {
            val settings = AngelscriptSettings.getInstance()
            val node = settings.nodePath.ifBlank { "node" }
            val lsp = when (settings.lspPathKind) {
                LspPathKind.Bundled -> extractBundledLspServer() ?: ""
                LspPathKind.VsCode -> findDefaultLspPath() ?: ""
                LspPathKind.Custom -> settings.lspPath
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

    override fun fileOpened(project: Project, file: VirtualFile, serverStarter: LspServerSupportProvider.LspServerStarter) {
        if (file.extension == "as") {
            serverStarter.ensureServerStarted(AngelLspServerDescriptor(project))
        }
    }

}
