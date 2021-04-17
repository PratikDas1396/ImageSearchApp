package com.imagesearch.app.database.Tables;

import android.database.sqlite.SQLiteDatabase;

public class ImageLabelMappingTable {

    public static final String TableName = "ImageLabelMapping";

    public static final String Id = "Id";
    public static final String LabelID = "LabelID";
    public static final String LabelName = "LabelName";
    public static final String ImageId = "ImageId";
    public static final String ImageName = "ImageName";
    public static final String FullPath = "FullPath";
    public static final String CreatedDtim = "CreatedDtim";
    public static final String UpdatedDtim = "UpdatedDtim";

    public ImageLabelMappingTable(SQLiteDatabase db){
        String Sql = "create table " + TableName + " ( "
                + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LabelID +  " INTEGER, "
                + LabelName +  " Text, "
                + ImageId +  " INTEGER, "
                + ImageName +  " Text, "
                + FullPath +  " Text, "
                + CreatedDtim +  " Text, "
                + UpdatedDtim +  " Text "
                + ")";

        db.execSQL(Sql);
    }
}
