package com.antoinecampbell.githubuserbrowser.detail;

import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.TestUtils;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.test.TestFragmentActivity;
import com.robotium.solo.Solo;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Instrumentation tests for an isolated DetailFragment
 */
public class DetailFragmentTest extends ActivityInstrumentationTestCase2<TestFragmentActivity> {

    private Solo solo;

    public DetailFragmentTest() {
        super(TestFragmentActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Test UI is present
     */
    public void testUiPresent() {
        User user = new User();
        user.setLogin("test user");
        user.setAvatarUrl("https://avatars.githubusercontent.com/u/4073604?v=3");
        loadFragmentWithUser(user);

        // Ensure avatar is present
        onView(withId(R.id.fragment_detail_avatar_imageview)).check(matches(isDisplayed()));

        TestUtils.takeScreenshot(this, solo, "detail_fragment_ui_present");
    }

    /**
     * Load DetailFragment with stubbed user
     *
     * @param user User object
     */
    private void loadFragmentWithUser(User user) {
        DetailFragment fragment = DetailFragment.newInstance(user);

        // Attach fragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_test_fragment_container, fragment).commit();
    }
}
