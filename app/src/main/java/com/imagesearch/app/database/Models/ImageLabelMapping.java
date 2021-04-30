package com.imagesearch.app.database.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ImageLabelMapping extends RealmObject {

    @PrimaryKey
    public int Id;
    public long LabelID;
    public String LabelName;
    public long ImageId;
    public String ImageName;
    public String FullPath;
    public String UriPath;

    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String labelName) {
        LabelName = labelName;
    }

    public long getLabelID() {
        return LabelID;
    }

    public void setLabelID(long labelID) {
        LabelID = labelID;
    }

    public long getImageId() {
        return ImageId;
    }

    public void setImageId(long imageId) {
        ImageId = imageId;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String fullPath) {
        FullPath = fullPath;
    }

    public String getUriPath() {
        return UriPath;
    }

    public void setUriPath(String uriPath) {
        UriPath = uriPath;
    }

    @Override
    public String toString() {
        return "ImageLabelMapping{" +
                "Id=" + Id +
                ", LabelName='" + LabelName + '\'' +
                ", LabelID=" + LabelID +
                ", ImageId=" + ImageId +
                ", ImageName='" + ImageName + '\'' +
                ", FullPath='" + FullPath + '\'' +
                ", UriPath =" + UriPath +
                '}';
    }

}
