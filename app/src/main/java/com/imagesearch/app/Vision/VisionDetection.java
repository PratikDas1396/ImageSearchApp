package com.imagesearch.app.Vision;

import android.content.Context;

public class VisionDetection {

    Context context;
    LabelDetection labelDetection;
    ObjectDetection objectDetection;
    TextDetection textDetection;

    public VisionDetection(Context context) {
        this.context = context;
        this.labelDetection = new LabelDetection();
        this.objectDetection = new ObjectDetection();
        this.textDetection = new TextDetection();
    }
}
