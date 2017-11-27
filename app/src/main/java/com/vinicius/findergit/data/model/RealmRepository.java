package com.vinicius.findergit.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by paulo on 26/11/17.
 *
 */

public class RealmRepository extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private String language;
    private String url;
    private String ownerName;

    public RealmRepository() {
    }

    public RealmRepository(long id, String name, String language, String url, String ownerName) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.url = url;
        this.ownerName = ownerName;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
