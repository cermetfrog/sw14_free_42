package com.mobileapplications.emporium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.mobileapplications.emporium.camera.ImageViewActivity;
import com.mobileapplications.emporium.dropbox.DbxFolderChooser;
import com.mobileapplications.emporium.dropbox.DbxFolderContentListActivity;
import com.mobileapplications.emporium.filebrowser.FileBrowserListAdapter;
import com.mobileapplications.emporium.filebrowser.FileBrowserListItem;
import com.mobileapplications.emporium.filebrowser.FileManager;
import com.mobileapplications.emporium.filebrowser.GPSCoordinates;
import com.mobileapplications.emporium.maps.MapActivity;

public class MainActivity extends ListActivity 
    implements OnItemLongClickListener {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CHOOSE_DBX_FOLDER = 101;

    private static final String LOG_TAG = "MainActivity";

    /*************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getListView().setOnItemLongClickListener(this);
        
        updateListViewWithFile(FileManager.getOutputMediaFolder());
    }

    /*************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*************************************************************************/

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        Intent intent = null;
        
        switch(item.getItemId()) {
            case R.id.action_camera:
                startCamera();
                return true;
                
            case R.id.action_dropbox:
                intent = new Intent(this, DbxFolderContentListActivity.class);
                startActivity(intent);
                return true;
                
            case R.id.action_map:
                intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /*************************************************************************/

    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {

        Log.d(LOG_TAG,"onListItemClick: position = " + position);
        
        FileBrowserListAdapter adapter = (FileBrowserListAdapter)getListAdapter();
        FileBrowserListItem item = adapter.getItem(position);
        
        Intent intent = new Intent(this,ImageViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ImageViewActivity.TAG_IMAGEVIEW_PATH, item.getFileUri().getPath());
        bundle.putInt(ImageViewActivity.TAG_IMAGEVIEW_SOURCE_TYPE, ImageViewActivity.TAG_SOURCE_SDCARD);
        
        intent.putExtra("fileInfo", bundle);
        
        startActivity(intent);
    }

    /*************************************************************************/

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, 
            int position, long id) {
        
        Log.d(LOG_TAG,"onItemLongClick: position = " + position);
        
        // TODO remove test code

        FileBrowserListAdapter adapter = (FileBrowserListAdapter)getListAdapter();
        FileBrowserListItem item = adapter.getItem(position);

        GPSCoordinates gps = GPSCoordinates.fromImage(item.getFileUri());
        
        if (gps == null) {
            Log.d(LOG_TAG, "No GPS data :(  ->  writting some...");
            if (FileManager.writeGPSCoordinatesToImage(new GPSCoordinates(15.460392, "E", 47.058344, "N"), item.getFileUri())) {
                Log.d(LOG_TAG,"Success! :)");
            } else {
                Log.d(LOG_TAG,"Fail! :(");
            }
        } else {
            Log.d(LOG_TAG, "GPS-Data: " + gps.toString());
        }
        
        return true;
    }

    /*************************************************************************/

    @SuppressWarnings("unused")
    private void updateListViewWithUri(Uri uri) {
        if (uri == null) return;
        File file = new File(uri.getPath());
        updateListViewWithFile(file);
    }
    
    /*************************************************************************/

    private void updateListViewWithFile(File file) {
        
        if (file == null || !file.isDirectory()) return;
        
        File[] files = file.listFiles(new FilenameFilter() {
            public boolean accept(File path, String fileName) {
                return (fileName.endsWith(".jpg") || fileName.endsWith(".JPG"));
            }
        });
        
        List<FileBrowserListItem> fileList = FileBrowserListItem.newInstance(this,files);
        if (fileList == null) return;
        
        if (!file.equals(FileManager.getOutputMediaFolder())) {
            Resources res = this.getResources();
            int resId = res.getIdentifier("parent_folder_icon", "drawable", this.getPackageName());
            Drawable icon = res.getDrawable(resId);
            fileList.add(0, FileBrowserListItem.newInstance(this, res.getString(R.string.parent_folder), icon));
        }
        
        FileBrowserListAdapter listAdapter = (FileBrowserListAdapter)getListAdapter();
        if (listAdapter == null) {
            listAdapter = new FileBrowserListAdapter(this, R.layout.file_browser_listitem, fileList);
            setListAdapter(listAdapter);
        }
        else {
            listAdapter.clear();
            listAdapter.addAll(fileList);
            listAdapter.notifyDataSetChanged();
        }
    }

    /*************************************************************************/
    
    private void startCamera() {
        
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri fileUri = FileManager.getOutputMediaFileUri(FileManager.MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    
    /*************************************************************************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                
                if (resultCode == RESULT_OK) {
                    // Image captured and saved to fileUri specified in the Intent
                    updateListViewWithFile(FileManager.getOutputMediaFolder());
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
                
                break;
                
            case CHOOSE_DBX_FOLDER:
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra(DbxFolderChooser.TAG_DBX_FOLDER_CHOOSER_RESULT_PATH);
                    Log.d(LOG_TAG,result);
                }
    
            default:
                break;
        }
    }
}
