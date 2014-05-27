package com.mobileapplications.emporium.test.dropbox;

import com.mobileapplications.emporium.dropbox.DbxFolderContentListActivity;

import android.test.ActivityInstrumentationTestCase2;

public class DbxFolderContentListActivityTest extends
        ActivityInstrumentationTestCase2<DbxFolderContentListActivity> {

    private DbxFolderContentListActivity activity;
    
    public DbxFolderContentListActivityTest() {
        super(DbxFolderContentListActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

}
