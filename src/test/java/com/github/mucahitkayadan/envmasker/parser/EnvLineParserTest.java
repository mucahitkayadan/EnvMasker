package com.github.mucahitkayadan.envmasker.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvLineParserTest {

    @Test
    void masksStandardAssignment() {
        EnvLineParser.MaskRange range = EnvLineParser.getMaskRange("DB_PASSWORD=secret", 0, true);
        assertNotNull(range);
        assertEquals(12, range.valueStart());
        assertEquals(18, range.valueEnd());
        assertEquals("secret", range.value("DB_PASSWORD=secret"));
    }

    @Test
    void skipsPureCommentLines() {
        assertNull(EnvLineParser.getMaskRange("# Database Configuration", 0, true));
        assertNull(EnvLineParser.getMaskRange("# Database Configuration", 0, false));
    }

    @Test
    void masksCommentedOutAssignmentWhenEnabled() {
        EnvLineParser.MaskRange range = EnvLineParser.getMaskRange("# API_KEY=secret123", 0, true);
        assertNotNull(range);
        assertEquals("secret123", range.value("# API_KEY=secret123"));
    }

    @Test
    void skipsCommentedOutAssignmentWhenDisabled() {
        assertNull(EnvLineParser.getMaskRange("# API_KEY=secret123", 0, false));
    }

    @Test
    void excludesInlineCommentFromMaskedValue() {
        EnvLineParser.MaskRange range = EnvLineParser.getMaskRange("DB_HOST=localhost # dev server", 0, true);
        assertNotNull(range);
        assertEquals("localhost", range.value("DB_HOST=localhost # dev server"));
    }

    @Test
    void keepsHashInUnquotedValueWithoutLeadingSpace() {
        EnvLineParser.MaskRange range = EnvLineParser.getMaskRange("MESSAGE=hello#world", 0, true);
        assertNotNull(range);
        assertEquals("hello#world", range.value("MESSAGE=hello#world"));
    }

    @Test
    void handlesQuotedValuesWithHashInside() {
        EnvLineParser.MaskRange range = EnvLineParser.getMaskRange("KEY=\"value # with hash\"", 0, true);
        assertNotNull(range);
        assertEquals("\"value # with hash\"", range.value("KEY=\"value # with hash\""));
    }

    @Test
    void skipsEmptyValues() {
        assertNull(EnvLineParser.getMaskRange("KEY=", 0, true));
        assertNull(EnvLineParser.getMaskRange("KEY=   ", 0, true));
    }

    @Test
    void respectsLineOffset() {
        EnvLineParser.MaskRange range = EnvLineParser.getMaskRange("API_KEY=abc", 10, true);
        assertNotNull(range);
        assertEquals(18, range.valueStart());
        assertEquals(21, range.valueEnd());
    }
}
