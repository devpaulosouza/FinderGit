package com.vinicius.findergit.data.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by paulo on 26/11/17.
 *
 */

public class RealmHistory extends RealmObject{
    @PrimaryKey
    private String word;
    private Date date;
    private long repositoryId = -1;

    public RealmHistory() {
    }

    public RealmHistory(String word, Date date, long repositoryId) {
        this.word = word;
        this.date = date;
        this.repositoryId = repositoryId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(long repositoryId) {
        this.repositoryId = repositoryId;
    }
}
