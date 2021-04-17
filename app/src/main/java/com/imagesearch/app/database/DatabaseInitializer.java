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
    private static String Database_Name = "TEST_DATABASE.db";
    public  Context context;

    //table context
    LabelTable LabelTable;
    ImageTable ImageTable;
    ImageLabelMappingTable ImagesLabelMappingTable;

    //Repository
    ImagesRepository ImageRepo;
    ImageLableMappingRepository mappingRepository;
    ArrayList<String> labelList = new ArrayList<String>();

    private String ExternalStoragePath = Environment.getExternalStorageDirectory().getPath();

    public DatabaseInitializer(Context context){
        //Database creation
        super(context, Database_Name, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LabelTable = new LabelTable(db);
        ImageTable = new ImageTable(db);
        ImagesLabelMappingTable = new ImageLabelMappingTable(db);

        LabelRepository repo = new LabelRepository();

        for(int i = 1; i <= 100; i++){
            Label l1 = new Label();
            l1.setLabelName("Label " + i) ;
            labelList.add("Label " + i);
            repo.Add(db, l1);
        }

        ImageRepo = new ImagesRepository();
        mappingRepository = new ImageLableMappingRepository();

        SaveImages(db, ExternalStoragePath);
    }

    private void SaveImages(SQLiteDatabase db, String directoryPath) {
        File[] files = new File(directoryPath).listFiles(new ImageFileFilter());
        for (File file : files) {
            if (file.isDirectory() && file.listFiles(new ImageFileFilter()).length > 0) {
                SaveImages(db, file.getAbsolutePath());
            }
            else {
                try{
                    Images img = new Images();
                    String extension = FilenameUtils.getExtension(file.getName());

                    if(extension == "" || extension == null ){
                        return;
                    }

                    //Read Creation Date time
                    BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class);
                    img.setName(file.getName());
                    img.setExtension(FilenameUtils.getExtension(file.getName()));
                    img.setPath(file.getAbsolutePath());
                    img.setFullPath(file.getAbsolutePath());
                    img.setFileSize(file.length());
                    img.setImageCreationDate(new Date(attr.creationTime().to(TimeUnit.MILLISECONDS)));
                    long key  = ImageRepo.Add(db, img);

                    int random = (int)(Math.random()*(10)+1);
                    ArrayList randomNumbers = new ArrayList();

                    while(randomNumbers.size() != random){
                        int randomLabel = (int)(Math.random()*(100)+1);
                        if(!randomNumbers.contains(randomLabel)){
                            randomNumbers.add(randomLabel);
                            ImageLabelMapping map = new ImageLabelMapping();
                            map.setLabelID(randomLabel);
                            map.setLabelName("Label " + randomLabel);
                            map.setImageId(key);
                            map.setImageName(file.getName());
                            map.setFullPath(file.getAbsolutePath());
                            mappingRepository.Add(db, map);
                        }
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}