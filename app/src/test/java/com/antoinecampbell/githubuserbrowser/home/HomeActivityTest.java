package com.antoinecampbell.githubuserbrowser.home;

import com.antoinecampbell.githubuserbrowser.BuildConfig;
import com.antoinecampbell.githubuserbrowser.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeActivityTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testUiPresent() {
        HomeActivity activity =
                Robolectric.buildActivity(HomeActivity.class).create().visible().get();

        // Check fragment container is present
        assertNotNull(activity.findViewById(R.id.activity_home_fragment_container));

        // Check fragment is present
        assertNotNull(activity.getSupportFragmentManager()
                .findFragmentByTag(HomeFragment.class.getSimpleName()));
    }
}
