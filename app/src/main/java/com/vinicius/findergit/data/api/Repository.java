package com.vinicius.findergit.data.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paulo on 25/11/17.
 *
 */

public class Repository {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("language")
    private String language;
    @SerializedName("html_url")
    private String url;
    @SerializedName("owner")
    private Owner owner;

    public Repository() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
