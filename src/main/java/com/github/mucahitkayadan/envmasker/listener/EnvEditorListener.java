package com.github.mucahitkayadan.envmasker.listener;

import com.github.mucahitkayadan.envmasker.file.EnvFileUtils;
import com.github.mucahitkayadan.envmasker.masking.EnvMaskerFoldingService;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
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
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnvEditorListener implements EditorFactoryListener {

    private final Map<Document, DocumentListener> documentListeners = new ConcurrentHashMap<>();
    
    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        VirtualFile file = FileDocumentManager.getInstance().getFile(editor.getDocument());

        if (EnvFileUtils.isEnvFile(file)) {
            EnvMaskerFoldingService.maskContent(editor);
            setupMouseListener(editor);
            registerDocumentListener(editor);
            
            if (project != null) {
                project.getMessageBus().connect().subscribe(
                    FileEditorManagerListener.FILE_EDITOR_MANAGER,
                    new FileEditorManagerListener() {
                        @Override
                        public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                            if (EnvFileUtils.isEnvFile(file)) {
                                FileEditor[] editors = source.getEditors(file);
                                for (FileEditor fileEditor : editors) {
                                    if (fileEditor instanceof TextEditor) {
                                        EnvMaskerFoldingService.maskContent(((TextEditor) fileEditor).getEditor());
                                    }
                                }
                            }
                        }

                        @Override
                        public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                            if (EnvFileUtils.isEnvFile(file)) {
                                for (FileEditor fileEditor : source.getEditors(file)) {
                                    if (fileEditor instanceof TextEditor) {
                                        deregisterDocumentListener(((TextEditor) fileEditor).getEditor());
                                    }
                                }
                            }
                        }

                        @Override
                        public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                            if (EnvFileUtils.isEnvFile(event.getNewFile())) {
                                FileEditor fileEditor = event.getNewEditor();
                                if (fileEditor instanceof TextEditor) {
                                    EnvMaskerFoldingService.maskContent(((TextEditor) fileEditor).getEditor());
                                }
                            }
                        }
                    }
                );
            }
        }
    }

    private void registerDocumentListener(Editor editor) {
        Document document = editor.getDocument();

        if (documentListeners.containsKey(document)) {
            return;
        }

        DocumentListener listener = new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                EnvMaskerFoldingService.maskContent(editor);
            }
        };

        document.addDocumentListener(listener);
        documentListeners.put(document, listener);
    }

    private void deregisterDocumentListener(Editor editor) {
        Document document = editor.getDocument();
        DocumentListener listener = documentListeners.remove(document);

        if (listener != null) {
            document.removeDocumentListener(listener);
        }
    }

    private void setupMouseListener(Editor editor) {
        editor.addEditorMouseListener(new EditorMouseListener() {
            @Override
            public void mouseClicked(@NotNull EditorMouseEvent e) {
                ApplicationManager.getApplication().runWriteAction(() -> {
                    int offset = e.getOffset();
                    FoldingModelEx foldingModel = (FoldingModelEx) editor.getFoldingModel();
                    
                    FoldRegion targetRegion = null;
                    for (FoldRegion region : foldingModel.getAllFoldRegions()) {
                        if (EnvMaskerFoldingService.isMaskRegion(region)
                            && offset >= region.getStartOffset()
                            && offset <= region.getEndOffset()) {
                            targetRegion = region;
                            break;
                        }
                    }

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
}
