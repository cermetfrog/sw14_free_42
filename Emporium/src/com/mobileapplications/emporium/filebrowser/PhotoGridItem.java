package com.mobileapplications.emporium.filebrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;

public class PhotoGridItem {
    
    String text;
    Uri fileUri;
    
    public static PhotoGridItem newInstance(File file) {
        if (file == null) return null;
        
        return new PhotoGridItem(file);
    }
    
    public static List<PhotoGridItem> newInstance(File[] fileArray) {
        if (fileArray == null) return null;
        
        List<PhotoGridItem> list = new ArrayList<PhotoGridItem>(fileArray.length);
        for (File file : fileArray) {
            list.add(PhotoGridItem.newInstance(file));
        }
        
        return list;
    }
    
    private PhotoGridItem(File file) {
        this.text = file.getName();
        this.fileUri = Uri.fromFile(file);
    }
    
    public String getText() {
        return "Picture Text";
    }
    
    public Uri getFileUri() {
        return this.fileUri;
    }

}
