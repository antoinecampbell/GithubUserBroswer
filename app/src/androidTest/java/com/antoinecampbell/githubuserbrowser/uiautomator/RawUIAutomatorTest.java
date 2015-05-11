package com.antoinecampbell.githubuserbrowser.uiautomator;

import android.app.Activity;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.test.InstrumentationTestCase;

import com.antoinecampbell.githubuserbrowser.TestUtils;
import com.robotium.solo.Solo;

/**
 * UIAutomator testing examples launching the app from the home screen
 */
public class RawUIAutomatorTest extends InstrumentationTestCase {

    private UiDevice device;
    private static final String APP_NAME = "Github User Browser";
    private Solo solo;

    @Override
    protected void setUp() throws Exception {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        device.pressHome();
        device.waitForIdle();
        device.pressHome();
        device.waitForIdle();

        solo = new Solo(getInstrumentation(), (Activity)null);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        device.waitForIdle();
    }

    /**
     * Test the app opens
     *
     * @throws Exception
     */
    public void testAppOpen() throws Exception {
        openApp();

        TestUtils.takeScreenshot(this, solo, "raw_ui_open_app");
    }

    /**
     * Test scrolling the grid then leaving and returning to the app
     *
     * @throws Exception
     */
    public void testGridScroll() throws Exception {
        openApp();

        // Find grid view by id
        UiScrollable grid = new UiScrollable(new UiSelector()
                .resourceId("com.antoinecampbell.githubuserbrowser:id/fragment_home_gridview"));
        // Scroll to specific user
        grid.scrollTextIntoView("dylan");

        TestUtils.takeScreenshot(this, solo, "raw_ui_grid_scroll_item");

        // Go to home screen
        device.pressHome();
        device.waitForIdle();

        // Open recent apps
        device.pressRecentApps();
        device.waitForIdle();

        Thread.sleep(1000);

        TestUtils.takeScreenshot(this, solo, "raw_ui_recent_apps");

        // Return to app
        device.findObject(By.text(APP_NAME)).click();
        device.waitForIdle();

        Thread.sleep(1000);

        TestUtils.takeScreenshot(this, solo, "raw_ui_app_return");
    }

    /**
     * Test app in airplane mode
     *
     * @throws Exception
     */
    public void testAirplaneMode() throws Exception {

        // Enable airplane mode
        device.executeShellCommand("settings put global airplane_mode_on 1");
        device.executeShellCommand(
                "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true");

        openApp();

        assertNotNull(device.findObject(By.text("Nothing to see here")));

        TestUtils.takeScreenshot(this, solo, "raw_ui_airplane_mode");

        // Disable airplane mode
        device.executeShellCommand("settings put global airplane_mode_on 0");
        device.executeShellCommand(
                "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state false");

        // Give the device time to reconnect
        Thread.sleep(5000);
    }

    /**
     * Find the app from the app drawer and launch it
     *
     * @throws Exception
     */
    private void openApp() throws Exception {
        // Open app drawer
        device.findObject(By.desc("Apps")).click();
        Thread.sleep(1000);

        // Find app under test
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        // Swipe horizontally when searching (default is vertical)
        appViews.setAsHorizontalList();

        // The appsViews will perform horizontal scrolls to find the Settings app
        UiObject app = appViews.getChildByText(
                new UiSelector().className(android.widget.TextView.class.getName()), APP_NAME);
        device.waitForIdle();
        app.clickAndWaitForNewWindow();
    }

}
