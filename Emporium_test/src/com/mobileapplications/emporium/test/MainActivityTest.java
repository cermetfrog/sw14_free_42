package com.mobileapplications.emporium.test;

import android.app.Activity;
import android.provider.Settings;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.maps.GoogleMap;
import com.mobileapplications.emporium.MainActivity;
import com.mobileapplications.emporium.maps.GPSTracker;
import com.mobileapplications.emporium.maps.MapActivity;
import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mySolo;
    
    public MainActivityTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMapActivity()
    {
        mySolo.clickOnMenuItem("Map");
        mySolo.assertCurrentActivity("Current activity should be MapActivity", MapActivity.class);
        if (mySolo.waitForDialogToOpen(3000))
        	mySolo.clickOnButton("Cancel");
        mySolo.goBack();
    }
    
    public void testMapSettings()
    {
        mySolo.clickOnMenuItem("Map");
        mySolo.assertCurrentActivity("Current activity should be MapActivity", MapActivity.class);
        
        if (mySolo.waitForDialogToOpen(3000))
        {
        	mySolo.clickOnButton("Settings");
            mySolo.goBack();
        }
        mySolo.goBack();
    }
    
    public void testMapSetSettings()
    {
        mySolo.clickOnMenuItem("Map");
        mySolo.assertCurrentActivity("Current activity should be MapActivity", MapActivity.class);
        MapActivity tempMapActivity =  (MapActivity) mySolo.getCurrentActivity();

        float zoom = 0;
        boolean trackable = false;
        
        if (mySolo.waitForDialogToOpen(3000))
        {
        	mySolo.clickOnButton("Settings");
        	mySolo.waitForDialogToClose();
        }
              
        GPSTracker tracker = tempMapActivity.getTrack();
        trackable = tracker.canGetLocation();
        zoom = tempMapActivity.getZoom();
        
        if(trackable)
            assertTrue(zoom != 0);
        else
        	assertTrue(zoom == 0);
        	
        mySolo.goBack();
        mySolo.goBack();
        
    }    
    /*
    public void testRestActivity()
    {
    	
    	mySolo.clickOnMenuItem("Dropbox"); //"@emporium:string/action_dropbox");
        mySolo.assertCurrentActivity("Current activity should be DbxFoldercontentListActivity", DbxFolderContentListActivity.class);
        mySolo.goBack();
                
        mySolo.clickOnMenuItem("Camera");
        mySolo.assertCurrentActivity("Current activity should be CameraActivity", CameraActivity.class);
        mySolo.goBack();
    }*/
}
