package com.vinicius.findergit.data.dao;

import android.support.annotation.NonNull;

import com.vinicius.findergit.ConstUtils;
import com.vinicius.findergit.Utils;
import com.vinicius.findergit.data.model.RealmOwner;
import com.vinicius.findergit.data.model.RealmRepository;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by paulo on 26/11/17.
 *
 */

public class OwnerDao {
    private Realm realm;

    public OwnerDao() {
        realm = Realm.getInstance(Utils.getConfiguration(ConstUtils.REALM_OWNER));
    }

    public RealmResults<RealmOwner> getAll () {
        return realm.where(RealmOwner.class).findAll();
    }

    public void add (final RealmOwner realmOwner) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(realmOwner);
            }
        });
    }
    public void add (final ArrayList<RealmOwner> realmOwners) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealmOrUpdate(realmOwners);
            }
        });
    }

    public void deleteAll () {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public RealmOwner get(String name) {
        return realm.where(RealmOwner.class).equalTo("name", name).findFirst();
    }
}
