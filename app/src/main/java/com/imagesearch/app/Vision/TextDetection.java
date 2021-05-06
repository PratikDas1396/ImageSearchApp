package com.imagesearch.app.Vision;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.IOException;

public class TextDetection {

    private TextRecognizer recognizer;

    public TextDetection() {
        recognizer = TextRecognition.getClient();
    }

    public void detect(Context context,
                        Uri filePath,
                        OnSuccessListener<Text> successListener,
                        OnFailureListener failureListener
    ) throws IOException {
        InputImage image = InputImage.fromFilePath(context, filePath);

        recognizer.process(image)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }
}
