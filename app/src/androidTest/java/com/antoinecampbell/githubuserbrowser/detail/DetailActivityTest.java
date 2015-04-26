package com.antoinecampbell.githubuserbrowser.detail;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.TestUtils;
import com.antoinecampbell.githubuserbrowser.home.HomeActivity;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;
import com.robotium.solo.Solo;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

public class DetailActivityTest extends ActivityInstrumentationTestCase2<DetailActivity> {

    private User user;
    private Solo solo;

    public DetailActivityTest() {
        super(DetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String userString =
                "{   \"login\": \"antoinecampbell\",   \"id\": 4073604,   \"avatar_url\": \"https://avatars.githubusercontent.com/u/4073604?v=3\",   \"gravatar_id\": \"\",   \"url\": \"https://api.github.com/users/antoinecampbell\",   \"html_url\": \"https://github.com/antoinecampbell\",   \"followers_url\": \"https://api.github.com/users/antoinecampbell/followers\",   \"following_url\": \"https://api.github.com/users/antoinecampbell/following{/other_user}\",   \"gists_url\": \"https://api.github.com/users/antoinecampbell/gists{/gist_id}\",   \"starred_url\": \"https://api.github.com/users/antoinecampbell/starred{/owner}{/repo}\",   \"subscriptions_url\": \"https://api.github.com/users/antoinecampbell/subscriptions\",   \"organizations_url\": \"https://api.github.com/users/antoinecampbell/orgs\",   \"repos_url\": \"https://api.github.com/users/antoinecampbell/repos\",   \"events_url\": \"https://api.github.com/users/antoinecampbell/events{/privacy}\",   \"received_events_url\": \"https://api.github.com/users/antoinecampbell/received_events\",   \"type\": \"User\",   \"site_admin\": false,   \"name\": \"Antoine Campbell\",   \"company\": \"\",   \"blog\": \"\",   \"location\": \"Charlotte, NC\",   \"email\": \"\",   \"hireable\": false,   \"bio\": null,   \"public_repos\": 4,   \"public_gists\": 1,   \"followers\": 8,   \"following\": 2,   \"created_at\": \"2013-04-05T23:22:20Z\",   \"updated_at\": \"2015-04-19T14:17:18Z\" } ";
        user = ServiceUtils.getObjectMapper().readValue(userString, User.class);

        // Set intent extras
        Bundle args = DetailActivity.newInstanceBundle(user);
        setActivityIntent(new Intent(getInstrumentation().getTargetContext(), DetailActivity.class)
                .putExtras(args));

        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUiPresent() {
        // Verify fragment container present
        onView(withId(R.id.activity_detail_fragment_container)).check(matches(isDisplayed()));

        // Action bar should have users login
        String title = getActivity().getTitle().toString();
        assertEquals(title, user.getLogin());

        TestUtils.takeScreenshot(this, solo, "detail_activity");
    }

    public void testUpNavigation() {
        // Remove Robotium activity monitor, it blocks other monitors from being hit
        getInstrumentation().removeMonitor(solo.getActivityMonitor());

        Instrumentation.ActivityMonitor am =
                getInstrumentation().addMonitor(HomeActivity.class.getName(), null, false);

        // Click the up button, view cannot be found by id android.R.id.home
        onView(withContentDescription("Navigate up")).perform(click());

        HomeActivity homeActivity = (HomeActivity) am.waitForActivityWithTimeout(5000);
        assertNotNull(homeActivity);
        assertEquals(HomeActivity.class, homeActivity.getClass());

        // Ensure activity monitor was hit one
        assertEquals(1, am.getHits());

        getInstrumentation().removeMonitor(am);

        TestUtils.takeScreenshot(this, solo, "detail_activity_up_navigation");
    }
}
