package com.antoinecampbell.githubuserbrowser.home;

import android.support.test.espresso.IdlingResource;
import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.TestUtils;
import com.antoinecampbell.githubuserbrowser.test.AdapterViewCountMatcher;
import com.robotium.solo.Solo;


import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Instrumentation integration tests for HomeActivity
 */
public class HomeActivityTestIT extends ActivityInstrumentationTestCase2<HomeActivity> {

    private HomeFragment fragment;
    private Solo solo;


    public HomeActivityTestIT() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        fragment =
                (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag(
                        HomeFragment.class.getSimpleName());
    }

    /**
     * Test UI is present
     */
    public void testUiPresent() {
        // Verify fragment container present
        onView(withId(R.id.activity_home_fragment_container)).check(matches(isDisplayed()));

        TestUtils.takeScreenshot(this, solo, "home_activity_home_page_present");
    }

    /**
     * Test user data loads from the network
     *
     * @throws Exception
     */
    public void testUsersLoad() throws Exception {
        // Ensure view loaded
        onView(withId(R.id.activity_home_fragment_container)).check(matches(isDisplayed()));

        // Verify gridview present
        onView(withId(R.id.fragment_home_gridview)).check(matches(isDisplayed()));

        // Wait for data to load
        IdlingResource idlingResource = new IdlingResource() {

            ResourceCallback resourceCallback;

            @Override
            public String getName() {
                return fragment.getClass().getSimpleName();
            }

            @Override
            public boolean isIdleNow() {
                // Must inform Espresso resource is idle before it finds out
                if (fragment.adapter != null) {
                    resourceCallback.onTransitionToIdle();
                }
                return fragment.adapter != null;
            }

            @Override
            public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
                this.resourceCallback = resourceCallback;
            }
        };
        // Register idling resource
        registerIdlingResources(idlingResource);

        TestUtils.takeScreenshot(this, solo, "home_activity_users_present");

        // Adapter should have 50 items
        onView(withId(R.id.fragment_home_gridview)).check(matches(new AdapterViewCountMatcher(50)));

        // Must unregister or may affect following tests
        unregisterIdlingResources(idlingResource);
    }


}
