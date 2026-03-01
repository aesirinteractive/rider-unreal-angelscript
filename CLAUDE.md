# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

An IntelliJ Platform plugin providing AngelScript language support, targeting IntelliJ IDEA 2025.2+ (build 252.25557+). Built with Kotlin/JVM 21 using the IntelliJ Platform Gradle Plugin v2.

## Commands

```bash
# Build the plugin
./gradlew build

# Run a sandboxed IDE instance with the plugin loaded
./gradlew runIde

# Run tests
./gradlew test

# Verify plugin compatibility
./gradlew verifyPlugin

# Build distributable ZIP
./gradlew buildPlugin
```

## Architecture

The plugin is currently in early/scaffolding state. Key entry points:

- **`plugin.xml`** (`src/main/resources/META-INF/plugin.xml`) — Declares plugin ID (`com.aesir-interactive.Angelscript`), dependencies, and extension registrations. All new extensions (language support, LSP client, file types, etc.) must be registered here.
- **`MyToolWindowFactory`** (`MyToolWindow.kt`) — Scaffold tool window registered as `MyToolWindow`. This is the only active UI component; it should be replaced or extended with real AngelScript tooling.
- **`MyMessageBundle`** — Localization bundle wrapping `messages/MyMessageBundle.properties`. Use `MyMessageBundle.message("key")` for all user-visible strings.

## Key Dependencies

- `com.intellij.modules.lsp` — LSP client support (declared in plugin.xml); intended for AngelScript language server integration
- `com.intellij.modules.json` — JSON module (bundled plugin)
- `com.intellij.properties` — Properties file support

## IntelliJ Platform Notes

- The platform version is pinned to IntelliJ IDEA `2025.2.4` in `build.gradle.kts`. Updating requires changing both `intellijIdea(...)` and `sinceBuild` in `ideaVersion`.
- Sandbox IDE logs are at `build/idea-sandbox/system/log/idea.log`.
- Gradle configuration cache and build cache are both enabled (`gradle.properties`).
- All new extensions/services/listeners must be declared in `plugin.xml` — they are not auto-discovered.

