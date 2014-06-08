package com.mobileapplications.emporium.filebrowser;

import java.io.File;
import java.io.IOException;

import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

public class GPSCoordinates {
    
    public static final String TAG_LONGITUDE = "longitude";
    public static final String TAG_LATITUDE = "latitude";
    public static final String TAG_ALTITUDE = "altitude";

    public static final String TAG_LONGITUDE_REF = "longitudeRef";
    public static final String TAG_LATITUDE_REF = "latitudeRef";
    public static final String TAG_ALTITUDE_REF = "altitudeRef";

    private static final String LOG_TAG = "GPSCoordinates";
    
    private double mlongitude;
    private double mlatitude;
    private double maltitude;
    private String mlongitudeRef;
    private String mlatitudeRef;
    private int maltitudeRef;
    
    
    
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
        
        GPSCoordinates gps = new GPSCoordinates(longlat[0],longRef,longlat[1],latRef);
        
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
        maltitude = 0.0;
        maltitudeRef = 0;
    }
    
    public GPSCoordinates(double longitude, String longRef, 
            double latitude, String latRef,
            double altitude, int altitudeRef) {
        this.mlongitude = longitude;
        this.mlatitude = latitude;
        this.mlongitudeRef = longRef;
        this.mlatitudeRef = latRef;
        this.maltitude = altitude;
        this.maltitudeRef = altitudeRef;
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
    
    public double getAltitude() {
        return maltitude;
    }
    
    public int getAltitudeRef() {
        return maltitudeRef;
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
