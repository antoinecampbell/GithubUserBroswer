package com.antoinecampbell.githubuserbrowser.home;

import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.TestUtils;
import com.robotium.solo.Solo;

import java.io.IOException;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

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

    public void testUiPresent() throws IOException {
        TestUtils.takeScreenshot(this, solo, "home_page");

        // Verify fragment container present
        onView(withId(R.id.activity_home_fragment_container)).check(matches(isDisplayed()));
    }
}
