package org.example.envmasker.listener

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import com.intellij.openapi.editor.event.EditorMouseEvent
import com.intellij.openapi.editor.event.EditorMouseListener
import com.intellij.openapi.editor.ex.FoldingModelEx
import com.intellij.openapi.fileEditor.FileDocumentManager
import org.example.envmasker.file.EnvFileType

class EnvEditorListener : EditorFactoryListener {
    private val LOG = Logger.getInstance(EnvEditorListener::class.java)

    override fun editorCreated(event: EditorFactoryEvent) {
        val editor = event.editor
        val document = editor.document
        val file = FileDocumentManager.getInstance().getFile(document)
        
        if (file?.fileType == EnvFileType) {
            LOG.info("Applying folding for .env file")
            applyFolding(editor)
            
            editor.addEditorMouseListener(object : EditorMouseListener {
                override fun mouseClicked(e: EditorMouseEvent) {
                    val offset = e.offset
                    val foldingModel = editor.foldingModel as FoldingModelEx
                    val region = foldingModel.getCollapsedRegionAtOffset(offset)
                    
                    if (region != null && region.isExpanded) {
                        foldingModel.runBatchFoldingOperation {
                            region.isExpanded = false
                        }
                    }
                }
            })
        }
    }

    private fun applyFolding(editor: Editor) {
        val document = editor.document
        val foldingModel = editor.foldingModel as FoldingModelEx
        
        foldingModel.runBatchFoldingOperation {
            var offset = 0
            val text = document.text
            val lines = text.lines()
            
            for (line in lines) {
                val equalIndex = line.indexOf('=')
                
                if (equalIndex != -1) {
                    val valueStart = offset + equalIndex + 1
                    val valueEnd = offset + line.length
                    
                    LOG.info("Line: '$line', offset: $offset, valueStart: $valueStart, valueEnd: $valueEnd")
                    
                    if (valueEnd > valueStart && line.substring(equalIndex + 1).isNotEmpty()) {
                        val region = foldingModel.addFoldRegion(
                            valueStart,
                            valueEnd,
                            "********"
                        )
                        region?.isExpanded = false
                    }
                }
                
                offset += line.length + 1  // +1 for newline
            }
        }
    }
} 