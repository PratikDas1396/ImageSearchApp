package com.imagesearch.app.database.Repository;

import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Models.LabelImageDataModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImageLabelMappingRepository {

    private int TopLabels = 10;
    private int NoOfImages = 10;

    public ImageLabelMappingRepository() {
    }

    public void Add(ImageLabelMapping mapping) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mapping);
            }
        });
        db.close();
    }

    public void Add(List<ImageLabelMapping> mapping) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
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
                    .distinct("ImageId")
                    .limit(NoOfImages)
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

    public ImageLabelMapping GetMapping(long ImageId) {
        Realm db = Realm.getDefaultInstance();
        ImageLabelMapping task = db.where(ImageLabelMapping.class).equalTo("ImageId", ImageId).distinct("LabelName").findFirst();
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

    public List<ImageLabelMapping> Get(long ImageId) {
        Realm db = Realm.getDefaultInstance();
        List<ImageLabelMapping> mapping = new ArrayList<ImageLabelMapping>();
        RealmResults<ImageLabelMapping> task = db.where(ImageLabelMapping.class).distinct("ImageName").equalTo("ImageId",ImageId).findAll();
        mapping = db.copyFromRealm(task);
        db.close();
        return mapping;
    }

    public List<Images> GetImagesByLabel(String LabelName) {
        Realm db = Realm.getDefaultInstance();
        List<ImageLabelMapping> mapping = new ArrayList<ImageLabelMapping>();
        List<Images> images = new ArrayList<Images>();

        RealmResults<ImageLabelMapping> task = db.where(ImageLabelMapping.class)
                .distinct("ImageId")
                .contains("LabelName", LabelName)
                .findAll();

        mapping = db.copyFromRealm(task);

        List<Long> ids = new ArrayList<Long>();
        mapping.stream().forEach(imageLabelMapping -> ids.add(imageLabelMapping.getImageId()));

        Long[] longs = new Long[ids.size()];
        longs = ids.toArray(longs);

        RealmResults<Images> imageTask = db.where(Images.class).in("Id", longs).findAll();

        images = db.copyFromRealm(imageTask);

        db.close();
        return images;
    }


    public long GetImageCountByLabel(String LabelName) {
        Realm db = Realm.getDefaultInstance();
        long count = db.where(ImageLabelMapping.class).equalTo("LabelName", LabelName).count();
        db.close();
        return count;
    }
}
