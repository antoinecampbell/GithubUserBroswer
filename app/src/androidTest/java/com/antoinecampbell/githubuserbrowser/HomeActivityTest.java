package com.antoinecampbell.githubuserbrowser;

import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.activity.HomeActivity;
import com.robotium.solo.Solo;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUiPresent() throws InterruptedException {
        // Verify fragment container present
        onView(withId(R.id.container))
                .check(matches(isDisplayed()));

        solo.takeScreenshot("home_page");

        // Verify recyclerview present
        onView(withId(R.id.home_fragment_recyclerview))
                .check(matches(isDisplayed()));

        // Now that the network call is no longer mocked wait for data to load
        solo.waitForView(R.id.home_recyclerview_item_avatar_imageview, 1, 5000);

        // Clicking first view on main thread since only original thread is allowed to touch views.
        // View at position 0 is the RecyclerView itself
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Espresso is unable to click views within a RecyclerView based on index so using Robotium.
                // RecyclerView does not inherit from AdapterView, which allows Espresso.onData to be used
                solo.getViews(solo.getView(R.id.home_fragment_recyclerview)).get(1).performClick();
            }
        });

        // Verify imageview on detail page is present
        onView(withId(R.id.fragment_detail_avatar_imageview))
                .check(matches(isDisplayed()));

        solo.takeScreenshot("detail_page");
    }
}
