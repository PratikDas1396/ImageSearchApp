package com.imagesearch.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.imagesearch.app.AsyncTask.AppStartupAsyncTask;
import com.imagesearch.app.CommonClass.AppPermission;
import com.imagesearch.app.CommonClass.CustomDialog;
import com.imagesearch.app.CommonClass.ImageFileFilter;
import com.imagesearch.app.Vision.VisionDetection;
import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Models.LabelImageDataModel;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.imagesearch.app.database.Repository.LabelRepository;
import com.imagesearch.app.ui.home.HomeFragment;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import needle.Needle;

public class MainActivity extends AppCompatActivity {

    public static final int READ_FILE_PERMISSION_CODE = 0x30;
    Dialog loadingDialog;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Set Action bars
            setActionBar();

            //Set Bottom Navigation Bar
            setNavigationMenu();

            loadingDialog = CustomDialog.getLoadingDialog(this);
            textView = loadingDialog.findViewById(R.id.loading_text);
            textView.setText("Getting Images to read..");

            DatabaseInitializer.Init(this);

            if (AppPermission.checkAppPermissionsGranted(this)) {
                AppStartupAsyncTask backgroundTask = new AppStartupAsyncTask(this, loadingDialog);
                backgroundTask.run();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (grantResults[0] == 0) {
//
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about: {
                Toast.makeText(this, "this is about us", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void setActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.app_bar_main);
    }

    public void setNavigationMenu() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}