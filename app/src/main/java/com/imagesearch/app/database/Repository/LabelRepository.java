package com.imagesearch.app.database.Repository;


import com.imagesearch.app.database.Models.Label;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class LabelRepository {

    public void Add(Label label) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(label);
            }
        });
        db.close();
    }

    public void Add(List<Label> labels) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(labels);
            }
        });
        db.close();
    }

    public void Add(int id, String name) {
        Realm db = Realm.getDefaultInstance();
        db.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Label label = db.createObject(Label.class, id);
                label.setLabelName(name);
                realm.copyToRealm(label);
            }
        });
        db.close();
    }

    public void GetAsync(RealmChangeListener<RealmResults<Label>> listener) {
        Realm db = Realm.getDefaultInstance();
        db.where(Label.class).findAllAsync().addChangeListener(listener);
        db.close();
    }

    public List<Label> Get() {
        Realm db = Realm.getDefaultInstance();
        List<Label> labels = new ArrayList<Label>();
        RealmResults<Label> task =  db.where(Label.class).findAll();
        labels.addAll(db.copyFromRealm(task));
        db.close();
        return labels;
    }

    public long GetCount() {
        Realm db = Realm.getDefaultInstance();
        long count =  db.where(Label.class).count();
        db.close();
        return count;
    }
}
