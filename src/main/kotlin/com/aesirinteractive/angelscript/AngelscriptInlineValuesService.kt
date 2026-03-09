package com.aesirinteractive.angelscript

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.util.concurrent.ConcurrentHashMap

@Service(Service.Level.PROJECT)
class AngelscriptInlineValuesService(private val project: Project) {

    // file → (0-based line → ordered list of "name = value" entries)
    // Multiple variables on the same line (e.g. parameters, multi-declarators) each append an entry.
    private val values = ConcurrentHashMap<VirtualFile, ConcurrentHashMap<Int, MutableList<String>>>()

    companion object {
        fun getInstance(project: Project): AngelscriptInlineValuesService = project.service()
    }

    /** Called from the Unreal reader thread when an inline evaluation result arrives. */
    fun putValue(file: VirtualFile, line: Int, text: String) {
        val lineMap = values.getOrPut(file) { ConcurrentHashMap() }
        val list = lineMap.computeIfAbsent(line) { mutableListOf() }
        synchronized(list) { list.add(text) }
        repaintEditors()
    }

    /** Clear all inline values (called on session resume or stop). */
    fun clear() {
        values.clear()
        repaintEditors()
    }

    /** Called synchronously from the EDT paint thread by AngelscriptEditorLinePainter. */
    fun getLineText(file: VirtualFile, line: Int): String? {
        val list = values[file]?.get(line) ?: return null
        return synchronized(list) { if (list.isEmpty()) null else list.joinToString(", ") }
    }

    private fun repaintEditors() {
        ApplicationManager.getApplication().invokeLater {
            EditorFactory.getInstance().allEditors.forEach { editor ->
                if (editor.project == project) editor.component.repaint()
            }
        }
    }
}
