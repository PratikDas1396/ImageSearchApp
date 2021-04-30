package com.imagesearch.app.database.Repository;

import com.imagesearch.app.database.Models.ImageLabelMapping;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ImageLabelMappingRepository {

    private int TopLabels = 10;
    private int NoOfImages = 10;
    private Realm db;

    public ImageLabelMappingRepository(Realm db) {
        this.db = db;
    }

    private RealmResults<ImageLabelMapping> GetAll() {
        return db.where(ImageLabelMapping.class).findAllAsync();
    }

    public void Add(ImageLabelMapping mapping) {
        db.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mapping);
            }
        });
    }

    public void Add(List<ImageLabelMapping> mapping) {
        db.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(mapping);
            }
        });
    }

    public void GetLabelsByImages(int ImageID, RealmChangeListener<RealmResults<ImageLabelMapping>> listener) {
        this.db.where(ImageLabelMapping.class)
                .equalTo("ImageId", ImageID)
                .findAllAsync()
                .addChangeListener(new RealmChangeListener<RealmResults<ImageLabelMapping>>() {
                    @Override
                    public void onChange(RealmResults<ImageLabelMapping> imageLabelMappings) {
                        ImageLabelMapping l = new ImageLabelMapping();
                    }
                });
    }

    public void GetImagesByLabels(int LabelID, int MaxImages) {
        this.db.where(ImageLabelMapping.class)
                .equalTo("LabelId", LabelID)
                .findAllAsync()
                .addChangeListener(new RealmChangeListener<RealmResults<ImageLabelMapping>>() {
                    @Override
                    public void onChange(RealmResults<ImageLabelMapping> imageLabelMappings) {
                        ImageLabelMapping l = new ImageLabelMapping();
                    }
                });
    }

    public void GetTopLabels(int labelCount) {

    }

    public void GetTrendingLabels() {
//        this.db.where(ImageLabelMapping.class)
//                .distinct("LabelName")
//                .count(
//        ;
    }

    public void GetMapping() {

    }

}
