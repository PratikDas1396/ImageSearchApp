package com.imagesearch.app.database.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Label extends RealmObject {

    @PrimaryKey
    private int Id;
    private String LabelName;
    private long ImageCount;

    public long getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getLabelName() {
        return LabelName;
    }

    public void setLabelName(String LabelName) {
        this.LabelName = LabelName;
    }

    public long getCount() {
        return ImageCount;
    }

    public void setCount(long count) {
        ImageCount = count;
    }

    @Override
    public String toString() {
        return "Label{" + "ID=" + Id + ", LabelName='" + LabelName + '\'' + '}';
    }
}
