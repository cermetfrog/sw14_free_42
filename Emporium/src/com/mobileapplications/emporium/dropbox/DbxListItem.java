package com.mobileapplications.emporium.dropbox;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.dropbox.sync.android.DbxFileInfo;

public class DbxListItem {

    private String text;
    private Drawable drawable;
    private Context context;

    public DbxListItem(Context context, String text, Drawable drawable) {
        this.context = context;
        this.text = text;
        this.drawable = drawable;
    }
    
    
    public DbxListItem(Context context, DbxFileInfo fileInfo) {
        this.context = context;
        if (fileInfo != null) {
            text = fileInfo.path.getName();
            drawable = getDrawableFromDbxFileInfo(fileInfo);
        }
    }
    
    public String getText() {
        return this.text;
    }
    
    public Drawable getDrawable() {
        return this.drawable;
    }
    
    
    private Drawable getDrawableFromDbxFileInfo(DbxFileInfo fileInfo) {
        
        int resId = context.getResources().getIdentifier(fileInfo.iconName, "drawable", context.getPackageName());
        
        if (resId == 0) {
            return null;
        } else {
            return context.getResources().getDrawable(resId);
        }
    }
}
