package com.imagesearch.app.database.Models;

import java.util.List;

public class LabelImageDataModel {
    private long ID;
    private String LabelName;
    private List<Images> images;

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

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }


}
