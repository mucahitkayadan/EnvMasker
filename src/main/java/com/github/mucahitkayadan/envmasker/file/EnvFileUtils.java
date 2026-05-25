package com.github.mucahitkayadan.envmasker.file;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EnvFileUtils {

    private EnvFileUtils() {
    }

    public static boolean isEnvFile(@Nullable VirtualFile file) {
        return file != null && !file.isDirectory() && isEnvFileName(file.getNameSequence().toString());
    }

    public static boolean isEnvFileName(@NotNull String fileName) {
        if (fileName.equals(".env") || fileName.startsWith(".env.")) {
            return true;
        }

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return false;
        }

        return fileName.substring(dotIndex + 1).equalsIgnoreCase("env");
    }
}
