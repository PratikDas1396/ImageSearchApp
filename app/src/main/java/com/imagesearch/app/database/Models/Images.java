package com.imagesearch.app.database.Models;

import java.util.Date;


public class Images {
    private long Id;
    private String Name;
    private String Extension;
    private long FileSize;
    private int isDetectionDone;
    private String UriPath;
    private String FullPath;
    private Date ImageCreationDate;
    private Date CreatedDtim;
    private Date UpdatedDtim;

    public long getId() {
        return this.Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getExtension() {
        return this.Extension;
    }

    public void setExtension(String extension) {
        this.Extension = extension;
    }

    public long getFileSize() {
        return this.FileSize;
    }

    public void setFileSize(long fileSize) {
        this.FileSize = fileSize;
    }

    public String getUriPath() {
        return this.UriPath;
    }

    public void setUriPath(String uriPath) {
        this.UriPath = uriPath;
    }

    public String getFullPath() {
        return this.FullPath;
    }

    public void setFullPath(String fullPath) {
        this.FullPath = fullPath;
    }

    public Date getCreatedDtim() {
        return this.CreatedDtim;
    }

    public void setCreatedDtim(Date createdDtim) {
        this.CreatedDtim = createdDtim;
    }

    public Date getUpdatedDtim() {
        return this.UpdatedDtim;
    }

    public void setUpdatedDtim(Date updatedDtim) {
        this.UpdatedDtim = updatedDtim;
    }

    public Date getImageCreationDate() {
        return ImageCreationDate;
    }

    public void setImageCreationDate(Date imageCreationDate) {
        this.ImageCreationDate = imageCreationDate;
    }

    public int getIsDetectionDone() {
        return isDetectionDone;
    }

    public void setIsDetectionDone(int isDetectionDone) {
        this.isDetectionDone = isDetectionDone;
    }

    @Override
    public String toString() {
        return "Images{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Extension='" + Extension + '\'' +
                ", FileSize=" + FileSize +
                ", UriPath='" + UriPath + '\'' +
                ", FullPath='" + FullPath + '\'' +
                ", ImageCreationDate=" + ImageCreationDate +
                ", isDetectionDone=" + isDetectionDone +
                ", CreatedDtim=" + CreatedDtim +
                ", UpdatedDtim=" + UpdatedDtim +
                '}';
    }

}
