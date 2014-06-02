package com.mobileapplications.emporium.test.dropbox;

import java.io.File;

import junit.framework.Assert;

import com.dropbox.sync.android.DbxPath;
import com.mobileapplications.emporium.dropbox.DbxManager;
import com.mobileapplications.emporium.filebrowser.FileManager;

import android.os.Environment;
import android.test.InstrumentationTestCase;
import android.util.Log;

public class DbxManagerTest extends InstrumentationTestCase {

    private DbxManager dbxManager;
    
    public DbxManagerTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        dbxManager = DbxManager.getInstance(getInstrumentation().getTargetContext().getApplicationContext());
        Assert.assertNotNull("DbxManager.getInstance() must not return NULL", dbxManager);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testUploadFileToDbxPath() {
        boolean rc = dbxManager.uploadFileToDbxPath(null, null);
        assertEquals(false,rc);
        
        File rootFolder = FileManager.getOutputMediaFolder();
        
        File testimageFile = new File(rootFolder,"testimage.jpg");
        assertTrue(testimageFile.exists());
        
        assertTrue(dbxManager.hasLinkedAccount());
        
        rc = dbxManager.uploadFileToDbxPath(testimageFile, DbxPath.ROOT);
        assertTrue(rc);
    }
    
//    public void testUnlinkAccount()
//    {
//        dbxManager.unlinkAccount();
//        assertEquals(false, dbxManager.hasLinkedAccount());
//    }

}
