package com.imagesearch.app.AsyncTask;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.imagesearch.app.CommonClass.AppStartup;
import com.imagesearch.app.Vision.VisionDetection;
import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.imagesearch.app.database.Repository.LabelRepository;

import java.util.List;

import io.realm.Realm;
import needle.Needle;

public class AppStartupAsyncTask  {
    private final Context context;
    private Dialog progressBar;
    private AppStartup appStartup;

    public AppStartupAsyncTask(Context context, Dialog progressBar) {
        this.progressBar = progressBar;
        this.context = context;
        this.appStartup = new AppStartup(context);
    }

    public void run (){
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

    public void runDetection () {
        ImagesRepository imagesRepository = new ImagesRepository();
        LabelRepository labelRepository = new LabelRepository();
        ImageLabelMappingRepository imageLabelMappingRepository = new ImageLabelMappingRepository();

        List<Images> images = imagesRepository.GetPending();

        for(int i=0; i< images.size(); i++){
            VisionDetection detection = new VisionDetection(context);
            detection.detect(images.get(i));
            ImageLabelMapping map = new ImageLabelMappingRepository().GetMapping(images.get(i).getId());
        }
        Log.d("for" , "loop donw");
        
        List<Label> labels = labelRepository.Get();
        for (int i = 0; i < labels.size(); i++) {
            Label l = labels.get(i);
            l.setCount(imageLabelMappingRepository.GetImageCountByLabel(l.getLabelName()));
        }
        labels.stream().filter(a -> a.getCount() > 0);
        labelRepository.UpdateImageCount(labels);
    }
}
