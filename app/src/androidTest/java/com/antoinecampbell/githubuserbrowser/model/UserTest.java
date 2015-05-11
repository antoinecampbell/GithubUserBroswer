package com.antoinecampbell.githubuserbrowser.model;

import android.os.Parcel;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Unit tests for User
 */
public class UserTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test user parcel is created correctly
     */
    @SmallTest
    public void testUserParcel() {
        User user = new User();
        user.setLogin("login");
        user.setId(1);
        user.setAvatarUrl("avatarUrl");
        user.setPublicRepos(2);
        user.setPublicGists(3);
        user.setFollowers(4);
        user.setFollowing(5);
        user.setUrl("url");
        user.setFollowersUrl("followersUrl");
        user.setFollowingUrl("followingUrl");

        Parcel parcel = Parcel.obtain();
        user.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        User newUser = User.CREATOR.createFromParcel(parcel);

        assertEquals(user.getLogin(), newUser.getLogin());
        assertEquals(user.getId(), newUser.getId());
        assertEquals(user.getAvatarUrl(), newUser.getAvatarUrl());
        assertEquals(user.getPublicRepos(), newUser.getPublicRepos());
        assertEquals(newUser.getPublicGists(), newUser.getPublicGists());
        assertEquals(user.getFollowers(), newUser.getFollowers());
        assertEquals(user.getFollowing(), newUser.getFollowing());
        assertEquals(user.getUrl(), newUser.getUrl());
        assertEquals(user.getFollowersUrl(), newUser.getFollowersUrl());
        assertEquals(user.getFollowingUrl(), newUser.getFollowingUrl());

        assertTrue(user.equals(newUser));
        assertEquals(user.hashCode(), newUser.hashCode());
    }

}
