package com.antoinecampbell.githubuserbrowser.home;

import android.content.Intent;
import android.view.View;

import com.antoinecampbell.githubuserbrowser.BuildConfig;
import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.detail.DetailActivity;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.model.UsersResponse;
import com.antoinecampbell.githubuserbrowser.service.GithubService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;

import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.Query;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * Robolectric unit tests for HomeActivity and HomeFragment
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeActivityTest {

    private HomeActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(HomeActivity.class);
    }

    /**
     * Test UI is present
     */
    @Test
    public void testUiPresent() {
        // Check fragment container is present
        assertNotNull(activity.findViewById(R.id.activity_home_fragment_container));

        // Check fragment is present
        assertNotNull(activity.getSupportFragmentManager()
                .findFragmentByTag(HomeFragment.class.getSimpleName()));
    }

    /**
     * Test detail page intent fires on item selection
     */
    @Test
    public void testDetailPageIntentFires() {
        HomeFragment fragment = (HomeFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(HomeFragment.class.getSimpleName());
        assertNotNull(fragment);

        final UsersResponse response = new UsersResponse();
        response.setItems(Collections.singletonList(new User()));

        fragment.githubService = new GithubService() {

            @Override
            public void getCharlotteUsers(
                    @Query("page")
                    int page,
                    @Query("per_page")
                    int perPage, Callback<UsersResponse> callback) {
                callback.success(response, null);
            }

            @Override
            public void getUser(
                    @Path("username")
                    String username, Callback<User> callback) {
                callback.failure(null);
            }
        };

        // Adapter should be null
        assertNull(fragment.adapter);

        // Force fragment to call onResume, calling github service
        fragment.onResume();

        // Adapter should no longer be null
        assertNotNull(fragment.adapter);

        // Adapter should have one item
        assertEquals(1, fragment.adapter.getCount());

        // Empty view should not be visible
        assertEquals(View.GONE, fragment.emptyView.getVisibility());

        // Click first item in gridview
        fragment.onItemClick(fragment.gridView, null, 0, 0);

        // Ensure DetailActivity intent was fired
        Intent expectedIntent = new Intent(activity, DetailActivity.class);
        assertEquals(expectedIntent.getComponent(),
                shadowOf(activity).getNextStartedActivity().getComponent());
    }

    /**
     * Test empty view appears when no data is returned
     */
    @Test
    public void testEmptyResponse() {
        HomeFragment fragment = (HomeFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(HomeFragment.class.getSimpleName());
        assertNotNull(fragment);

        fragment.githubService = new GithubService() {

            @Override
            public void getCharlotteUsers(
                    @Query("page")
                    int page,
                    @Query("per_page")
                    int perPage, Callback<UsersResponse> callback) {
                callback.success(new UsersResponse(), null);
            }

            @Override
            public void getUser(
                    @Path("username")
                    String username, Callback<User> callback) {
                callback.failure(null);
            }
        };

        // Adapter should be null
        assertNull(fragment.adapter);

        // Force fragment to call onResume, calling github service
        fragment.onResume();

        // Adapter should no longer be null
        assertNotNull(fragment.adapter);

        // Adapter should have zero items
        assertEquals(0, fragment.adapter.getCount());

        // Empty view should be visible
        assertEquals(View.VISIBLE, fragment.emptyView.getVisibility());
    }
}
