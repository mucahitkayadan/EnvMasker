package com.github.mucahitkayadan.envmasker.settings;

import com.github.mucahitkayadan.envmasker.masking.EnvMaskerFoldingService;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public final class EnvMaskerConfigurable implements Configurable {

    private JCheckBox maskCommentedLinesCheckbox;
    private EnvMaskerSettings settings;

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Env File Masker";
    }

    @Override
    public @Nullable JComponent createComponent() {
        settings = EnvMaskerSettings.getInstance();
        maskCommentedLinesCheckbox = new JCheckBox(
            "Mask values in commented-out lines (e.g. # API_KEY=secret)",
            settings.isMaskCommentedLines()
        );

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(maskCommentedLinesCheckbox);
        return panel;
    }

    @Override
    public boolean isModified() {
        return maskCommentedLinesCheckbox != null
            && maskCommentedLinesCheckbox.isSelected() != settings.isMaskCommentedLines();
    }

    @Override
    public void apply() {
        settings.setMaskCommentedLines(maskCommentedLinesCheckbox.isSelected());
        EnvMaskerFoldingService.refreshAllOpenEnvEditors();
    }

    @Override
    public void reset() {
        if (maskCommentedLinesCheckbox != null) {
            maskCommentedLinesCheckbox.setSelected(settings.isMaskCommentedLines());
        }
    }
}
