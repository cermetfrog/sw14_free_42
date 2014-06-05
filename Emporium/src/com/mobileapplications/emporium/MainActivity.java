package com.mobileapplications.emporium;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.mobileapplications.emporium.camera.CameraActivity;
import com.mobileapplications.emporium.dropbox.DbxFolderContentListActivity;
import com.mobileapplications.emporium.filebrowser.FileBrowserListAdapter;
import com.mobileapplications.emporium.filebrowser.FileBrowserListItem;
import com.mobileapplications.emporium.filebrowser.FileManager;
import com.mobileapplications.emporium.maps.MapActivity;

public class MainActivity extends ListActivity 
    implements OnItemLongClickListener {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getListView().setOnItemLongClickListener(this);
        
        updateListViewWithFile(FileManager.getOutputMediaFolder());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
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
    
    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {

        Log.d(LOG_TAG,"onListItemClick: position = " + position);
        
        
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, 
            int position, long id) {
        
        Log.d(LOG_TAG,"onItemLongClick: position = " + position);
        
        return true;
    }


    private void updateListViewWithUri(Uri uri) {
        if (uri == null) return;
        File file = new File(uri.getPath());
        updateListViewWithFile(file);
    }
    
    private void updateListViewWithFile(File file) {
        
        if (file == null || !file.isDirectory()) return;
        
        File[] files = file.listFiles(new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith(".jpg");
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


    public void cameraButtonOnClick(View view) {
        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);
    }
    
    public void mapButtonOnClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
    
    public void dropboxButtonOnClick(View view) {
        Intent intent = new Intent(this, DbxFolderContentListActivity.class);
        
        startActivity(intent);
    }
    
    private void startCamera() {
        
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri fileUri = FileManager.getOutputMediaFileUri(FileManager.MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
}
