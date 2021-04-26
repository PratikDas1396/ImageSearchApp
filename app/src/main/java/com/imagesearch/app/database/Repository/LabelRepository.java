package com.imagesearch.app.database.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.imagesearch.app.database.DatabaseInitializer;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Tables.LabelTable;

import java.util.ArrayList;
import java.util.List;

public class LabelRepository extends Label {
    private DatabaseInitializer context;
    private SQLiteDatabase db;

    public LabelRepository() {
    }

    public LabelRepository(DatabaseInitializer context) {
        this.context = context;
    }

    public long Add(Label label) {
        SQLiteDatabase db = context.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LabelTable.labelName, label.getLabelName());
        return db.insert(LabelTable.TableName, null, cv);
    }

    public long Add(SQLiteDatabase db, Label label) {
        ContentValues cv = new ContentValues();
        cv.put(LabelTable.labelName, label.getLabelName());
        return db.insert(LabelTable.TableName, null, cv);
    }

    public long Add(String labelName) {
        SQLiteDatabase db = context.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LabelTable.labelName, labelName);
        return db.insert(LabelTable.TableName, null, cv);
    }

    public long Add(SQLiteDatabase db, String labelName) {
        ContentValues cv = new ContentValues();
        cv.put(LabelTable.labelName, labelName);
        return db.insert(LabelTable.TableName, null, cv);
    }

    public List<Label> GetLabels() {
        List<Label> labels = new ArrayList<Label>();
        String Sql = "Select * from " + LabelTable.TableName + " order by " + LabelTable.ID;
        ;
        SQLiteDatabase db = context.getReadableDatabase();
        Cursor cursor = db.rawQuery(Sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                Label l1 = new Label();
                l1.setID(cursor.getLong(0));
                l1.setLabelName(cursor.getString(1));
                labels.add(l1);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }

    public List<String> GetLabelNames() {
        List<String> labels = new ArrayList<String>();
        SQLiteDatabase db = context.getReadableDatabase();
        String Sql = "Select * from " + LabelTable.TableName + " order by " + LabelTable.labelName;
        Cursor cursor = db.rawQuery(Sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }

    public List<Label> GetLabels(SQLiteDatabase db) {
        List<Label> labels = new ArrayList<Label>();
        String Sql = "Select * from " + LabelTable.TableName;
        Cursor cursor = db.rawQuery(Sql, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                Label l1 = new Label();
                l1.setID(cursor.getInt(0));
                l1.setLabelName(cursor.getString(1));
                labels.add(l1);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }
}
