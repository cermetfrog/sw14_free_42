package com.mobileapplications.emporium.filebrowser;

import java.io.File;
import java.io.IOException;

import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

public class GPSCoordinates {
    
    private static String LOG_TAG = "GPSCoordinates";
    
    private double mlongitude;
    private double mlatitude;
    private String mlongitudeRef;
    private String mlatitudeRef;
    
    
    
    public static GPSCoordinates fromImage(Uri imageUri) {
        if (imageUri == null) return null;
        return GPSCoordinates.fromImage(new File(imageUri.getPath()));
    }
    
    /** Extract GPS coordinates from an images Exif-data **/
    public static GPSCoordinates fromImage(File imageFile) {
        
        if (imageFile == null) return null;
        
        ExifInterface exif = null;
        try {
             exif = new ExifInterface(imageFile.getPath());
        }
        catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
            return null;
        }
        
        float[] longlat = new float[2];
        if (!exif.getLatLong(longlat)) return null;
        
        String latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        String longRef = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        
        GPSCoordinates gps = new GPSCoordinates(longlat,longRef,latRef);
        
        
        return gps;
    }
    
    
    /**
     * Constructor
     */
    public GPSCoordinates(double longitude, String longRef, double latitude, String latRef) {
        this.mlongitude = longitude;
        this.mlatitude = latitude;
        this.mlongitudeRef = longRef;
        this.mlatitudeRef = latRef;
    }
    
    public GPSCoordinates(float[] longlat, String longRef, String latRef) {
        this.mlongitude = (double)longlat[0];
        this.mlatitude = (double)longlat[1];
        this.mlongitudeRef = longRef;
        this.mlatitudeRef = latRef;
    }
    

    /**
     * Getter methods
     */
    public double getLongitude() {
        return mlongitude;
    }
    
    public double getLatitude() {
        return mlatitude;
    }
    
    public String getLongRef() {
        return mlongitudeRef;
    }
    
    public String getLatRef() {
        return mlatitudeRef;
    }
    
    
    public String toString() {
        return mlongitude + " " + mlongitudeRef + " , " + mlatitude + " " + mlatitudeRef;
    }
    
    
    public String longToDegreeMinutesSeconds() {
        
        double degree = 0.0;
        double minutes = 0.0;
        double seconds = 0.0;
        
        double kDegree = 1.0;
        double kMinutes = 1.0;
        double kSeconds = 100.0;
        
        degree = Math.floor(mlongitude);
        minutes = Math.floor((mlongitude - degree) * 60);
        seconds = Math.floor((mlongitude - degree - minutes/60) * 3600 * kSeconds);
        
        String ret = (int)degree + "/" + (int)kDegree + "," 
                   + (int)minutes + "/" + (int)kMinutes + "," 
                   + (int)seconds + "/" + (int)kSeconds;
        
        Log.d(LOG_TAG,ret);
        return ret;
    }
    
    public String latToDegreeMinutesSeconds() {
        
        double degree = 0.0;
        double minutes = 0.0;
        double seconds = 0.0;
        
        double kDegree = 1.0;
        double kMinutes = 1.0;
        double kSeconds = 100.0;
        
        degree = Math.floor(mlatitude);
        minutes = Math.floor((mlatitude - degree) * 60);
        seconds = Math.floor((mlatitude - degree - minutes/60) * 3600 * kSeconds);
        
        String ret = (int)degree + "/" + (int)kDegree + "," 
                   + (int)minutes + "/" + (int)kMinutes + "," 
                   + (int)seconds + "/" + (int)kSeconds;
        
        Log.d(LOG_TAG,ret);
        return ret;
    }
    
}
