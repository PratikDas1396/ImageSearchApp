package com.imagesearch.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.imagesearch.app.Adaptor.ImageAdaptor;
import com.imagesearch.app.Adaptor.ImageLabelMappingAdaptor;
import com.imagesearch.app.Adaptor.LabelAdaptor;
import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Repository.ImageLableMappingRepository;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.imagesearch.app.database.Repository.LabelRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;

import java.security.Permission;

public class LabelActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 0x30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            return;
        }
        else{
            onActivityStart();
        }
    }

    public void onActivityStart()  {
        DatabaseInitializer db = new DatabaseInitializer(this);
        String Value = getIntent().getStringExtra("Value");
        switch (Value) {
            case "Label": {
                LabelRepository labelRepository = new LabelRepository(db);
                LabelAdaptor labelAdaptor = new LabelAdaptor(this, labelRepository.GetLabels());
                ListView labelListView = (ListView) findViewById(R.id.labelListView);
                labelListView.setAdapter(labelAdaptor);
                break;
            }
            case "Image": {
                ImagesRepository imagesRepository = new ImagesRepository(db);
                ImageAdaptor imageAdaptor = new ImageAdaptor(this, imagesRepository.GetImages());
                ListView labelListView = (ListView) findViewById(R.id.labelListView);
                labelListView.setAdapter(imageAdaptor);
                break;
            }
            case "Mapping": {
                ImageLableMappingRepository imageLableMappingRepository = new ImageLableMappingRepository(db);
                ImageLabelMappingAdaptor imageAdaptor = new ImageLabelMappingAdaptor(this, imageLableMappingRepository.GetMapping());
                ListView labelListView = (ListView) findViewById(R.id.labelListView);
                labelListView.setAdapter(imageAdaptor);
                break;
            }
            default: {
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onActivityStart();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                }
                return;
        }
    }
}
