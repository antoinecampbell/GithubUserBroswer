package com.antoinecampbell.githubuserbrowser.home;

import android.content.Intent;
import android.support.test.espresso.IdlingResource;
import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.robotium.solo.Solo;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * This test demonstrates using an IdlingResource to cause the test to wait until an operation is complete
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityIntent(new Intent(getInstrumentation().getTargetContext(), HomeActivity.class));
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUiPresent() throws InterruptedException {
        // Verify fragment container present
        onView(withId(R.id.activity_home_fragment_container))
                .check(matches(isDisplayed()));

        solo.takeScreenshot("home_page");

        // Verify recyclerview present
        onView(withId(R.id.fragment_home_gridview))
                .check(matches(isDisplayed()));

        // Use fragment's adapter to determine when it becomes idle by grabbing a reference to the adapter
        final HomeFragment fragment = (HomeFragment) getActivity().getSupportFragmentManager()
                .findFragmentByTag(HomeFragment.class.getSimpleName());
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

        // Long click first item in the grid
        onData(allOf(is(instanceOf(User.class)))).inAdapterView(withId(R.id.fragment_home_gridview))
                .atPosition(0).perform(longClick());

        // Verify imageview on detail page is present
        onView(withId(R.id.fragment_detail_avatar_imageview))
                .check(matches(isDisplayed()));

        solo.takeScreenshot("detail_page");

    }
}
