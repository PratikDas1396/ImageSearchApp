package com.imagesearch.app.database.Repository;

import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;

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

    public void GetLabelsByImages(int ImageID, RealmChangeListener<RealmResults<ImageLabelMapping>> listener) {
        Realm db = Realm.getDefaultInstance();
        db.where(ImageLabelMapping.class)
                .equalTo("ImageId", ImageID)
                .findAllAsync()
                .addChangeListener(new RealmChangeListener<RealmResults<ImageLabelMapping>>() {
                    @Override
                    public void onChange(RealmResults<ImageLabelMapping> imageLabelMappings) {
                        ImageLabelMapping l = new ImageLabelMapping();
                    }
                });
        db.close();
    }

    public void GetImagesByLabels(int LabelID, int MaxImages) {
        Realm db = Realm.getDefaultInstance();
        db.where(ImageLabelMapping.class)
                .equalTo("LabelId", LabelID)
                .findAllAsync()
                .addChangeListener(new RealmChangeListener<RealmResults<ImageLabelMapping>>() {
                    @Override
                    public void onChange(RealmResults<ImageLabelMapping> imageLabelMappings) {
                        ImageLabelMapping l = new ImageLabelMapping();
                    }
                });
        db.close();
    }

    public void GetTopLabels(int labelCount) {

    }

    public void GetTrendingLabels() {
        Realm db = Realm.getDefaultInstance();
        long count = db.where(ImageLabelMapping.class)
                .distinct("LabelName")
                //.findAll()
                .count();
        db.close();
    }

    public void GetMapping() {

    }
}
