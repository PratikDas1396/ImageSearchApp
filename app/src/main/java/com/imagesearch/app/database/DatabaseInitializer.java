package com.imagesearch.app.database;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.imagesearch.app.CommonClass.ImageFileFilter;
import com.imagesearch.app.database.Models.ImageLabelMapping;
import com.imagesearch.app.database.Models.Images;
import com.imagesearch.app.database.Models.Label;
import com.imagesearch.app.database.Repository.ImageLableMappingRepository;
import com.imagesearch.app.database.Repository.ImagesRepository;
import com.imagesearch.app.database.Repository.LabelRepository;
import com.imagesearch.app.database.Tables.ImageLabelMappingTable;
import com.imagesearch.app.database.Tables.ImageTable;
import com.imagesearch.app.database.Tables.LabelTable;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatabaseInitializer extends SQLiteOpenHelper {
    private static String Database_Name = "ImageSearchApp.db";
    public Context context;

    //table context
    LabelTable LabelTable;
    ImageTable ImageTable;
    ImageLabelMappingTable ImagesLabelMappingTable;

    private String ExternalStoragePath = Environment.getExternalStorageDirectory().getPath();

    public DatabaseInitializer(Context context) {
        //Database creation
        super(context, Database_Name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LabelTable = new LabelTable(db);
        ImageTable = new ImageTable(db);
        ImagesLabelMappingTable = new ImageLabelMappingTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}