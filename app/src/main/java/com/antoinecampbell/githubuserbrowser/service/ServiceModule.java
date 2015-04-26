package com.antoinecampbell.githubuserbrowser.service;

import com.antoinecampbell.githubuserbrowser.BuildConfig;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

@Module
public class ServiceModule {

    private static final String GITHUB_SERVICE_ENDPOINT = "https://api.github.com";
    private static final String GITHUB_SERVICE_VERSION = "application/vnd.github.v3+json";
    private static final String GITHUB_SERVICE_USER_AGENT = "com.antoinecampbell.githubuserbrowse";

    @Provides
    @Inject
    public GithubService provideGithubService(RestAdapter restAdapter) {
        return restAdapter.create(GithubService.class);
    }

    @Provides
    public RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setClient(new OkClient())
                .setConverter(new JacksonConverter(ServiceUtils.getObjectMapper()))
                .setLogLevel(BuildConfig.SERVICE_DEBUGGING ? RestAdapter.LogLevel.FULL :
                        RestAdapter.LogLevel.NONE)
                .setEndpoint(GITHUB_SERVICE_ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {

                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", GITHUB_SERVICE_VERSION);
                        // User-Agent required by Github API
                        request.addHeader("User-Agent", GITHUB_SERVICE_USER_AGENT);
                    }
                }).build();
    }
}
