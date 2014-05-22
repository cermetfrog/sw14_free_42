package com.mobileapplications.emporium.test.dropbox;

import com.mobileapplications.emporium.dropbox.DbxListItem;

import android.test.AndroidTestCase;

public class DbxListItemTest extends AndroidTestCase {
    
    public DbxListItemTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testConstructor() {
        
        try {
            DbxListItem item = new DbxListItem(null,null);
            assertNotNull(item);
        }
        catch (Exception ex) {
            
        }
    }
}
