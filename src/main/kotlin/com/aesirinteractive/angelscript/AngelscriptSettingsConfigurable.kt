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
    private val autoReconnectCheckBox = JBCheckBox("Auto-reconnect debugger when connection is lost (unlimited retries)")
    private val reconnectDelayField = JBTextField()
    private val logDebugMessagesCheckBox = JBCheckBox("Log debug protocol messages to console")
    private val clangFormatPathKindCombo = ComboBox(ClangFormatPathKind.entries.toTypedArray())
    private val clangFormatPathLabel = JBLabel("clang-format path:")
    private val clangFormatPathField = JBTextField()
    private val clangFormatFileField = JBTextField()
    private var panel: JPanel? = null

    override fun getDisplayName() = "AngelScript"

    override fun createComponent(): JComponent {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(nodePathLabel, nodePathField, 1, false)
            .addLabeledComponent(JBLabel("Language server source:"), lspPathKindCombo, 1, false)
            .addLabeledComponent(lspPathLabel, lspPathField, 1, false)
            .addLabeledComponent(customCommandLineLabel, customCommandLineField, 1, false)
            .addLabeledComponent(JBLabel("File extensions (comma-separated):"), fileExtensionsField, 1, false)
            .addLabeledComponent(JBLabel("clang-format source:"), clangFormatPathKindCombo, 1, false)
            .addLabeledComponent(clangFormatPathLabel, clangFormatPathField, 1, false)
            .addLabeledComponent(JBLabel("clang-format file:"), clangFormatFileField, 1, false)
            .addSeparator()
            .addLabeledComponent(JBLabel("Debug server host:"), debugHostField, 1, false)
            .addLabeledComponent(JBLabel("Debug server port:"), debugPortField, 1, false)
            .addComponent(autoReconnectCheckBox, 1)
            .addLabeledComponent(JBLabel("Reconnect delay (ms):"), reconnectDelayField, 1, false)
            .addComponent(logDebugMessagesCheckBox, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel

        lspPathKindCombo.addActionListener { updateLspPathVisibility() }
        updateLspPathVisibility()

        clangFormatPathKindCombo.addActionListener { updateClangFormatPathVisibility() }
        updateClangFormatPathVisibility()

        return panel!!
    }

    private fun updateClangFormatPathVisibility() {
        val isCustom = clangFormatPathKindCombo.selectedItem == ClangFormatPathKind.Custom
        clangFormatPathLabel.isVisible = isCustom
        clangFormatPathField.isVisible = isCustom
        panel?.revalidate()
        panel?.repaint()
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
            || clangFormatPathKindCombo.selectedItem != settings.clangFormatPathKind
            || clangFormatPathField.text != settings.clangFormatPath
            || clangFormatFileField.text != settings.clangFormatFile
            || debugHostField.text != settings.debugHost
            || debugPortField.text != settings.debugPort.toString()
            || autoReconnectCheckBox.isSelected != settings.autoReconnectDebugger
            || reconnectDelayField.text.toLongOrNull() != settings.debugReconnectDelayMs
            || logDebugMessagesCheckBox.isSelected != settings.logDebugMessages
    }

    override fun apply() {
        val settings = AngelscriptSettings.getInstance()
        settings.nodePath = nodePathField.text
        settings.lspPath = lspPathField.text
        settings.lspPathKind = lspPathKindCombo.selectedItem as LspPathKind
        settings.customCommandLine = customCommandLineField.text
        settings.fileExtensions = fileExtensionsField.text
        settings.clangFormatPathKind = clangFormatPathKindCombo.selectedItem as ClangFormatPathKind
        settings.clangFormatPath = clangFormatPathField.text
        settings.clangFormatFile = clangFormatFileField.text
        AngelscriptExternalFormatter.clearClangFormatCache()
        settings.debugHost = debugHostField.text
        settings.debugPort = debugPortField.text.toIntOrNull() ?: 27099
        settings.autoReconnectDebugger = autoReconnectCheckBox.isSelected
        settings.debugReconnectDelayMs = reconnectDelayField.text.toLongOrNull()?.coerceAtLeast(100L) ?: 2000L
        settings.logDebugMessages = logDebugMessagesCheckBox.isSelected
    }

    override fun reset() {
        val settings = AngelscriptSettings.getInstance()
        nodePathField.text = settings.nodePath
        lspPathField.text = settings.lspPath
        lspPathKindCombo.selectedItem = settings.lspPathKind
        customCommandLineField.text = settings.customCommandLine
        fileExtensionsField.text = settings.fileExtensions
        clangFormatPathKindCombo.selectedItem = settings.clangFormatPathKind
        clangFormatPathField.text = settings.clangFormatPath
        clangFormatFileField.text = settings.clangFormatFile
        debugHostField.text = settings.debugHost
        debugPortField.text = settings.debugPort.toString()
        autoReconnectCheckBox.isSelected = settings.autoReconnectDebugger
        reconnectDelayField.text = settings.debugReconnectDelayMs.toString()
        logDebugMessagesCheckBox.isSelected = settings.logDebugMessages
        updateLspPathVisibility()
        updateClangFormatPathVisibility()
    }
}
