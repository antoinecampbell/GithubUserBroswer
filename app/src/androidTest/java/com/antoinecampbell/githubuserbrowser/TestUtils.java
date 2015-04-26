package com.antoinecampbell.githubuserbrowser;

import com.robotium.solo.Solo;

public class TestUtils {

    private TestUtils() {}

    public static void takeScreenshot(Object testClass, Solo solo, String name) {
        solo.takeScreenshot(testClass.getClass().getSimpleName()  + "_" + name);
    }
}
