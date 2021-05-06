package com.imagesearch.app.database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DatabaseInitializer {
    private static String Database_Name = "ImageSearchApp.realm";
    private static Context context;
    private static RealmConfiguration config;
    private static Realm realm;

    public static void Init(Context contextVar) {
        context = contextVar;
        Realm.init(context);
        config = new RealmConfiguration.Builder()
                .name(Database_Name)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.setDefaultConfiguration(config);
    }

    public static Realm getInstance() {
        return Realm.getDefaultInstance();
    }




}