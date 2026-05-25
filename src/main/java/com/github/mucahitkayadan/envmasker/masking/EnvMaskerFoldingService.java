package com.github.mucahitkayadan.envmasker.masking;

import com.github.mucahitkayadan.envmasker.file.EnvFileUtils;
import com.github.mucahitkayadan.envmasker.parser.EnvLineParser;
import com.github.mucahitkayadan.envmasker.settings.EnvMaskerSettings;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.FoldRegion;
import com.intellij.openapi.editor.ex.FoldingModelEx;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public final class EnvMaskerFoldingService {

    public static final String MASK_PLACEHOLDER = "********";

    private EnvMaskerFoldingService() {
    }

    public static void maskContent(@NotNull Editor editor) {
        safeWriteAction(editor, () -> {
            FoldingModelEx foldingModel = (FoldingModelEx) editor.getFoldingModel();
            boolean maskCommentedLines = EnvMaskerSettings.getInstance().isMaskCommentedLines();

            foldingModel.runBatchFoldingOperation(() -> {
                for (FoldRegion region : foldingModel.getAllFoldRegions()) {
                    if (isMaskRegion(region)) {
                        foldingModel.removeFoldRegion(region);
                    }
                }

                String text = editor.getDocument().getText();
                String[] lines = text.split("\n", -1);
                int offset = 0;

                for (String line : lines) {
                    EnvLineParser.MaskRange range = EnvLineParser.getMaskRange(line, offset, maskCommentedLines);
                    if (range != null && !range.isEmpty()) {
                        FoldRegion region = foldingModel.addFoldRegion(range.valueStart(), range.valueEnd(), MASK_PLACEHOLDER);
                        if (region != null) {
                            region.setExpanded(false);
                        }
                    }
                    offset += line.length() + 1;
                }
            });
        });
    }

    public static boolean isMaskRegion(@NotNull FoldRegion region) {
        return MASK_PLACEHOLDER.equals(region.getPlaceholderText());
    }

    public static void refreshAllOpenEnvEditors() {
        ApplicationManager.getApplication().invokeLater(() -> {
            for (Project project : ProjectManager.getInstance().getOpenProjects()) {
                if (project.isDisposed()) {
                    continue;
                }

                FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
                for (VirtualFile file : fileEditorManager.getOpenFiles()) {
                    if (!EnvFileUtils.isEnvFile(file)) {
                        continue;
                    }

                    for (FileEditor fileEditor : fileEditorManager.getEditors(file)) {
                        if (fileEditor instanceof TextEditor textEditor) {
                            maskContent(textEditor.getEditor());
                        }
                    }
                }
            }
        });
    }

    private static void safeWriteAction(@NotNull Editor editor, @NotNull Runnable action) {
        ApplicationManager.getApplication().invokeLater(() -> {
            if (editor.isDisposed()) {
                return;
            }

            ApplicationManager.getApplication().runWriteAction(() -> {
                if (editor.isDisposed()) {
                    return;
                }
                action.run();
            });
        });
    }
}
