package com.imagesearch.app.CommonClass;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class ImageFileFilter implements FileFilter {

    ArrayList miscellaneous = new ArrayList();

    public ImageFileFilter() {
        miscellaneous.add("Android");
    }

    @Override
    public boolean accept(File file) {
        if(isMiscellaneous(file.getName()))
            return false;

        if (isHiddenFolder(file.getName()) || isHidden(file))
            return false;

        if (file.isDirectory())
            return true;

        if (isImageFile(file.getAbsolutePath()))
            return true;

        return false;
    }

    private boolean isMiscellaneous(String Name) {
        return miscellaneous.contains(Name);
    }

    private boolean isHiddenFolder(String name) {
        return name.startsWith(".");
    }

    private boolean isHidden(File file) {
        return file.isHidden();
    }

    private boolean isImageFile(String filePath) {
        return filePath.endsWith(".jpg") || filePath.endsWith(".png");
    }
}
