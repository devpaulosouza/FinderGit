package com.vinicius.findergit.data.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paulo on 26/11/17.
 *
 */

public class Owner {
    @SerializedName("login")
    private String name;
    @SerializedName("atavar_url")
    private String avatarUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
