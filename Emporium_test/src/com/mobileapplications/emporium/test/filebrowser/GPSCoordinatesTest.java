package com.mobileapplications.emporium.test.filebrowser;

import com.mobileapplications.emporium.filebrowser.GPSCoordinates;

import android.test.AndroidTestCase;

public class GPSCoordinatesTest extends AndroidTestCase {

    public GPSCoordinatesTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testConstructors() {
        
        double latitude = 47.058344;
        double longitude = 15.460392;
        
        GPSCoordinates gps = new GPSCoordinates(longitude, "E", latitude, "N");
        assertEquals(latitude, gps.getLatitude(), 0.0);
        assertEquals(longitude, gps.getLongitude(), 0.0);
        assertEquals("E", gps.getLongRef());
        assertEquals("N", gps.getLatRef());
    }
    
    public void testLongToDegreeMinutesSeconds() {
        
        double latitude = 47.058344;
        double longitude = 15.460392;
        
        GPSCoordinates gps = new GPSCoordinates(longitude, "E", latitude, "N");
        assertNotNull(gps);
        
        String strLong = gps.longToDegreeMinutesSeconds();
        assertNotNull(strLong);
        assertEquals("15/1,27/1,3741/100", strLong);
    }
    
    public void testLatToDegreeMinutesSeconds() {
        
        double latitude = 47.058344;
        double longitude = 15.460392;
        
        GPSCoordinates gps = new GPSCoordinates(longitude, "E", latitude, "N");
        assertNotNull(gps);
        
        String strLat = gps.latToDegreeMinutesSeconds();
        assertNotNull(strLat);
        assertEquals("47/1,3/1,3003/100", strLat);
    }
    
    public void testToString() {
        
        double latitude = 47.058344;
        double longitude = 15.460392;
        
        GPSCoordinates gps = new GPSCoordinates(longitude, "E", latitude, "N");
        assertNotNull(gps);
        
        assertEquals("15.460392 E , 47.058344 N", gps.toString());
    }
}
