package com.imagesearch.app.database.Tables;

import android.database.sqlite.SQLiteDatabase;

public class LabelTable {

    // Table Name
    public static final String TableName = "Labels";

    //Column Names
    public static final String ID = "ID";
    public static final String labelName = "LabelName";

    public LabelTable(SQLiteDatabase db){
        db.execSQL("create table "+ TableName + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + labelName + " TEXT)");
    }
}
