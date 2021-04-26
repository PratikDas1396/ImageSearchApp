package com.imagesearch.app.database.Models;

import java.util.Date;
import java.util.List;

public class ImageLabelDataModel {
    private long Id;
    private String Name;
    private String Extension;
    private long FileSize;
    private String FullPath;
    private String UriPath;
    private int isDetectionDone;
    private Date ImageCreationDate;
    private List<Label> labels;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }

    public long getFileSize() {
        return FileSize;
    }

    public void setFileSize(long fileSize) {
        FileSize = fileSize;
    }

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String fullPath) {
        FullPath = fullPath;
    }

    public Date getImageCreationDate() {
        return ImageCreationDate;
    }

    public void setImageCreationDate(Date imageCreationDate) {
        ImageCreationDate = imageCreationDate;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getUriPath() {
        return UriPath;
    }

    public void setUriPath(String uriPath) {
        UriPath = uriPath;
    }

    public int getIsDetectionDone() {
        return isDetectionDone;
    }

    public void setIsDetectionDone(int isDetectionDone) {
        this.isDetectionDone = isDetectionDone;
    }
}
