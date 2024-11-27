package com.github.mucahitkayadan.envmasker.listener;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.ex.FoldingModelEx;
import com.intellij.openapi.editor.FoldRegion;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.github.mucahitkayadan.envmasker.file.EnvFileType;
import org.jetbrains.annotations.NotNull;

public class EnvEditorListener implements EditorFactoryListener {
    
    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        VirtualFile file = FileDocumentManager.getInstance().getFile(editor.getDocument());

        if (file != null && file.getFileType() == EnvFileType.INSTANCE) {
            maskContent(editor);
            setupMouseListener(editor);
            
            if (project != null) {
                project.getMessageBus().connect().subscribe(
                    FileEditorManagerListener.FILE_EDITOR_MANAGER,
                    new FileEditorManagerListener() {
                        @Override
                        public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                            if (file.getFileType() == EnvFileType.INSTANCE) {
                                FileEditor[] editors = source.getEditors(file);
                                for (FileEditor fileEditor : editors) {
                                    if (fileEditor instanceof TextEditor) {
                                        maskContent(((TextEditor) fileEditor).getEditor());
                                    }
                                }
                            }
                        }

                        @Override
                        public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                            // No action needed
                        }

                        @Override
                        public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                            if (event.getNewFile() != null && event.getNewFile().getFileType() == EnvFileType.INSTANCE) {
                                FileEditor fileEditor = event.getNewEditor();
                                if (fileEditor instanceof TextEditor) {
                                    maskContent(((TextEditor) fileEditor).getEditor());
                                }
                            }
                        }
                    }
                );
            }
        }
    }

    private void maskContent(Editor editor) {
        ApplicationManager.getApplication().invokeLater(() -> {
            ApplicationManager.getApplication().runWriteAction(() -> {
                FoldingModelEx foldingModel = (FoldingModelEx) editor.getFoldingModel();
                foldingModel.clearFoldRegions();
                applyFolding(editor);
                
                // Ensure all regions are collapsed
                foldingModel.runBatchFoldingOperation(() -> {
                    for (FoldRegion region : foldingModel.getAllFoldRegions()) {
                        region.setExpanded(false);
                    }
                });
            });
        });
    }

    private void setupMouseListener(Editor editor) {
        editor.addEditorMouseListener(new EditorMouseListener() {
            @Override
            public void mouseClicked(@NotNull EditorMouseEvent e) {
                ApplicationManager.getApplication().runWriteAction(() -> {
                    int offset = e.getOffset();
                    FoldingModelEx foldingModel = (FoldingModelEx) editor.getFoldingModel();
                    
                    // Try to find any region at the click position
                    FoldRegion targetRegion = null;
                    for (FoldRegion region : foldingModel.getAllFoldRegions()) {
                        if (offset >= region.getStartOffset() && offset <= region.getEndOffset()) {
                            targetRegion = region;
                            break;
                        }
                    }

                    // Toggle the found region
                    if (targetRegion != null) {
                        final FoldRegion region = targetRegion;
                        foldingModel.runBatchFoldingOperation(() -> 
                            region.setExpanded(!region.isExpanded())
                        );
                    }
                });
            }
        });
    }

    private void applyFolding(Editor editor) {
        FoldingModelEx foldingModel = (FoldingModelEx) editor.getFoldingModel();
        foldingModel.runBatchFoldingOperation(() -> {
            String text = editor.getDocument().getText();
            String[] lines = text.split("\n");
            int offset = 0;

            for (String line : lines) {
                int equalIndex = line.indexOf('=');
                if (equalIndex != -1) {
                    int valueStart = offset + equalIndex + 1;
                    int valueEnd = offset + line.length();
                    if (valueEnd > valueStart) {
                        String value = line.substring(equalIndex + 1).trim();
                        if (!value.isEmpty()) {
                            FoldRegion region = foldingModel.addFoldRegion(
                                valueStart,
                                valueEnd,
                                "********"
                            );
                            if (region != null) {
                                region.setExpanded(false);
                            }
                        }
                    }
                }
                offset += line.length() + 1; // +1 for newline
            }
        });
    }
}