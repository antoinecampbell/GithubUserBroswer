package com.antoinecampbell.githubuserbrowser.model;

import com.antoinecampbell.githubuserbrowser.service.ServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserResponseTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = ServiceUtils.getObjectMapper();
    }

    @Test
    public void testUserResponseSuccessfulParse() throws IOException {
        String json = "{  \"total_count\": 226,  \"incomplete_results\": false,  \"items\": [    {      \"login\": \"thomasgriffin\",      \"id\": 909318,      \"avatar_url\": \"https://avatars.githubusercontent.com/u/909318?v=3\",      \"gravatar_id\": \"\",      \"url\": \"https://api.github.com/users/thomasgriffin\",      \"html_url\": \"https://github.com/thomasgriffin\",      \"followers_url\": \"https://api.github.com/users/thomasgriffin/followers\",      \"following_url\": \"https://api.github.com/users/thomasgriffin/following{/other_user}\",      \"gists_url\": \"https://api.github.com/users/thomasgriffin/gists{/gist_id}\",      \"starred_url\": \"https://api.github.com/users/thomasgriffin/starred{/owner}{/repo}\",      \"subscriptions_url\": \"https://api.github.com/users/thomasgriffin/subscriptions\",      \"organizations_url\": \"https://api.github.com/users/thomasgriffin/orgs\",      \"repos_url\": \"https://api.github.com/users/thomasgriffin/repos\",      \"events_url\": \"https://api.github.com/users/thomasgriffin/events{/privacy}\",      \"received_events_url\": \"https://api.github.com/users/thomasgriffin/received_events\",      \"type\": \"User\",      \"site_admin\": false,      \"score\": 1.0    }  ]}";
        UsersResponse response = mapper.readValue(json, UsersResponse.class);

        assertNotNull(response);
        assertNotNull(response.getItems());

    }
}
