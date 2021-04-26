package com.imagesearch.app.database.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Tables.ImageTable;
import com.imagesearch.app.database.Tables.LabelTable;

import java.util.ArrayList;
import java.util.List;


public class ImagesRepository {
    private DatabaseInitializer context;

    public ImagesRepository() {
    }

    public ImagesRepository(DatabaseInitializer context){
        this.context = context;
    }

    public long Add(Images images){
        SQLiteDatabase db = context.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ImageTable.Name, images.getName());
        cv.put(ImageTable.Extension, images.getExtension());
        cv.put(ImageTable.FileSize, images.getFileSize());
        cv.put(ImageTable.UriPath, images.getUriPath());
        cv.put(ImageTable.FullPath, images.getFullPath());
        cv.put(ImageTable.isDetectionDone, images.getIsDetectionDone());
        cv.put(ImageTable.ImageCreationDate, images.getImageCreationDate().toString());

        if(images.getCreatedDtim() != null)
            cv.put(ImageTable.CreatedDtim, images.getCreatedDtim().toString());

        if(images.getUpdatedDtim() != null)
            cv.put(ImageTable.UpdatedDtim, images.getUpdatedDtim().toString());

        return db.insert(ImageTable.TableName, null, cv);
    }

    public long Add(SQLiteDatabase db, Images images){
        ContentValues cv = new ContentValues();
        cv.put(ImageTable.Name, images.getName());
        cv.put(ImageTable.Extension, images.getExtension());
        cv.put(ImageTable.FileSize, images.getFileSize());
        cv.put(ImageTable.UriPath, images.getUriPath());
        cv.put(ImageTable.FullPath, images.getFullPath());
        cv.put(ImageTable.ImageCreationDate, images.getImageCreationDate().toString());
        cv.put(ImageTable.isDetectionDone, images.getIsDetectionDone());

        if(images.getCreatedDtim() != null)
            cv.put(ImageTable.CreatedDtim, images.getCreatedDtim().toString());

        if(images.getUpdatedDtim() != null)
            cv.put(ImageTable.UpdatedDtim, images.getUpdatedDtim().toString());

        return db.insert(ImageTable.TableName, null, cv);
    }

    public List<String> GetImageNames() {
        SQLiteDatabase db = context.getReadableDatabase();
        List<String> imageNames = new ArrayList<String>();
        String Sql = "Select * from " + ImageTable.TableName;
        Cursor cursor = db.rawQuery(Sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                imageNames.add(cursor.getString(1));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return imageNames;
    }

    public List<Images> GetImages() {
        SQLiteDatabase db = context.getReadableDatabase();
        List<Images> imageNames = new ArrayList<Images>();
        String Sql = "Select * from " + ImageTable.TableName; //+ " Limit 1000";
        Cursor cursor = db.rawQuery(Sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                Images i = new Images();
                i.setId(cursor.getLong(0));
                i.setName(cursor.getString(1));
                i.setExtension(cursor.getString(2));
                i.setFileSize(cursor.getLong(3));
                i.setUriPath(cursor.getString(4));
                i.setFullPath(cursor.getString(5));
                i.setIsDetectionDone(cursor.getInt(7));
                imageNames.add(i);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return imageNames;
    }
}
