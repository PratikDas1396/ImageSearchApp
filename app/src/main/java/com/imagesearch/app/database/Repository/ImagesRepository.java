package com.imagesearch.app.database.Repository;

import com.imagesearch.app.database.Models.Images;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ImagesRepository {
    public void Add(Images images) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(images);
            }
        });
        db.close();
    }

    public void Add(List<Images> images) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(images);
            }
        });
        db.close();
    }

    public void GetAsync(RealmChangeListener<RealmResults<Images>> listener) {
        Realm db = Realm.getDefaultInstance();
        List<Images> labels = new ArrayList<Images>();
        db.where(Images.class).findAllAsync().addChangeListener(listener);
    }

    public List<Images> Get() {
        Realm db = Realm.getDefaultInstance();
        List<Images> images = new ArrayList<Images>();
        RealmResults<Images> task = db.where(Images.class).findAll();
        images.addAll(db.copyFromRealm(task));
        db.close();
        return images;
    }

    public Images Get(int imageId) {
        Realm db = Realm.getDefaultInstance();
        Images images = db.where(Images.class).equalTo("Id", imageId).findFirst();
        db.close();
        return images;
    }

    public long GetCount() {
        Realm db = Realm.getDefaultInstance();
        long count = db.where(Images.class).count();
        db.close();
        return count;
    }

}
