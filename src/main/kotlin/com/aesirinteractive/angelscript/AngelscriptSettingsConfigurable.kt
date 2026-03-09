package com.aesirinteractive.angelscript

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AngelscriptSettingsConfigurable : Configurable {

    private val nodePathLabel = JBLabel("Node.js path:")
    private val nodePathField = JBTextField()
    private val lspPathKindCombo = ComboBox(LspPathKind.entries.toTypedArray())
    private val lspPathLabel = JBLabel("Language server path:")
    private val lspPathField = JBTextField()
    private val customCommandLineLabel = JBLabel("Command line:")
    private val customCommandLineField = JBTextField()
    private val fileExtensionsField = JBTextField()
    private val debugHostField = JBTextField()
    private val debugPortField = JBTextField()
    private val autoAttachCheckBox = JBCheckBox("Auto-attach AngelScript debugger when launching Unreal")
    private var panel: JPanel? = null

    override fun getDisplayName() = "AngelScript"

    override fun createComponent(): JComponent {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(nodePathLabel, nodePathField, 1, false)
            .addLabeledComponent(JBLabel("Language server source:"), lspPathKindCombo, 1, false)
            .addLabeledComponent(lspPathLabel, lspPathField, 1, false)
            .addLabeledComponent(customCommandLineLabel, customCommandLineField, 1, false)
            .addLabeledComponent(JBLabel("File extensions (comma-separated):"), fileExtensionsField, 1, false)
            .addSeparator()
            .addLabeledComponent(JBLabel("Debug server host:"), debugHostField, 1, false)
            .addLabeledComponent(JBLabel("Debug server port:"), debugPortField, 1, false)
            .addComponent(autoAttachCheckBox, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel

        lspPathKindCombo.addActionListener { updateLspPathVisibility() }
        updateLspPathVisibility()

        return panel!!
    }

    private fun updateLspPathVisibility() {
        val kind = lspPathKindCombo.selectedItem as? LspPathKind
        val isCommandLine = kind == LspPathKind.CommandLine
        val isCustom = kind == LspPathKind.Custom
        nodePathLabel.isVisible = !isCommandLine
        nodePathField.isVisible = !isCommandLine
        lspPathLabel.isVisible = isCustom
        lspPathField.isVisible = isCustom
        customCommandLineLabel.isVisible = isCommandLine
        customCommandLineField.isVisible = isCommandLine
        panel?.revalidate()
        panel?.repaint()
    }

    override fun isModified(): Boolean {
        val settings = AngelscriptSettings.getInstance()
        return nodePathField.text != settings.nodePath
            || lspPathField.text != settings.lspPath
            || lspPathKindCombo.selectedItem != settings.lspPathKind
            || customCommandLineField.text != settings.customCommandLine
            || fileExtensionsField.text != settings.fileExtensions
            || debugHostField.text != settings.debugHost
            || debugPortField.text != settings.debugPort.toString()
            || autoAttachCheckBox.isSelected != settings.autoAttachDebugger
    }

    override fun apply() {
        val settings = AngelscriptSettings.getInstance()
        settings.nodePath = nodePathField.text
        settings.lspPath = lspPathField.text
        settings.lspPathKind = lspPathKindCombo.selectedItem as LspPathKind
        settings.customCommandLine = customCommandLineField.text
        settings.fileExtensions = fileExtensionsField.text
        settings.debugHost = debugHostField.text
        settings.debugPort = debugPortField.text.toIntOrNull() ?: 27099
        settings.autoAttachDebugger = autoAttachCheckBox.isSelected
    }

    override fun reset() {
        val settings = AngelscriptSettings.getInstance()
        nodePathField.text = settings.nodePath
        lspPathField.text = settings.lspPath
        lspPathKindCombo.selectedItem = settings.lspPathKind
        customCommandLineField.text = settings.customCommandLine
        fileExtensionsField.text = settings.fileExtensions
        debugHostField.text = settings.debugHost
        debugPortField.text = settings.debugPort.toString()
        autoAttachCheckBox.isSelected = settings.autoAttachDebugger
        updateLspPathVisibility()
    }
}
