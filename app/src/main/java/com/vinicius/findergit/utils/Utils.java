package com.vinicius.findergit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulo on 25/11/17.
 *
 */

public class Utils {

    public static boolean isEmailValid(String email) {
        Matcher matcher = Pattern
                .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(email);

        return matcher.find();
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public static Retrofit connect(String endpoint) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RealmConfiguration getConfiguration (String name) {
        return new RealmConfiguration.Builder()
                .name(name.concat(".realm"))
                .schemaVersion(42)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

}
