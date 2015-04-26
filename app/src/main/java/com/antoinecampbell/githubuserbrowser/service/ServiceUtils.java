package com.antoinecampbell.githubuserbrowser.service;

import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.antoinecampbell.githubuserbrowser.BuildConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class ServiceUtils {

    private static Picasso picassoInstance;

    public static Uri getSizedImageUri(Context context, String imageUrl, int imageSize) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        imageSize = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, imageSize, displayMetrics);
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

    public static Picasso getPicasso(Context context) {
        if (picassoInstance == null) {
            picassoInstance = new Picasso.Builder(context)
                    .indicatorsEnabled(true)
                    .downloader(new OkHttpDownloader(context))
                    .loggingEnabled(BuildConfig.SERVICE_DEBUGGING).build();
        }

        return picassoInstance;
    }
}
