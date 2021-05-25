package com.imagesearch.app.AsyncTask;

import android.app.Dialog;
import android.content.Context;

import com.imagesearch.app.CommonClass.AppStartup;
import com.imagesearch.app.Vision.VisionDetection;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.imagesearch.app.database.Repository.LabelRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import needle.Needle;

public class AppStartupAsyncTask {
    private final Context context;
    private Dialog progressBar;
    private AppStartup appStartup;

    public AppStartupAsyncTask(Context context, Dialog progressBar) {
        this.progressBar = progressBar;
        this.context = context;
        this.appStartup = new AppStartup(context);
    }

    public void run() {
        this.showLoader();
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                appStartup.run();
                dismissLoader();
                runDetection();
            }
        });
    }

    public void showLoader() {
        Needle.onMainThread().execute(new Runnable() {
            @Override
            public void run() {
                progressBar.show();
            }
        });
    }

    public void dismissLoader() {
        Needle.onMainThread().execute(new Runnable() {
            @Override
            public void run() {
                progressBar.dismiss();
            }
        });
    }

    public void runDetection() {
        ImagesRepository imagesRepository = new ImagesRepository();
        List<Images> images = imagesRepository.GetPending();
        for (int i = 0; i < images.size(); i++) {
            VisionDetection detection = new VisionDetection(context);
            try {
                detection.detect(images.get(i));
                try {
                    List<ImageLabelMapping> mapping = new ImageLabelMappingRepository().Get(images.get(i).getId());

                    String[] map = new String[mapping.size()];
                    List<Object> object = Arrays.asList(mapping.stream().map(imageLabelMapping -> imageLabelMapping.getLabelName()).toArray());
                    object.toArray(map);

                    new LabelRepository().UpdateCount(map);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
