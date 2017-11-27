package com.vinicius.findergit.data.dao;

import android.support.annotation.NonNull;

import com.vinicius.findergit.ConstUtils;
import com.vinicius.findergit.Utils;
import com.vinicius.findergit.data.model.RealmHistory;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by paulo on 25/11/17.
 *
 */

public class HistoryDao {

    private Realm realm;

    public HistoryDao() {
        realm = Realm.getInstance(Utils.getConfiguration(ConstUtils.REALM_HISTORY));
    }

    public RealmResults<RealmHistory> getAll () {
        return realm.where(RealmHistory.class)
                .findAll();
    }

    public RealmResults<RealmHistory> getAll (String key, Sort sort) {
        return realm.where(RealmHistory.class)
                .findAllSorted(key, sort);
    }

    public void remove (final RealmHistory realmHistory) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmObject.deleteFromRealm(realmHistory);
            }
        });
    }

    public void add (final RealmHistory realmHistory) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(realmHistory);
            }
        });
    }
}
