package com.steti.core.assertions;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public class STAssertions {
    private static final String MESSAGE_EXPECTED_ACTUAL = " {}{} - expected: {}{} - actual: {}";
    private static Logger logger = LoggerFactory.getLogger(STAssertions.class);


    public static void assertTrue(String message, boolean condition) {
        String fullMessage = "Assert that " + message;
        logger.info(MESSAGE_EXPECTED_ACTUAL, fullMessage, System.lineSeparator(), Boolean.TRUE, System.lineSeparator(), condition);
        Assert.assertTrue(fullMessage, condition);
    }

    public static void assertFalse(String message, boolean condition) {
        String fullMessage = "Assert that " + message;
        logger.info(MESSAGE_EXPECTED_ACTUAL, fullMessage, System.lineSeparator(), Boolean.FALSE, System.lineSeparator(), condition);
        Assert.assertFalse(fullMessage, condition);
    }

    public static <T> void assertThat(String message, T actual, Matcher<? super T> matcher) {
        assertThat(message, "", actual, matcher);
    }

    public static <T> void assertThat(String logMessage, String failureMessage, T actual, Matcher<? super T> matcher) {
        String fullMessage = "Assert that " + logMessage;
        logger.info(MESSAGE_EXPECTED_ACTUAL, fullMessage, System.lineSeparator(), matcher.toString(), System.lineSeparator(), actual);
        if (StringUtils.isEmpty(failureMessage)) {
            Assert.assertThat(actual, matcher);
        } else {
            Assert.assertThat(failureMessage, actual, matcher);
        }
    }

    public static <T> void assertThat(String message, List<T> param, String failureMessage, T actual, Matcher<? super T> matcher) {
        logger.info(message, param);
        assertThat(message, failureMessage, actual, matcher);
    }
}
