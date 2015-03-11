package com.antoinecampbell.githubuserbrowser.service;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

public class ServiceUtil {

    private static final String GITHUB_SERVICE_ENDPOINT = "https://api.github.com";
    private static final String GITHUB_SERVICE_VERSION = "application/vnd.github.v3+json";
    private static final String GITHUB_SERVICE_USER_AGENT = "com.antoinecampbell.githubuserbrowse";
    private static GithubService githubServiceInstance;
    private static Picasso picassoInstance;

    public static GithubService getGithubService() {
        if (githubServiceInstance == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setClient(new OkClient())
                    .setConverter(new JacksonConverter(getObjectMapper()))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(GITHUB_SERVICE_ENDPOINT)
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Accept", GITHUB_SERVICE_VERSION);
                            // User-Agent required by Github API
                            request.addHeader("User-Agent", GITHUB_SERVICE_USER_AGENT);
                        }
                    }).build();
            githubServiceInstance = restAdapter.create(GithubService.class);
        }

        return githubServiceInstance;
    }

    public static Picasso getPicasso(Context context) {
        if (picassoInstance == null) {

            picassoInstance = new Picasso.Builder(context)
                    .indicatorsEnabled(true)
                    .downloader(new OkHttpDownloader(context))
                    .loggingEnabled(true).build();
        }
        return picassoInstance;
    }

    public static Uri getSizedImageUri(Context context, String imageUrl, int imageSize) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        imageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSize, displayMetrics);
        Uri imageUri = Uri.parse(imageUrl);
        imageUri = imageUri.buildUpon()
                .appendQueryParameter("s", String.valueOf(imageSize))
                .build();

        return imageUri;
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
