package com.mobileapplications.emporium.test;

import com.mobileapplications.emporium.MainActivity;
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
        //mySolo.clickOnButton("Map");
        //mySolo.clickOnButton("Camera");
    }
}
