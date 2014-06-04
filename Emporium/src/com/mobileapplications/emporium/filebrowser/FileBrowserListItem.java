package com.mobileapplications.emporium.filebrowser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class FileBrowserListItem {
    
    String text;
    Drawable icon;
    Context context;
    
    public static FileBrowserListItem newInstance(Context context, File file) {
        if (file == null || context == null) return null;
        return new FileBrowserListItem(context, file);
    }
    
    public static List<FileBrowserListItem> newInstance(Context context, File[] fileArray) {
        if (fileArray == null || context == null) return null;
        
        List<FileBrowserListItem> list = new ArrayList<FileBrowserListItem>(fileArray.length);
        for (File file : fileArray) {
            list.add(FileBrowserListItem.newInstance(context, file));
        }
        
        return list;
    }
    
    public static FileBrowserListItem newInstance(Context context, String text, Drawable icon) {
        if (context == null || text == null || icon == null) return null;
        return new FileBrowserListItem(context,text,icon);
    }
    
    
    
    
    private FileBrowserListItem(Context context, File file) {
        this.context = context;
        this.text = file.getName();
        int resId = context.getResources().getIdentifier("image_icon_small", "drawable", context.getPackageName());
        this.icon = context.getResources().getDrawable(resId);

    }
    
    private FileBrowserListItem(Context context, String text, Drawable icon) {
        this.text = text;
        this.icon = icon;
        this.context = context;
    }
    
    public String getText() {
        return text;
    }
    
    public Drawable getIcon() {
        return this.icon;
    }

}
