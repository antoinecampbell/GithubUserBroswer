package com.antoinecampbell.githubuserbrowser.model;

import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testUserSuccessfulParse() throws Exception {
        String json =
                "{\"login\":\"antoinecampbell\",\"id\":4073604,\"avatar_url\":\"https://avatars.githubusercontent.com/u/4073604?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/antoinecampbell\",\"html_url\":\"https://github.com/antoinecampbell\",\"followers_url\":\"https://api.github.com/users/antoinecampbell/followers\",\"following_url\":\"https://api.github.com/users/antoinecampbell/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/antoinecampbell/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/antoinecampbell/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/antoinecampbell/subscriptions\",\"organizations_url\":\"https://api.github.com/users/antoinecampbell/orgs\",\"repos_url\":\"https://api.github.com/users/antoinecampbell/repos\",\"events_url\":\"https://api.github.com/users/antoinecampbell/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/antoinecampbell/received_events\",\"type\":\"User\",\"site_admin\":false,\"name\":\"AntoineCampbell\",\"company\":\"\",\"blog\":\"\",\"location\":\"Charlotte,NC\",\"email\":\"\",\"hireable\":false,\"bio\":null,\"public_repos\":4,\"public_gists\":1,\"followers\":8,\"following\":2,\"created_at\":\"2013-04-05T23:22:20Z\",\"updated_at\":\"2015-04-21T22:19:17Z\"}";

        User user = new User();
        user.setLogin("antoinecampbell");
        user.setId(4073604);
        user.setAvatarUrl("https://avatars.githubusercontent.com/u/4073604?v=3");
        user.setPublicRepos(4);
        user.setPublicGists(1);
        user.setFollowers(8);
        user.setFollowing(2);
        user.setUrl("https://api.github.com/users/antoinecampbell");
        user.setFollowersUrl("https://api.github.com/users/antoinecampbell/followers");
        user.setFollowingUrl("https://api.github.com/users/antoinecampbell/following{/other_user}");

        User parsedUser = ServiceUtils.getObjectMapper().readValue(json, User.class);

        assertEquals(user.getLogin(), parsedUser.getLogin());
        assertEquals(user.getId(), parsedUser.getId());
        assertEquals(user.getAvatarUrl(), parsedUser.getAvatarUrl());
        assertEquals(user.getPublicRepos(), parsedUser.getPublicRepos());
        assertEquals(parsedUser.getPublicGists(), parsedUser.getPublicGists());
        assertEquals(user.getFollowers(), parsedUser.getFollowers());
        assertEquals(user.getFollowing(), parsedUser.getFollowing());
        assertEquals(user.getUrl(), parsedUser.getUrl());
        assertEquals(user.getFollowersUrl(), parsedUser.getFollowersUrl());
        assertEquals(user.getFollowingUrl(), parsedUser.getFollowingUrl());

        assertTrue(user.equals(parsedUser));
        assertEquals(user.hashCode(), parsedUser.hashCode());
    }

}
