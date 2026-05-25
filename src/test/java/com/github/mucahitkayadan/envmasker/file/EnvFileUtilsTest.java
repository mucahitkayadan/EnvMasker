package com.github.mucahitkayadan.envmasker.file;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnvFileUtilsTest {

    @Test
    void recognizesDotEnvFiles() {
        assertTrue(EnvFileUtils.isEnvFileName(".env"));
        assertTrue(EnvFileUtils.isEnvFileName(".env.local"));
        assertTrue(EnvFileUtils.isEnvFileName(".env.production"));
    }

    @Test
    void recognizesEnvExtensionFiles() {
        assertTrue(EnvFileUtils.isEnvFileName("config.env"));
        assertTrue(EnvFileUtils.isEnvFileName("CONFIG.ENV"));
    }

    @Test
    void rejectsUnrelatedFiles() {
        assertFalse(EnvFileUtils.isEnvFileName("environment.txt"));
        assertFalse(EnvFileUtils.isEnvFileName(".environment"));
        assertFalse(EnvFileUtils.isEnvFileName("env"));
        assertFalse(EnvFileUtils.isEnvFileName("docker-compose.yml"));
    }
}
