package com.imagesearch.app.database.Repository;


import com.imagesearch.app.database.Models.Label;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class LabelRepository {

    private int TopLabels = 10;

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
        RealmResults<Label> task = db.where(Label.class).sort("LabelName").findAll();
        labels.addAll(db.copyFromRealm(task));
        db.close();
        return labels;
    }


    public Label Get(String LabelName) {
        Realm db = Realm.getDefaultInstance();
        Label label = db.where(Label.class).contains("LabelName" , LabelName).findFirst();
        db.close();
        return label;
    }

    public List<Label> Get(String[] LabelList) {
        Realm db = Realm.getDefaultInstance();
        List<Label> labels = new ArrayList<Label>();
        RealmResults<Label> task = db.where(Label.class).in("LabelName", LabelList).findAll();
        labels.addAll(db.copyFromRealm(task));
        db.close();
        return labels;
    }


    public void UpdateCount(String[] LabelList){
        Realm db = Realm.getDefaultInstance();
        List<Label> labels = new ArrayList<Label>();
        RealmResults<Label> task = db.where(Label.class).in("LabelName", LabelList).findAll();
        labels.addAll(db.copyFromRealm(task));
        for (Label label : labels) {
            label.setCount(label.getCount() + 1);
        }
        db.beginTransaction();
        db.copyToRealmOrUpdate(labels);
        db.commitTransaction();
        db.close();
    }

    public List<Label> GetTrendingLabels() {
        Realm db = Realm.getDefaultInstance();
        RealmResults<Label> result = db.where(Label.class)
                .notEqualTo("ImageCount", 0)
                .sort("ImageCount", Sort.DESCENDING)
                .limit(TopLabels)
                .findAll();
        List<Label> map = db.copyFromRealm(result);
        db.close();
        return map;
    }

    public void UpdateImageCount(List<Label> labels) {
        Realm db = Realm.getDefaultInstance();
        db.beginTransaction();
        db.copyToRealmOrUpdate(labels);
        db.commitTransaction();
        db.close();
    }

    public long GetCount() {
        Realm db = Realm.getDefaultInstance();
        long count = db.where(Label.class).count();
        db.close();
        return count;
    }
}
