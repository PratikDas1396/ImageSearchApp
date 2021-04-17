package com.imagesearch.app.database.Models;

public class Label {
    private long ID;
    private String LabelName;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String LabelName) {
        this.LabelName = LabelName;
    }

    @Override
    public String toString() {
        return "Label{" +  "ID=" + ID + ", LabelName='" + LabelName + '\'' + '}';
    }
}
