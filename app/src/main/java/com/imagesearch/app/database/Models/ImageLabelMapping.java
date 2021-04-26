package com.imagesearch.app.database.Models;

import java.util.Date;

public class ImageLabelMapping {
    public long DetectionId;
    public long LabelID;
    public String LabelName;
    public long ImageId;
    public String ImageName;
    public String FullPath;
    public String UriPath;
    public Date CreatedDtim;
    public Date UpdatedDtim;

    public long getDetectionId() {
        return DetectionId;
    }

    public void setDetectionId(int detectionId) {
        DetectionId = detectionId;
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

    public Date getCreatedDtim() {
        return CreatedDtim;
    }

    public void setCreatedDtim(Date createdDtim) {
        CreatedDtim = createdDtim;
    }

    public Date getUpdatedDtim() {
        return UpdatedDtim;
    }

    public void setUpdatedDtim(Date updatedDtim) {
        UpdatedDtim = updatedDtim;
    }

    @Override
    public String toString() {
        return "ImageLabelMapping{" +
                "DetectionId=" + DetectionId +
                ", LabelName='" + LabelName + '\'' +
                ", LabelID=" + LabelID +
                ", ImageId=" + ImageId +
                ", ImageName='" + ImageName + '\'' +
                ", FullPath='" + FullPath + '\'' +
                ", UriPath =" + UriPath +
                ", CreatedDtim=" + CreatedDtim +
                ", UpdatedDtim=" + UpdatedDtim +
                '}';
    }

}
