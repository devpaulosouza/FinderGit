package com.vinicius.findergit.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by paulo on 26/11/17.
 *
 */

public class RealmOwner extends RealmObject{
    @PrimaryKey
    private String name;
    private String avatarUrl;

    public RealmOwner() {
    }

    public RealmOwner(String name, String avatarUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

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
