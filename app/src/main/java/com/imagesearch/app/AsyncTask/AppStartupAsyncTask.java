package com.imagesearch.app.AsyncTask;

import android.app.Dialog;
import android.content.Context;

import com.imagesearch.app.CommonClass.AppStartup;
import com.imagesearch.app.database.DatabaseInitializer;

import needle.Needle;

public class AppStartupAsyncTask  {
    private final DatabaseInitializer db;
    private final Context context;
    private Dialog progressBar;
    private AppStartup appStartup;

    public AppStartupAsyncTask(Context context, Dialog progressBar , DatabaseInitializer db) {
        this.progressBar = progressBar;
        this.db = db;
        this.context = context;
        this.appStartup = new AppStartup(context, db);
    }

    public void run (){
        this.showLoader();
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                appStartup.run();
                dismissLoader();
            }
        });
    }

    public void showLoader(){
        Needle.onMainThread().execute(new Runnable() {
            @Override
            public void run() {
                progressBar.show();
            }
        });
    }

    public void dismissLoader(){
        Needle.onMainThread().execute(new Runnable() {
            @Override
            public void run() {
                progressBar.dismiss();
            }
        });
    }
}
