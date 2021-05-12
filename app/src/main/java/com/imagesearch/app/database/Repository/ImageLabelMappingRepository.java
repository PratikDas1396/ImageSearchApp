package com.imagesearch.app.database.Repository;

import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Models.LabelImageDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ImageLabelMappingRepository {

    private int TopLabels = 10;
    private int NoOfImages = 10;

    public ImageLabelMappingRepository() {
    }

    public void Add(ImageLabelMapping mapping) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mapping);
            }
        });
        db.close();
    }

    public void Add(List<ImageLabelMapping> mapping) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mapping);
            }
        });
        db.close();
    }

    public List<LabelImageDataModel> GetImagesByLabel(List<Label> labels) {
        List<LabelImageDataModel> items = new ArrayList<LabelImageDataModel>();
        Realm db = Realm.getDefaultInstance();
        for (int i = 0; i < labels.size(); i++) {
            RealmResults<ImageLabelMapping> result = db.where(ImageLabelMapping.class)
                    .equalTo("LabelName", labels.get(i).getLabelName())
                    .findAll();
            List<ImageLabelMapping> mapping = db.copyFromRealm(result);

            LabelImageDataModel model = new LabelImageDataModel();
            model.setID(labels.get(i).getId());
            model.setLabelName(labels.get(i).getLabelName());
            model.setImages(mapping);
            items.add(model);
        }
        db.close();
        return items;
    }

    public ImageLabelMapping GetMapping(int ImageId) {
        Realm db = Realm.getDefaultInstance();
        ImageLabelMapping task = db.where(ImageLabelMapping.class).equalTo("ImageId", ImageId).findFirst();
        db.close();
        return task;
    }

    public List<ImageLabelMapping> Get() {
        Realm db = Realm.getDefaultInstance();
        List<ImageLabelMapping> mapping = new ArrayList<ImageLabelMapping>();
        RealmResults<ImageLabelMapping> task = db.where(ImageLabelMapping.class).distinct("ImageName").findAll();
        mapping = db.copyFromRealm(task);
        db.close();
        return mapping;
    }

    public long GetImageCountByLabel(String LabelName) {
        Realm db = Realm.getDefaultInstance();
        long count = db.where(ImageLabelMapping.class).equalTo("LabelName", LabelName).count();
        db.close();
        return count;
    }
}
