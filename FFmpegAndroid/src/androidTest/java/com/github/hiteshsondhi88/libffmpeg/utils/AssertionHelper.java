package com.github.hiteshsondhi88.libffmpeg.utils;

import static junit.framework.Assert.assertTrue;

@SuppressWarnings("unused")
public class AssertionHelper {

    public static void assertError(String message) {
        assertTrue(message, false);
    }

    public static void assertError() {
        assertError("");
    }

    public static void assertOK(String message) {
        assertTrue(message, true);
    }

    public static void assertOK() {
        assertOK("");
    }

}
