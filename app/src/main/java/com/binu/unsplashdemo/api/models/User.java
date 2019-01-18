package com.binu.unsplashdemo.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nmillward on 3/25/17.
 */

public class User {

    @SerializedName("name") private String name;
    @SerializedName("location") private String location;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
