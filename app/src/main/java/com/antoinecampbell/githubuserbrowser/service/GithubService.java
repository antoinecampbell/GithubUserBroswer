package com.antoinecampbell.githubuserbrowser.service;

import com.antoinecampbell.githubuserbrowser.model.UsersResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GithubService {

    @GET("/search/users?q=location:Charlotte&sort=followers&order=desc")
    void getCharlotteUsers(
            @Query("page")
            int page,
            @Query("per_page")
            int perPage, Callback<UsersResponse> callback);
}
