package com.binu.unsplashdemo.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nmillward on 3/25/17.
 */

public class PhotoResponse {

    @SerializedName("user") private User user;
    @SerializedName("urls") private Urls urls;

    public User getUser() {
        return user;
    }

    public Urls getUrls() {
        return urls;
    }

    @Override
    public String toString() {
        return "PhotoResponse{" +
                "user=" + user +
                ", urls=" + urls +
                '}';
    }
}
