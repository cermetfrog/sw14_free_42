package com.mobileapplications.emporium.test.dropbox;

import junit.framework.Assert;
import com.mobileapplications.emporium.dropbox.DbxManager;

import android.test.InstrumentationTestCase;

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


    public void testUnlinkAccount()
    {
        dbxManager.unlinkAccount();
        assertEquals(false, dbxManager.hasLinkedAccount());
    }
    
    
}
