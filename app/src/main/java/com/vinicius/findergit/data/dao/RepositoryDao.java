package com.vinicius.findergit.data.dao;

import com.vinicius.findergit.utils.ConstUtils;
import com.vinicius.findergit.utils.Utils;
import com.vinicius.findergit.data.model.RealmRepository;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by paulo on 26/11/17.
 *
 */

public class RepositoryDao {

    public RepositoryDao() {
    }

    public RealmResults<RealmRepository> getAll () {
        Realm realm = Realm.getInstance(Utils.getConfiguration(ConstUtils.REALM_REPOSITORY));
        return realm.where(RealmRepository.class).findAll();
    }

    public void add (final RealmRepository realmRepository) {
        Realm realm = Realm.getInstance(Utils.getConfiguration(ConstUtils.REALM_REPOSITORY));
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmRepository);
        realm.commitTransaction();
        realm.close();
    }
    public void add (final ArrayList<RealmRepository> realmSearch) {
        Realm realm = Realm.getInstance(Utils.getConfiguration(ConstUtils.REALM_REPOSITORY));
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmSearch);
        realm.commitTransaction();
        realm.close();
    }

    public void deleteAll () {
        Realm realm = Realm.getInstance(Utils.getConfiguration(ConstUtils.REALM_REPOSITORY));
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }
}
