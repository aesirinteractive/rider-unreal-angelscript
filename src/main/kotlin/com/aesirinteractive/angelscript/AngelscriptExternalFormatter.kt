package com.aesirinteractive.angelscript

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.ExternalFormatProcessor
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.io.ByteArrayInputStream
import java.util.concurrent.TimeUnit
import javax.xml.parsers.SAXParserFactory

class AngelscriptExternalFormatter : ExternalFormatProcessor {

    companion object {
        private val FORMAT_STRING_PREFIX = Regex("""^[A-Za-z_]['"].*""")
        private val IN_OUT_REF           = Regex("""^.?&(in|out).*""")
        private val START_LINE_TYPED     = Regex("""^\s*\w+\s+\w+\s*\(.*""")
        private val START_LINE_TYPE_ONLY = Regex("""^\s*\w+\r?\n.*""")
        private val ACCESS_LINE          = Regex("""^\s*access\s*:.*""")

        private data class KeywordRules(
            val hasIdent: Regex,
            val splitAtKeyword: Regex,
            val splitAtIdent: Regex
        )

        private val KEYWORD_RULES: List<KeywordRules> =
            listOf("private", "protected", "delegate", "event").map { kw ->
                KeywordRules(
                    hasIdent       = Regex("""^\s*$kw\s+\w+.*"""),
                    splitAtKeyword = Regex("""^\s*$kw\r?\n.*"""),
                    splitAtIdent   = Regex("""^\s*$kw\s+\w+\r?\n.*""")
                )
            }
    }

    override fun getId(): String = "ANGELSCRIPT_CLANGD_FORMATTER"

    override fun activeForFile(source: PsiFile): Boolean =
        source.language == AngelscriptLanguage.INSTANCE

    override fun indent(p0: PsiFile, p1: Int): String? {
        return null
    }

    override fun format(
        psi: PsiFile,
        range: TextRange,
        canChangeWhiteSpacesOnly: Boolean,
        keepLineBreaks: Boolean,
        p4: Boolean,
        p5: Int
    ): TextRange? {
        val vf = psi.virtualFile!!
        val document = FileDocumentManager.getInstance().getDocument(vf) ?: return null
        val project = psi.project
        val content = document.text

        val clangFormatPath = AngelscriptSettings.getInstance().clangFormatPath.ifBlank { "clang-format" }
        val filename = vf.path

        val xmlOutput = runClangFormat(clangFormatPath, filename, content) ?: return null
        val replacements = parseReplacements(xmlOutput).filter { (offset, length, text) ->
            allowEdit(content, offset, offset + length, text)
        }

        if (replacements.isEmpty()) return range

        // Apply from end to start so earlier offsets remain valid
        val sorted = replacements.sortedByDescending { it.first }

        WriteCommandAction.runWriteCommandAction(project) {
            for ((offset, length, text) in sorted) {
                if (offset < 0 || offset + length > document.textLength) continue
                document.replaceString(offset, offset + length, text)
            }
        }

        return range
    }

    private fun allowEdit(content: String, startOffset: Int, endOffset: Int, replacementText: String): Boolean {
        val lineStart = content.lastIndexOf('\n', startOffset - 1) + 1
        val lineEnd = content.indexOf('\n', startOffset).let { if (it == -1) content.length else it }
        val startLine = content.substring(lineStart, lineEnd)
        val startColumn = startOffset - lineStart

        val endLineStart = content.lastIndexOf('\n', endOffset - 1) + 1
        val isSameLine = endLineStart == lineStart

        if (isSameLine) {
            val endColumn = endOffset - lineStart

            // 1. Column bounds check
            if (startColumn > startLine.length || endColumn > startLine.length) return false

            val startLineAfterEdit = startLine.substring(0, startColumn) +
                replacementText +
                startLine.substring(endColumn.coerceAtMost(startLine.length))

            if (startOffset == endOffset && startColumn > 0) {
                val context = startLine.substring(startColumn - 1)
                // 2. Format-string prefix guard: prevent space between prefix letter and quote
                if (FORMAT_STRING_PREFIX.containsMatchIn(context)) return false
                // 3. &in / &out guard: prevent space between & and in/out
                if (IN_OUT_REF.containsMatchIn(context)) return false
            }

            if ('\n' in replacementText) {
                // 4. Return-type newline guard
                if (START_LINE_TYPED.matches(startLine) && START_LINE_TYPE_ONLY.matches(startLineAfterEdit))
                    return false

                // 5. Keyword + identifier newline guard
                for (kw in KEYWORD_RULES) {
                    if (kw.hasIdent.matches(startLine) &&
                        (kw.splitAtKeyword.matches(startLineAfterEdit) || kw.splitAtIdent.matches(startLineAfterEdit)))
                        return false
                }
            }
        }

        // 6. access: line guard
        if (ACCESS_LINE.matches(startLine)) return false

        return true
    }

    private fun runClangFormat(executable: String, filename: String, content: String): String? {
        return try {
            val process = ProcessBuilder(executable, "--output-replacements-xml", "--assume-filename=$filename")
                .redirectErrorStream(false)
                .start()

            process.outputStream.bufferedWriter().use { it.write(content) }

            val stdout = process.inputStream.bufferedReader().readText()
            val stderr = process.errorStream.bufferedReader().readText()

            val exited = process.waitFor(10, TimeUnit.SECONDS)
            if (!exited) {
                process.destroyForcibly()
                thisLogger().warn("clang-format timed out for $filename")
                return null
            }

            if (process.exitValue() != 0) {
                thisLogger().warn("clang-format exited with ${process.exitValue()} for $filename: $stderr")
                return null
            }

            stdout
        } catch (e: Exception) {
            thisLogger().warn("Failed to run clang-format: ${e.message}")
            null
        }
    }

    // Returns list of (offset, length, replacementText)
    private fun parseReplacements(xml: String): List<Triple<Int, Int, String>> {
        val results = mutableListOf<Triple<Int, Int, String>>()
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()

            var currentOffset = 0
            var currentLength = 0
            val currentText = StringBuilder()
            var inReplacement = false

            val handler = object : DefaultHandler() {
                override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
                    if (qName.equals("replacement", ignoreCase = true)) {
                        currentOffset = attributes.getValue("offset")?.toIntOrNull() ?: 0
                        currentLength = attributes.getValue("length")?.toIntOrNull() ?: 0
                        currentText.clear()
                        inReplacement = true
                    }
                }

                override fun characters(ch: CharArray, start: Int, length: Int) {
                    if (inReplacement) currentText.append(ch, start, length)
                }

                override fun endElement(uri: String, localName: String, qName: String) {
                    if (qName.equals("replacement", ignoreCase = true)) {
                        results.add(Triple(currentOffset, currentLength, currentText.toString()))
                        inReplacement = false
                    }
                }
            }

            parser.parse(ByteArrayInputStream(xml.toByteArray(Charsets.UTF_8)), handler)
        } catch (e: Exception) {
            thisLogger().warn("Failed to parse clang-format XML: ${e.message}")
        }
        return results
    }
}
