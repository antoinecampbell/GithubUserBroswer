package com.antoinecampbell.githubuserbrowser.uiautomator;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.home.HomeActivity;

/**
 * UIAutomator tests using ActivityInstrumentationTestCase2 for the HomeActivity
 */
public class HomePageTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private UiDevice device;
    private static final String APP_NAME = "Github User Browser";

    public HomePageTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Initialize UiDevice instance
        device = UiDevice.getInstance(getInstrumentation());

        getActivity();
    }

    /**
     * Test scrolling to items in the grid and to the end of the grid
     *
     * @throws Exception
     */
    public void testGridScroll() throws Exception {
        // Ensure app opened, check action bar title
        assertNotNull(
                device.findObject(By.pkg("com.antoinecampbell.githubuserbrowser").text(APP_NAME)));

        // Scroll thru views, scrolls vertically be default
        UiScrollable grid = new UiScrollable(new UiSelector()
                .resourceId("com.antoinecampbell.githubuserbrowser:id/fragment_home_gridview"));
        assertTrue(grid.scrollIntoView(new UiSelector().text("rdrobinson3")));
        assertTrue(grid.scrollIntoView(new UiSelector().text("jroes")));
        grid.scrollToEnd(20);
    }

}
