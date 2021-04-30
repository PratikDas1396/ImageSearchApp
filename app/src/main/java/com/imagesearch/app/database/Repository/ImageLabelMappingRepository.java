package com.imagesearch.app.database.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.ImageLabelDataModel;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Models.LabelImageDataModel;
import com.imagesearch.app.database.Tables.ImageLabelMappingTable;
import com.imagesearch.app.database.Tables.ImageTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
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

    }

    public void Add(List<ImageLabelMapping> mapping) {

    }

    public void GetLabelsByImages(long ImageID) {

    }

    public void GetImagesByLabels(long LabelID, int MaxImages) {

    }

    public void GetTopLabels(int labelCount) {

    }

    public void GetTrendingLabels() {

    }

    public void GetMapping() {

    }

}
