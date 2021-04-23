package com.imagesearch.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.imagesearch.app.CommonClass.ImageFileFilter;
import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Repository.ImagesRepository;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items = new ArrayList<String>();
    ImagesRepository ImageRepo;
    DatabaseInitializer db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Set Action bar
            setActionBar();

            //Set Bottom Navigation Bar
            setNavigationMenu();

            db = new DatabaseInitializer(MainActivity.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void GetImageDirectories(String directoryPath) {
        File[] files = new File(directoryPath).listFiles(new ImageFileFilter());
        for (File file : files) {
            if (file.isDirectory() && file.listFiles(new ImageFileFilter()).length > 0) {
                GetImageDirectories(file.getAbsolutePath());
            }
            else {
                Images img = new Images();
                img.setName(file.getName());
                img.setExtension(FilenameUtils.getExtension(file.getName()));
                img.setPath(file.getAbsolutePath());
                img.setFullPath(file.getAbsolutePath());
                img.setFileSize(file.length());
                ImageRepo.Add(img);

                items.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Labels:
            {
                Toast.makeText(this, "this is action label", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LabelActivity.class);
                intent.putExtra("Value", "Label");
                startActivity(intent);
                break;
            }
            case R.id.action_Images:
            {
                Toast.makeText(this, "this is action image", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LabelActivity.class);
                intent.putExtra("Value", "Image");
                startActivity(intent);
                break;
            }
            case R.id.action_mapping:
            {
                Toast.makeText(this, "this is action mapping", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LabelActivity.class);
                intent.putExtra("Value", "Mapping");
                startActivity(intent);
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
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}