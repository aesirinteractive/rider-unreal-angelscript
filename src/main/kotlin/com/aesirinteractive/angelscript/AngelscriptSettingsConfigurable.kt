package com.aesirinteractive.angelscript

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AngelscriptSettingsConfigurable : Configurable {

    private val nodePathField = JBTextField()
    private val lspPathKindCombo = ComboBox(LspPathKind.entries.toTypedArray())
    private val lspPathLabel = JBLabel("Language server path:")
    private val lspPathField = JBTextField()
    private var panel: JPanel? = null

    override fun getDisplayName() = "AngelScript"

    override fun createComponent(): JComponent {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Node.js path:"), nodePathField, 1, false)
            .addLabeledComponent(JBLabel("Language server source:"), lspPathKindCombo, 1, false)
            .addLabeledComponent(lspPathLabel, lspPathField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel

        lspPathKindCombo.addActionListener { updateLspPathVisibility() }
        updateLspPathVisibility()

        return panel!!
    }

    private fun updateLspPathVisibility() {
        val isCustom = lspPathKindCombo.selectedItem == LspPathKind.Custom
        lspPathLabel.isVisible = isCustom
        lspPathField.isVisible = isCustom
        panel?.revalidate()
        panel?.repaint()
    }

    override fun isModified(): Boolean {
        val settings = AngelscriptSettings.getInstance()
        return nodePathField.text != settings.nodePath
            || lspPathField.text != settings.lspPath
            || lspPathKindCombo.selectedItem != settings.lspPathKind
    }

    override fun apply() {
        val settings = AngelscriptSettings.getInstance()
        settings.nodePath = nodePathField.text
        settings.lspPath = lspPathField.text
        settings.lspPathKind = lspPathKindCombo.selectedItem as LspPathKind
    }

    override fun reset() {
        val settings = AngelscriptSettings.getInstance()
        nodePathField.text = settings.nodePath
        lspPathField.text = settings.lspPath
        lspPathKindCombo.selectedItem = settings.lspPathKind
        updateLspPathVisibility()
    }
}
