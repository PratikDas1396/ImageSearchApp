package com.imagesearch.app.Vision;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.objects.DetectedObject;
import com.google.mlkit.vision.text.Text;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Repository.ImageLabelMappingRepository;
import com.imagesearch.app.database.Repository.ImagesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VisionDetection {

    private Context context;
    private LabelDetection labelDetection;
    private ObjectDetection objectDetection;
    private TextDetection textDetection;
    private Uri uri;
    private ImageLabelMappingRepository mappingRepository;
    private ImagesRepository imagesRepository;
    List<Label> labels;
    Images image;
    //private boolean isDetectionDone;

    MutableLiveData<Boolean> isDetectionDone = new MutableLiveData<Boolean>();

    public VisionDetection(Context context) {
        this.context = context;
        this.labelDetection = new LabelDetection();
        this.objectDetection = new ObjectDetection();
        this.textDetection = new TextDetection();
        this.mappingRepository = new ImageLabelMappingRepository();
        this.imagesRepository = new ImagesRepository();
        labels = new ArrayList<Label>();
        //setUpObserver();
    }

    private void setUpObserver() {
        isDetectionDone.observe((LifecycleOwner) this.context, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    List<ImageLabelMapping> mappings = new ArrayList<ImageLabelMapping>();
                    labels.stream().forEach(label -> {
                        ImageLabelMapping map = new ImageLabelMapping();
                        map.setImageId(image.getId());
                        map.setImageName(image.getName());
                        map.setUriPath(image.getUriPath());
                        map.setFullPath(image.getFullPath());
                        map.setLabelID(label.getId());
                        map.setLabelName(label.getLabelName());
                        mappings.add(map);
                    });
                    mappingRepository.Add(mappings);
                    Log.d("Path", "image : " + image.getFullPath());
                }
            }
        });
    }

    public void detect(Images image) {
        //Images image = imagesRepository.Get(1);
        this.image = image;
        this.uri = Uri.parse(image.getUriPath());
//        boolean[] isLabelDetectionDone = {false};
//        boolean[] isObjectDetectionDone = {false};
//        boolean[] isTextDetectionDone = {false};

        try {
            this.labelDetection.detect(this.context, this.uri, new OnSuccessListener<List<ImageLabel>>() {
                @Override
                public void onSuccess(@NonNull List<ImageLabel> imageLabels) {
                    List<ImageLabelMapping> mappings = new ArrayList<ImageLabelMapping>();
                    for (ImageLabel label : imageLabels) {
                        ImageLabelMapping map = new ImageLabelMapping();
                        map.setImageId(image.getId());
                        map.setImageName(image.getName());
                        map.setUriPath(image.getUriPath());
                        map.setFullPath(image.getFullPath());
                        map.setLabelID(label.getIndex() + 1);
                        map.setLabelName(label.getText());
                        mappings.add(map);
                    }
                    mappingRepository.Add(mappings);
                    //isLabelDetectionDone[0] = true;
                    //isDetectionDone = isLabelDetectionDone[0] && isObjectDetectionDone[0] && isObjectDetectionDone[0];
                    //isDetectionDone.setValue(isLabelDetectionDone[0] && isObjectDetectionDone[0] && isTextDetectionDone[0]);
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    //isLabelDetectionDone[0] = true;
                    //isDetectionDone = isLabelDetectionDone[0] && isObjectDetectionDone[0] && isObjectDetectionDone[0];
                    //isDetectionDone.setValue(isLabelDetectionDone[0] && isObjectDetectionDone[0] && isTextDetectionDone[0]);
                }
            });

            this.objectDetection.detect(this.context, this.uri, new OnSuccessListener<List<DetectedObject>>() {
                @Override
                public void onSuccess(@NonNull List<DetectedObject> detectedObjects) {
                    List<ImageLabelMapping> mappings = new ArrayList<ImageLabelMapping>();
                    for (DetectedObject detectedObject : detectedObjects) {
                        for (DetectedObject.Label labelItem : detectedObject.getLabels()) {
                            ImageLabelMapping map = new ImageLabelMapping();
                            map.setImageId(image.getId());
                            map.setImageName(image.getName());
                            map.setUriPath(image.getUriPath());
                            map.setFullPath(image.getFullPath());
                            map.setLabelID(labelItem.getIndex() + 1);
                            map.setLabelName(labelItem.getText());
                            mappings.add(map);
//                            Label label = new Label();
//                            label.setLabelName(labelItem.getText());
//                            label.setId(labelItem.getIndex() + 1);
//                            labels.add(label);
                        }
                    }
                    mappingRepository.Add(mappings);
                    //isObjectDetectionDone[0] = true;
                    //isDetectionDone = isLabelDetectionDone[0] && isObjectDetectionDone[0] && isObjectDetectionDone[0];
                    //isDetectionDone.setValue(isLabelDetectionDone[0] && isObjectDetectionDone[0] && isTextDetectionDone[0]);
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    //isObjectDetectionDone[0] = true;
                    //isDetectionDone = isLabelDetectionDone[0] && isObjectDetectionDone[0] && isObjectDetectionDone[0];
                    //isDetectionDone.setValue(isLabelDetectionDone[0] && isObjectDetectionDone[0] && isTextDetectionDone[0]);
                }
            });

            this.textDetection.detect(this.context, this.uri, new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(@NonNull Text text) {
                    List<Text.TextBlock> blocks = text.getTextBlocks();
                    List<ImageLabelMapping> mappings = new ArrayList<ImageLabelMapping>();
                    for (int i = 0; i < blocks.size(); i++) {
                        List<Text.Line> lines = blocks.get(i).getLines();
                        StringBuilder str = new StringBuilder();
                        for (int j = 0; j < lines.size(); j++) {
                            str.append(lines.get(j).getText());
                        }

                        ImageLabelMapping map = new ImageLabelMapping();
                        map.setImageId(image.getId());
                        map.setImageName(image.getName());
                        map.setUriPath(image.getUriPath());
                        map.setFullPath(image.getFullPath());
                        map.setLabelID(0);
                        map.setLabelName(str.toString());
                        mappings.add(map);

//                        Label label = new Label();
//                        label.setLabelName(str.toString());
//                        label.setId(0);
//                        labels.add(label);
                    }
                    mappingRepository.Add(mappings);
                    //isTextDetectionDone[0] = true;
                    //isDetectionDone = isLabelDetectionDone[0] && isObjectDetectionDone[0] && isObjectDetectionDone[0];
                    //isDetectionDone.setValue(isLabelDetectionDone[0] && isObjectDetectionDone[0] && isTextDetectionDone[0]);
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    //isTextDetectionDone[0] = true;
                    //isDetectionDone = isLabelDetectionDone[0] && isObjectDetectionDone[0] && isObjectDetectionDone[0];
                    //isDetectionDone.setValue(isLabelDetectionDone[0] && isObjectDetectionDone[0] && isTextDetectionDone[0]);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            //isLabelDetectionDone[0] = true;
            //isObjectDetectionDone[0] = true;
            //isTextDetectionDone[0] = true;
            //isDetectionDone.setValue(isLabelDetectionDone[0] && isObjectDetectionDone[0] && isTextDetectionDone[0]);
        }
    }
}


//                        Rect boundingBox = detectedObject.getBoundingBox();
//                        Integer trackingId = detectedObject.getTrackingId();
//                            if (PredefinedCategory.FOOD.equals(text)) {
//                            }
//                            int index = label.getIndex();
//                            if (PredefinedCategory.FOOD_INDEX == index) {
//                            }
//                            float confidence = label.getConfidence();

//                            List<Text.Element> elements = lines.get(j).getElements();
//                            for (int k = 0; k < elements.size(); k++) {
//
//                            }