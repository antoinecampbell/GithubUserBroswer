package com.antoinecampbell.githubuserbrowser.home;

import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.antoinecampbell.githubuserbrowser.R;
import com.antoinecampbell.githubuserbrowser.TestUtils;
import com.antoinecampbell.githubuserbrowser.detail.DetailActivity;
import com.antoinecampbell.githubuserbrowser.model.User;
import com.antoinecampbell.githubuserbrowser.model.UsersResponse;
import com.antoinecampbell.githubuserbrowser.service.GithubService;
import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;
import com.antoinecampbell.githubuserbrowser.test.AdapterViewCountMatcher;
import com.antoinecampbell.githubuserbrowser.test.TestFragmentActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotium.solo.Solo;

import retrofit.Callback;
import retrofit.http.Path;
import retrofit.http.Query;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.hamcrest.Matchers.*;

/**
 * Instrumentation test for an isolated HomeFragment
 */
public class HomeFragmentTest extends ActivityInstrumentationTestCase2<TestFragmentActivity> {

    private Solo solo;
    private UsersResponse response;

    public HomeFragmentTest() {
        super(TestFragmentActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

        // Use sample response stored in assets file instead of calling service
        ObjectMapper mapper = ServiceUtils.getObjectMapper();
        response = mapper.readValue(getActivity().getAssets().open("response.json"),
                UsersResponse.class);

    }

    /**
     * Test UI is present
     */
    public void testUiPresent() {
        loadFragmentWithResponse(response);

        // Ensure view loaded
        onView(withId(R.id.activity_test_fragment_container)).check(matches(isDisplayed()));

        // Verify gridview present
        onView(withId(R.id.fragment_home_gridview)).check(matches(isDisplayed()));

        TestUtils.takeScreenshot(this, solo, "home_page");
    }

    /**
     * Test UI with single item present
     */
    public void testSingleItem() {
        loadFragmentWithResponse(response);

        // Adapter should have only one item
        onView(withId(R.id.fragment_home_gridview)).check(matches(new AdapterViewCountMatcher(1)));

        TestUtils.takeScreenshot(this, solo, "home_page_single_item");
    }

    /**
     * Test detail page is loaded when item is clicked
     */
    public void testDetailPageLoad() {
        loadFragmentWithResponse(response);

        // Click the first item in the list
        onData(allOf(is(instanceOf(User.class)))).inAdapterView(withId(R.id.fragment_home_gridview))
                .atPosition(0).perform(longClick());

        boolean detailActivityLoaded = solo.waitForActivity(DetailActivity.class, 5000);
        assertTrue(detailActivityLoaded);

        // Ensure the detail activity intent was fired once
        assertEquals(1, solo.getActivityMonitor().getHits());

        TestUtils.takeScreenshot(this, solo, "home_page_load_detail");
    }

    /**
     * Test empty view appears when no data is returned from service
     */
    public void testEmptyResponse() {
        loadFragmentWithResponse(new UsersResponse());

        // Ensure view loaded
        onView(withId(R.id.activity_test_fragment_container)).check(matches(isDisplayed()));

        // Empty view should be present
        onView(withId(android.R.id.empty)).check(matches(isDisplayed()));

        TestUtils.takeScreenshot(this, solo, "home_page_empty_list");
    }

    /**
     * Load fragment with stubbed UserResponse
     *
     * @param response UserResponse object
     */
    private void loadFragmentWithResponse(final UsersResponse response) {
        // Override the fragment to replace service implementation
        HomeFragment fragment = new HomeFragment() {

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                githubService = new GithubService() {

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
                    }
                };
            }
        };
        // Attach fragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_test_fragment_container, fragment).commit();
    }


}
