package com.antoinecampbell.githubuserbrowser;

import com.robotium.solo.Solo;

/**
 * Testing utility methods
 */
public class TestUtils {

    private TestUtils() {}

    /**
     * Take a screenshot
     * @param testClass test class object
     * @param solo Solo object
     * @param name screenshot name
     */
    public static void takeScreenshot(Object testClass, Solo solo, String name) {
        solo.takeScreenshot(testClass.getClass().getSimpleName()  + "_" + name);
    }
}
