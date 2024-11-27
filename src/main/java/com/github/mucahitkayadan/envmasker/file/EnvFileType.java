package com.github.mucahitkayadan.envmasker.file;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class EnvFileType implements FileType {
    public static final EnvFileType INSTANCE = new EnvFileType();

    @NotNull
    @Override
    public String getName() {
        return "Env file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Environment variables file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "env";
    }

    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/icons/env.svg", getClass());
    }

    @Override
    public boolean isBinary() {
        return false;
    }

}