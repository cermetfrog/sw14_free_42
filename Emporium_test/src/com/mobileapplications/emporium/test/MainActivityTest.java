package com.mobileapplications.emporium.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.mobileapplications.emporium.MainActivity;
import com.mobileapplications.emporium.camera.ImageViewActivity;
import com.mobileapplications.emporium.dropbox.DbxFolderChooser;
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
    
    public void testInfoInImageViewActivity() {
        mySolo.clickOnText("Example.jpg");
        mySolo.assertCurrentActivity("Current activity should be ImageViewActivity", ImageViewActivity.class);
        
        mySolo.clickOnMenuItem("Info");
        if (mySolo.waitForDialogToOpen(3000))
            mySolo.clickOnButton("Close");
        else
            fail("Infobox not opend within 3 seconds.");
        
        mySolo.goBack();
    }
    
    public void testShowOnMapInImageViewActivity () {
        mySolo.clickOnText("ExampleGPS.jpg");
        mySolo.assertCurrentActivity("Current activity should be ImageViewActivity", ImageViewActivity.class);
        
        mySolo.clickOnMenuItem("Show on Map");
        mySolo.assertCurrentActivity("Current activity should be MapActivity", MapActivity.class);
        
        if (mySolo.waitForDialogToOpen(3000))
            mySolo.clickOnButton("Cancel");
        
        mySolo.goBack();
        mySolo.goBack();
    }
    
    public void testShowOnMapInImageViewActivityNoGPS () {
        mySolo.clickOnText("Example.jpg");
        mySolo.assertCurrentActivity("Current activity should be ImageViewActivity", ImageViewActivity.class);
        
        mySolo.clickOnMenuItem("Show on Map");
        
        if (mySolo.waitForDialogToOpen(3000))
            assertTrue(mySolo.searchText("NO GPS Information"));
            mySolo.clickOnButton("Okay");
        
        mySolo.goBack();
    }
    
    public void testShareDropboxInImageViewActivity() {
        mySolo.clickOnText("Example.jpg");
        mySolo.assertCurrentActivity("Current activity should be ImageViewActivity", ImageViewActivity.class);
        
        mySolo.clickOnMenuItem("Share Dropbox");
        //mySolo.assertCurrentActivity("Current activity should be DbxFolderChooser", DbxFolderChooser.class);
        //mySolo.sleep(2000);
        
        if (!mySolo.waitForActivity("DbxFolderChooser", 4000)){
            if (mySolo.waitForActivity("AuthActivity", 4000)){
            Log.d("WAIT FOR ACTIVITY!!!","ES FUNKTIONIERT! =)");
            }
        }
        Activity ac = mySolo.getCurrentActivity();
        Log.d("HERE WE ARE!!",ac.toString());
        

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
