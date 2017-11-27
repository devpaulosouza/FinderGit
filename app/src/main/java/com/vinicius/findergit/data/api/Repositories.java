package com.vinicius.findergit.data.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by paulo on 26/11/17.
 *
 */

public class Repositories {
    @SerializedName("items")
    private ArrayList<Repository> repositories;

    public ArrayList<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(ArrayList<Repository> repositories) {
        this.repositories = repositories;
    }
}
