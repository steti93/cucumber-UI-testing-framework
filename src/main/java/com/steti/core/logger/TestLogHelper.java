package com.steti.core.logger;

import org.slf4j.MDC;

public class TestLogHelper {

    private static final String TEST_NAME = "testname";

    public static void startTestLogging(String name) {
        MDC.put(TEST_NAME, name);
    }

    public static void stopTestLogging() {
        MDC.remove(TEST_NAME);
    }

    public static String getCurrentLogName() {
        return MDC.get(TEST_NAME) == null ? "test" : MDC.get(TEST_NAME);
    }
}
