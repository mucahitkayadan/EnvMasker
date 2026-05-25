package com.github.mucahitkayadan.envmasker.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EnvLineParser {

    private EnvLineParser() {
    }

    public record MaskRange(int valueStart, int valueEnd) {
        public boolean isEmpty() {
            return valueEnd <= valueStart;
        }

        @NotNull
        public String value(@NotNull String line) {
            return line.substring(valueStart, valueEnd).trim();
        }
    }

    public static @Nullable MaskRange getMaskRange(@NotNull String line, int lineOffset, boolean maskCommentedLines) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        boolean isCommentedOut = trimmed.startsWith("#");
        if (isCommentedOut && !maskCommentedLines) {
            return null;
        }

        int equalIndex = line.indexOf('=');
        if (equalIndex == -1) {
            return null;
        }

        int valueEndInLine = computeValueEnd(line, equalIndex);
        int valueStart = lineOffset + equalIndex + 1;
        int valueEnd = lineOffset + valueEndInLine;
        if (valueEnd <= valueStart) {
            return null;
        }

        if (line.substring(equalIndex + 1, valueEndInLine).trim().isEmpty()) {
            return null;
        }

        return new MaskRange(valueStart, valueEnd);
    }

    static int computeValueEnd(@NotNull String line, int equalIndex) {
        int i = equalIndex + 1;
        int length = line.length();

        while (i < length && line.charAt(i) == ' ') {
            i++;
        }

        if (i >= length) {
            return i;
        }

        char quote = line.charAt(i);
        if (quote == '"' || quote == '\'') {
            i++;
            while (i < length) {
                char current = line.charAt(i);
                if (current == '\\' && i + 1 < length) {
                    i += 2;
                    continue;
                }
                if (current == quote) {
                    i++;
                    break;
                }
                i++;
            }
            return trimTrailingWhitespace(line, i);
        }

        for (int j = i; j < length; j++) {
            if (line.charAt(j) == '#' && j > i && line.charAt(j - 1) == ' ') {
                return trimTrailingWhitespace(line, j - 1);
            }
        }

        return trimTrailingWhitespace(line, length);
    }

    private static int trimTrailingWhitespace(@NotNull String line, int end) {
        while (end > 0 && line.charAt(end - 1) == ' ') {
            end--;
        }
        return end;
    }
}
