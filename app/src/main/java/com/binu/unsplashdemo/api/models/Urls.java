package com.binu.unsplashdemo.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nmillward on 3/25/17.
 */

public class Urls {

    @SerializedName("regular") private String image_regular;
    @SerializedName("small") private String image_small;

    public String getImage_regular() {
        return image_regular;
    }

    public String getImage_small() {
        return image_small;
    }

    @Override
    public String toString() {
        return "Urls{" +
                "image_regular='" + image_regular + '\'' +
                '}';
    }
}
