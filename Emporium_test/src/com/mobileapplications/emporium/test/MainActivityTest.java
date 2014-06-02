package com.mobileapplications.emporium.test;

import com.mobileapplications.emporium.MainActivity;
import com.mobileapplications.emporium.camera.CameraActivity;
import com.mobileapplications.emporium.dropbox.DbxFolderContentListActivity;
import com.mobileapplications.emporium.maps.MapActivity;
import com.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;

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

    public void testButtons()
    {
        mySolo.clickOnButton("Dropbox");
        mySolo.assertCurrentActivity("Current activity should be DbxFoldercontentListActivity", DbxFolderContentListActivity.class);
        mySolo.goBack();
        
        mySolo.clickOnButton("Map");
        mySolo.assertCurrentActivity("Current activity should be MapActivity", MapActivity.class);
        mySolo.goBack();
        
        mySolo.clickOnButton("Camera");
        mySolo.assertCurrentActivity("Current activity should be CameraActivity", CameraActivity.class);
        mySolo.goBack();
    }
}
