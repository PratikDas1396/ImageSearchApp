package com.imagesearch.app.database.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.util.Log;

import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.ImageLabelDataModel;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Models.LabelImageDataModel;
import com.imagesearch.app.database.Tables.ImageLabelMappingTable;
import com.imagesearch.app.database.Tables.ImageTable;
import java.util.stream.Stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageLableMappingRepository {

    private DatabaseInitializer context;
    private int TopLabels = 10;
    private int NoOfImages = 10;

    public ImageLableMappingRepository() {
    }

    public ImageLableMappingRepository(DatabaseInitializer context){
        this.context = context;
    }

    public long Add(ImageLabelMapping mapping){
        SQLiteDatabase db = context.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ImageLabelMappingTable.LabelID, mapping.getLabelID());
        cv.put(ImageLabelMappingTable.LabelName, mapping.getLabelName());
        cv.put(ImageLabelMappingTable.ImageId, mapping.getImageId());
        cv.put(ImageLabelMappingTable.ImageName, mapping.getImageName());
        cv.put(ImageLabelMappingTable.FullPath, mapping.getFullPath());
        cv.put(ImageLabelMappingTable.UriPath, mapping.getUriPath());

        if(mapping.getCreatedDtim() != null)
            cv.put(ImageLabelMappingTable.CreatedDtim, mapping.getCreatedDtim().toString());

        if(mapping.getUpdatedDtim() != null)
            cv.put(ImageLabelMappingTable.UpdatedDtim, mapping.getUpdatedDtim().toString());

        return db.insert(ImageLabelMappingTable.TableName, null, cv);
    }

    public long Add(SQLiteDatabase db,ImageLabelMapping mapping){
        ContentValues cv = new ContentValues();
        cv.put(ImageLabelMappingTable.LabelID, mapping.getLabelID());
        cv.put(ImageLabelMappingTable.LabelName, mapping.getLabelName());
        cv.put(ImageLabelMappingTable.ImageId, mapping.getImageId());
        cv.put(ImageLabelMappingTable.ImageName, mapping.getImageName());
        cv.put(ImageLabelMappingTable.FullPath, mapping.getFullPath());
        cv.put(ImageLabelMappingTable.UriPath, mapping.getUriPath());

        if(mapping.getCreatedDtim() != null)
            cv.put(ImageLabelMappingTable.CreatedDtim, mapping.getCreatedDtim().toString());

        if(mapping.getUpdatedDtim() != null)
            cv.put(ImageLabelMappingTable.UpdatedDtim, mapping.getUpdatedDtim().toString());

        return db.insert(ImageLabelMappingTable.TableName, null, cv);
    }

    public ImageLabelDataModel GetLabelsByImages (long ImageID) {
        ImageLabelDataModel data = new ImageLabelDataModel();
        ArrayList<Label> labels = new ArrayList<Label>();

        String Sql = "select "
                + "map." + ImageLabelMappingTable.Id
                + ", map." + ImageLabelMappingTable.LabelID
                + ", map." + ImageLabelMappingTable.LabelName
                + ", map." + ImageLabelMappingTable.ImageId
                + ", map." + ImageLabelMappingTable.ImageName
                + ", map." + ImageLabelMappingTable.FullPath
                + ", img." + ImageTable.FileSize
                + ", img." + ImageTable.Extension
                + ", img." + ImageTable.ImageCreationDate
                + ", img." + ImageTable.UriPath
                + ", img." + ImageTable.isDetectionDone
                + " from " + ImageLabelMappingTable.TableName + " map "
                + "inner join " + ImageTable.TableName
                + " img on map. " + ImageLabelMappingTable.ImageId + " = img." + ImageTable.Id
                + " where extension <> '' and map." + ImageLabelMappingTable.ImageId + " =? " ;

        SQLiteDatabase db = context.getReadableDatabase();
        Cursor cursor = db.rawQuery(Sql, new String[]{ Long.toString(ImageID) });

        if (cursor.moveToFirst()) {
            do {
                Label l = new Label();
                l.setID(cursor.getLong(1));
                l.setLabelName(cursor.getString(2));
                labels.add(l);
            }
            while (cursor.moveToNext());
        }
        cursor.moveToFirst();
        data.setId(cursor.getLong(3));
        data.setName(cursor.getString(4));
        data.setFullPath(cursor.getString(5));
        data.setFileSize(cursor.getLong(6));
        data.setExtension(cursor.getString(7));
        data.setUriPath(cursor.getString(9));
        data.setIsDetectionDone(cursor.getInt(10));
        data.setLabels(labels);

        cursor.close();
        return data;
    }

    public LabelImageDataModel GetImagesByLabels (long LabelID, int MaxImages) {
        LabelImageDataModel data = new LabelImageDataModel();
        ArrayList<Images> images = new ArrayList<Images>();

        String Sql = "select "
                + "map." + ImageLabelMappingTable.Id
                + ", map." + ImageLabelMappingTable.LabelID
                + ", map." + ImageLabelMappingTable.LabelName
                + ", map." + ImageLabelMappingTable.ImageId
                + ", map." + ImageLabelMappingTable.ImageName
                + ", map." + ImageLabelMappingTable.FullPath
                + ", img." + ImageTable.FileSize
                + ", img." + ImageTable.Extension
                + ", img." + ImageTable.ImageCreationDate
                + ", img." + ImageTable.UriPath
                + ", img." + ImageTable.isDetectionDone
                + " from " + ImageLabelMappingTable.TableName + " map "
                + "inner join " + ImageTable.TableName
                + " img on map. " + ImageLabelMappingTable.ImageId + " = img." + ImageTable.Id
                + " where extension <> '' and map." + ImageLabelMappingTable.LabelID + " =? "
                + (MaxImages == 0 ? "" : " LIMIT " + MaxImages);

        SQLiteDatabase db = context.getReadableDatabase();
        Cursor cursor = db.rawQuery(Sql, new String[]{ Long.toString(LabelID) });

        if (cursor.moveToFirst()) {
            do {
                Images img = new Images();
                img.setId(cursor.getLong(3));
                img.setName(cursor.getString(4));
                img.setFullPath(cursor.getString(5));
                img.setFileSize(cursor.getLong(6));
                img.setExtension(cursor.getString(7));
                img.setUriPath(cursor.getString(9));
                img.setIsDetectionDone(cursor.getInt(10));
                images.add(img);
            }
            while (cursor.moveToNext());
        }
        cursor.moveToFirst();
        data.setID(cursor.getLong(1));
        data.setLabelName(cursor.getString(2));
        data.setImages(images);
        cursor.close();
        return data;
    }

    public List<Label> GetTopLabels(int labelCount){
        ArrayList<Label> labels = new ArrayList<Label>();

        String Sql = "select "
                + "  map." + ImageLabelMappingTable.LabelID
                + ", map." + ImageLabelMappingTable.LabelName
                + ", count(map." + ImageLabelMappingTable.FullPath + ")"
                + " from " + ImageLabelMappingTable.TableName + " map "
                + " Group by map." +   ImageLabelMappingTable.LabelID + ", map." + ImageLabelMappingTable.LabelName
                + " order by 3 desc " + (labelCount == 0 ? "" : "  LIMIT " +  labelCount ) ;

        SQLiteDatabase db = context.getReadableDatabase();
        Cursor cursor = db.rawQuery(Sql, new String[]{  });

        if (cursor.moveToFirst()) {
            do {
                Label l = new Label();
                l.setID(cursor.getLong(0));
                l.setLabelName(cursor.getString(1));
                labels.add(l);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }

    public  List<LabelImageDataModel> GetTrendingLabels(){
        List<Label> labels  = this.GetTopLabels(this.TopLabels);
        List<LabelImageDataModel> mapping = new ArrayList<LabelImageDataModel>();
        labels.stream().forEach(x -> mapping.add(this.GetImagesByLabels(x.getID(), this.NoOfImages)));
        return mapping;
    }

    public List<ImageLabelMapping> GetMapping(){
        SQLiteDatabase db = context.getReadableDatabase();
        List<ImageLabelMapping> imageNames = new ArrayList<ImageLabelMapping>();
        String Sql = "Select * from " + ImageLabelMappingTable.TableName; //+ " Limit 1000";
        Cursor cursor = db.rawQuery(Sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                ImageLabelMapping i = new ImageLabelMapping();
                i.setDetectionId(cursor.getInt(0));
                i.setLabelID(cursor.getInt(1));
                i.setLabelName(cursor.getString(2));
                i.setImageId(cursor.getInt(3));
                i.setImageName(cursor.getString(4));
                i.setFullPath(cursor.getString(5));
                i.setUriPath((cursor.getString(6)));
                imageNames.add(i);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return imageNames;
    }

}
