package com.aesirinteractive.angelscript

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

enum class LspPathKind { Bundled, VsCode, Custom }

@Service(Service.Level.APP)
@State(name = "AngelscriptSettings", storages = [Storage("angelscript.xml")])
class AngelscriptSettings : PersistentStateComponent<AngelscriptSettings.State> {

    data class State(
        var nodePath: String = "node",
        var lspPath: String = "",
        var lspPathKind: LspPathKind = LspPathKind.Bundled
    )

    private var state = State()

    override fun getState() = state

    override fun loadState(state: State) {
        this.state = state
    }

    var nodePath: String
        get() = state.nodePath
        set(value) { state.nodePath = value }

    var lspPath: String
        get() = state.lspPath
        set(value) { state.lspPath = value }

    var lspPathKind: LspPathKind
        get() = state.lspPathKind
        set(value) { state.lspPathKind = value }

    companion object {
        fun getInstance(): AngelscriptSettings =
            ApplicationManager.getApplication().getService(AngelscriptSettings::class.java)
    }
}
