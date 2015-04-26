package com.antoinecampbell.githubuserbrowser.home;

import android.support.test.espresso.IdlingResource;
import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.test.AdapterViewCountMatcher;
import com.antoinecampbell.githubuserbrowser.test.TestFragmentActivity;
import com.robotium.solo.Solo;


import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

public class HomeFragmentTestIT extends ActivityInstrumentationTestCase2<TestFragmentActivity> {

    private HomeFragment fragment;
    private TestFragmentActivity activity;
    private Solo solo;


    public HomeFragmentTestIT() {
        super(TestFragmentActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

        fragment = HomeFragment.newInstance();
        activity = getActivity();
    }

    public void testUiPresent() throws Exception {
        // Ensure view loaded
        onView(withId(R.id.activity_test_fragment_container)).check(matches(isDisplayed()));

        // Attach fragment
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_test_fragment_container, fragment).commit();

        // Verify gridview present
        onView(withId(R.id.fragment_home_gridview)).check(matches(isDisplayed()));

        // Wait for data to load
        registerIdlingResources(new IdlingResource() {

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
        });

        solo.takeScreenshot("home_fragment_it");

        // Adapter should have only one item
        onView(withId(R.id.fragment_home_gridview)).check(matches(new AdapterViewCountMatcher(50)));
    }



}
