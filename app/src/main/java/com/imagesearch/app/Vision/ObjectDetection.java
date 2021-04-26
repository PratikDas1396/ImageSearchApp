package com.imagesearch.app.Vision;

import android.content.Context;
import android.net.Uri;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.objects.ObjectDetector;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;


import java.io.IOException;
import java.util.List;

public class ObjectDetection {

    ObjectDetector objectDetector;

    public ObjectDetection() {
        objectDetector = com.google.mlkit.vision.objects.ObjectDetection
                .getClient(
                        new ObjectDetectorOptions.Builder()
                                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                                .enableMultipleObjects()
                                .enableClassification()
                                .build()
                );
    }

    public void detect(Context context,
                       Uri filePath,
                       OnSuccessListener<List<DetectedObject>> successListener,
                       OnFailureListener failureListener
    ) throws IOException {
        InputImage image = InputImage.fromFilePath(context, filePath);

        objectDetector.process(image)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

//     new OnSuccessListener<List<DetectedObject>>(){
//@Override
//public void onSuccess(List<DetectedObject> detectedObjects){
//        for(DetectedObject detectedObject:detectedObjects){
//        Rect boundingBox=detectedObject.getBoundingBox();
//        Integer trackingId=detectedObject.getTrackingId();
//        for(DetectedObject.Label label:detectedObject.getLabels()){
//        String text=label.getText();
//        if(PredefinedCategory.FOOD.equals(text)){
//        }
//        int index=label.getIndex();
//        if(PredefinedCategory.FOOD_INDEX==index){
//        }
//        float confidence=label.getConfidence();
//        }
//        }
//        }
//        }


//     new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception e) {
//            // Task failed with an exception
//            // ...
//        }
//    }
}
