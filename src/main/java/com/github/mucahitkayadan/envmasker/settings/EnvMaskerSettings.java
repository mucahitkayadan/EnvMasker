package com.github.mucahitkayadan.envmasker.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(name = "EnvMaskerSettings", storages = @Storage("EnvMasker.xml"))
public final class EnvMaskerSettings implements PersistentStateComponent<EnvMaskerSettings.State> {

    public static final class State {
        public boolean maskCommentedLines = true;
    }

    private State state = new State();

    public static @NotNull EnvMaskerSettings getInstance() {
        return ApplicationManager.getApplication().getService(EnvMaskerSettings.class);
    }

    public boolean isMaskCommentedLines() {
        return state.maskCommentedLines;
    }

    public void setMaskCommentedLines(boolean maskCommentedLines) {
        state.maskCommentedLines = maskCommentedLines;
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        XmlSerializerUtil.copyBean(state, this.state);
    }
}
