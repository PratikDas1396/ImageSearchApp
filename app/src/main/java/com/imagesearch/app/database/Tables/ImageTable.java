package com.imagesearch.app.database.Tables;

import android.database.sqlite.SQLiteDatabase;

public class ImageTable {
    // Table Name
    public static final String TableName = "Images";

    //Column Name
    public static final String Id = "Id";
    public static final String Name = "Name";
    public static final String Extension = "Extension";
    public static final String Path = "Path";
    public static final String FullPath = "FullPath";
    public static final String FileSize = "FileSize";
    public static final String ImageCreationDate = "ImageCreationDate";
    public static final String CreatedDtim = "CreatedDtim";
    public static final String UpdatedDtim = "UpdatedDtim";

    public ImageTable(SQLiteDatabase db){
        String Sql = "create table " + TableName + " ( "
                + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Name +  " Text, "
                + Extension +  " Text, "
                + FileSize +  " INTEGER, "
                + Path +  " Text, "
                + FullPath +  " Text, "
                + ImageCreationDate +  " Text, "
                + CreatedDtim +  " Text, "
                + UpdatedDtim +  " Text "
                + ")";

        db.execSQL(Sql);
    }

}
