package com.imagesearch.app.Vision;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.List;

public class LabelDetection {

    private ImageLabeler recognizer;

    public LabelDetection(){
        recognizer = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
    }

    public void detect(Context context,
                        Uri filePath,
                        OnSuccessListener<List<ImageLabel>> successListener,
                        OnFailureListener failureListener
    ) throws IOException {
        InputImage image = InputImage.fromFilePath(context, filePath);

        recognizer.process(image)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }
}
