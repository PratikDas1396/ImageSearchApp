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
        this.image = image;
        this.uri = Uri.parse(image.getUriPath());
        List<ImageLabelMapping> mappings = new ArrayList<ImageLabelMapping>();
        try {
            this.labelDetection.detect(this.context, this.uri, new OnSuccessListener<List<ImageLabel>>() {
                @Override
                public void onSuccess(@NonNull List<ImageLabel> imageLabels) {
                    //List<ImageLabelMapping> mappings = new ArrayList<ImageLabelMapping>();
                    for (ImageLabel label : imageLabels) {
                        boolean contains =  mappings.stream().anyMatch(imageLabelMapping ->
                            imageLabelMapping.getLabelName() == label.getText()
                        );
                        if(contains) continue;
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
                    updateImageStatus();
                    Log.d("Label Detection : ", image.getFullPath());
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });

            this.objectDetection.detect(this.context, this.uri, new OnSuccessListener<List<DetectedObject>>() {
                @Override
                public void onSuccess(@NonNull List<DetectedObject> detectedObjects) {

                    for (DetectedObject detectedObject : detectedObjects) {
                        for (DetectedObject.Label labelItem : detectedObject.getLabels()) {
                            boolean contains =  mappings.stream().anyMatch(imageLabelMapping ->
                                    imageLabelMapping.getLabelName() == labelItem.getText()
                            );
                            if(contains) continue;
                            ImageLabelMapping map = new ImageLabelMapping();
                            map.setImageId(image.getId());
                            map.setImageName(image.getName());
                            map.setUriPath(image.getUriPath());
                            map.setFullPath(image.getFullPath());
                            map.setLabelID(labelItem.getIndex() + 1);
                            map.setLabelName(labelItem.getText());
                            mappings.add(map);
                        }
                    }
                    mappingRepository.Add(mappings);
                    updateImageStatus();
                    Log.d("Object Detection : ", image.getFullPath());
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });

            this.textDetection.detect(this.context, this.uri, new OnSuccessListener<Text>() {
                @Override
                public void onSuccess(@NonNull Text text) {
                    List<Text.TextBlock> blocks = text.getTextBlocks();
                    StringBuilder str = new StringBuilder();

                    if(blocks.size() > 0)
                    {
                        for (int i = 0; i < blocks.size(); i++) {
                            List<Text.Line> lines = blocks.get(i).getLines();
                            for (int j = 0; j < lines.size(); j++) {
                                str.append(lines.get(j).getText() + "\n");
                            }
                        }
                        ImageLabelMapping map = new ImageLabelMapping();
                        map.setImageId(image.getId());
                        map.setImageName(image.getName());
                        map.setUriPath(image.getUriPath());
                        map.setFullPath(image.getFullPath());
                        map.setLabelID(0);
                        map.setLabelName(str.toString());
                        mappingRepository.Add(map);
                    }
                    updateImageStatus();
                    Log.d("Text Detection : ", image.getFullPath());
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateImageStatus() {
        int status = image.getIsDetectionDone();
        if (status == 0) {
            imagesRepository.setDetectionStatus(image, 1);
            image.setIsDetectionDone(1);
        }
    }
}