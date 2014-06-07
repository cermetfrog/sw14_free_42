package com.mobileapplications.emporium.filebrowser;

import java.io.File;
import java.io.IOException;

import android.media.ExifInterface;
import android.util.Log;

public class ExifInfo {
    
    private static final String LOG_TAG = "ExifInfo";

    
    private String dateTime;
    private GPSCoordinates gpsCoordinates;
    private int length;
    private int width;

    
    /** Extract GPS coordinates from an images Exif-data **/
    public static ExifInfo fromImage(File imageFile) {
        
        if (imageFile == null) return null;
        
        ExifInterface exif = null;
        try {
             exif = new ExifInterface(imageFile.getPath());
        }
        catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
            return null;
        }
        
        String dt = exif.getAttribute(ExifInterface.TAG_DATETIME);
        int len = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
        int width = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
        
        double alt = exif.getAttributeDouble(ExifInterface.TAG_GPS_ALTITUDE, -1.0);
        int altRef = exif.getAttributeInt(ExifInterface.TAG_GPS_ALTITUDE_REF, -1);
        
        float[] longlat = new float[2];
        if (!exif.getLatLong(longlat)) {
            longlat[0] = -1.0f;
            longlat[1] = -1.0f;
        }
        
        String latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
        String longRef = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
        
        GPSCoordinates gps = new GPSCoordinates(longlat[0],longRef,
                longlat[1],latRef,
                alt, altRef);
        
        ExifInfo info = new ExifInfo(dt,len,width,gps);
        
        return info;
    }
    
   
    
    public ExifInfo(String dateTime, int length, int width, GPSCoordinates gpsCoordinates) {
        this.dateTime = dateTime;
        this.length = length;
        this.width = width;
        this.gpsCoordinates = gpsCoordinates;
    }



    public String getDateTime() {
        return dateTime;
    }



    public GPSCoordinates getGpsCoordinates() {
        return gpsCoordinates;
    }



    public int getLength() {
        return length;
    }



    public int getWidth() {
        return width;
    }
    
    public String toString() {
        String ret = "";
        ret = ret + "Date/Time: " + dateTime;
        ret = ret + "\n" + "Image length: " + length;
        ret = ret + "\n" + "Image width: " + width;
        
        if (gpsCoordinates != null) {
            
            if (gpsCoordinates.getAltitude() >= 0.0) {
                ret = ret + "\n" + "GPS Altitude: " + gpsCoordinates.getAltitude();
            }
            if (gpsCoordinates.getLongitude() >= 0.0) {
                ret = ret + "\n" + "GPS Longitude: " + gpsCoordinates.getLongitude() + " " +
                        gpsCoordinates.getLongRef();
            }
            if (gpsCoordinates.getLatitude() >= 0.0) {
                ret = ret + "\n" + "GPS Latitude: " + gpsCoordinates.getLatitude() + " " +
                        gpsCoordinates.getLatRef();
            }
        }

        return ret;
    }
}
