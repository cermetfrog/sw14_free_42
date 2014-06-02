package com.mobileapplications.emporium.filebrowser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class FileManager {
    
    // Static Constants
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    
    
    /** Creates an Uri from a given File **/ 
    public static Uri getUriFromFile(File file) {
        if (file == null) return null;
        return Uri.fromFile(file);
    }
    
    /** Create a File for a given imageName if that file exists */
    public static File getImageFileWithName(String fileName) {
        
        if (fileName == null) return null;
        
        File imageFile = new File(getOutputMediaFolder(),fileName);
        if (! imageFile.exists()) return null;
        
        return imageFile;
    }
    
    /** Create a file Uri for saving an image or video */
    public static Uri getOutputMediaFileUri(int type) {
        File file = getOutputMediaFile(type);
        if (file == null) return null;
        return Uri.fromFile(file);
    }
    
    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(int type) {
        
        File mediaStorageDir = getOutputMediaFolder();
        if (mediaStorageDir == null) return null;

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.GERMAN).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    
    /** Create root folder for saving an image or video */
    public static File getOutputMediaFolder() {
      // To be safe, you should check that the SDCard is mounted
      // using Environment.getExternalStorageState() before doing this.
        
      File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Emporium");
      // This location works best if you want the created images to be shared
      // between applications and persist after your app has been uninstalled.

      // Create the storage directory if it does not exist
      if (! mediaStorageDir.exists()){
          if (! mediaStorageDir.mkdirs()){
              Log.d("Emporium: CameraApp", "failed to create directory");
              return null;
          }
      }
      return mediaStorageDir;
    }
}
