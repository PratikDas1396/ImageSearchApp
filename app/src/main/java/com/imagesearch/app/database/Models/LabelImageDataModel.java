package com.imagesearch.app.database.Models;

import java.util.List;

public class LabelImageDataModel {
    private long ID;
    private String LabelName;
    private List<ImageLabelMapping> images;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public List<ImageLabelMapping> getImages() {
        return images;
    }

    public void setImages(List<ImageLabelMapping> images) {
        this.images = images;
    }
}
