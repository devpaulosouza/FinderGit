package com.vinicius.findergit;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by paulo on 24/11/17.
 *
 */

public class FinderGitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }


}
