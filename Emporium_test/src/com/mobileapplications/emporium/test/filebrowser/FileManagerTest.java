package com.mobileapplications.emporium.test.filebrowser;

import java.io.File;

import android.net.Uri;
import android.test.AndroidTestCase;

import com.mobileapplications.emporium.filebrowser.FileManager;

public class FileManagerTest extends AndroidTestCase {

    public FileManagerTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testGetOutputMediaFolder() {

        File folder = FileManager.getOutputMediaFolder();
        assertTrue(folder.isDirectory());
        assertTrue(folder.exists());
    }
    
    public void testGetOutputMediaFile() {
        File newFile = FileManager.getOutputMediaFile(FileManager.MEDIA_TYPE_IMAGE);
        
        assertNotNull(newFile);
        
        String fileName = newFile.getName();
        assertTrue(fileName.contains(".jpg"));
        
        assertFalse(newFile.exists());
    }
    
    public void testGetOutputMediaFileUri() {
        Uri uri = FileManager.getOutputMediaFileUri(FileManager.MEDIA_TYPE_IMAGE);
        assertNotNull(uri);
        
        uri = FileManager.getOutputMediaFileUri(-1);
        assertNull(uri);
    }
    
    public void testGetImageFileWithName() {
        File file = FileManager.getImageFileWithName("testimage.jpg");
        assertNotNull(file);
        assertTrue(file.isFile());
        
        file = FileManager.getImageFileWithName(null);
        assertNull(file);
        
        file = FileManager.getImageFileWithName("ssssfjeifgghssnsdsfsfjlweijrwelfsghreguehasdfh");
        assertNull(file);
    }
    
    public void testGetUriFromFile() {
        Uri uri = FileManager.getUriFromFile(null);
        assertNull(uri);
        
        File file = FileManager.getOutputMediaFolder();
        assertNotNull(file);
        uri = FileManager.getUriFromFile(file);
        assertNotNull(uri);
    }
}