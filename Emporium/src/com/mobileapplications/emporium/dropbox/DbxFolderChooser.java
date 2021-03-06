package com.mobileapplications.emporium.dropbox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.mobileapplications.emporium.R;

public class DbxFolderChooser extends ListActivity 
    implements DbxFileSystem.PathListener {
    
    public static final String TAG_BUNDLE_KEY_IMAGE_PATH = "tagBundleKeyImagePath";
    
    public static final String TAG_DBX_FOLDER_CHOOSER_RESULT_PATH = "dbx_folder_chooser_result_path";
    
    private static final String LOG_TAG = "DbxFolderChooser";
    
    private DbxPath currentPath;
    private String localImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbx_folder_chooser);
        
        DbxManager dbxMan = DbxManager.getInstance(getApplicationContext());
        dbxMan.linkAccount(this, DbxManager.DBX_MANAGER_LINKACCOUNT_REQUEST_CODE);
        
        Intent intent = getIntent();
        localImagePath = intent.getStringExtra(TAG_BUNDLE_KEY_IMAGE_PATH);
    }
    
    

    @Override
    protected void onStart() {
        super.onStart();
        
        DbxManager dbxManager = DbxManager.getInstance(getApplicationContext());
        if (dbxManager.hasLinkedAccount()) {
            updateListViewWithPath(DbxPath.ROOT);
        }
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        
        ListAdapter listAdapter = getListAdapter();
        DbxListItem listItem = (DbxListItem)listAdapter.getItem(position);
        DbxFileInfo fileInfo = listItem.getDbxFileInfo();
        
        if (listItem.getText().equals(getResources().getString(R.string.parent_folder))) {
            updateListViewWithPath(currentPath.getParent());
        }
        else {
            if (fileInfo != null) {
                if (fileInfo.isFolder) {
                    updateListViewWithPath(fileInfo.path);
                }
            }
        }
    }
    
    public void selectButtonOnClick(View view) {
        
        DbxManager mgr = DbxManager.getInstance(getApplicationContext());
        if (!mgr.hasLinkedAccount()) {
            // TODO Error handling
            return;
        }
        
        File localFile = new File(localImagePath);
        
        if (! mgr.uploadFileToDbxPath(localFile, new DbxPath(currentPath,localFile.getName()))) {
            Log.d(LOG_TAG,"File upload failed :(");
        } else {
            Log.d(LOG_TAG,"File upload success :D");
        }
        
        finish();
    }
    
    private void updateListViewWithPath(DbxPath path) {
        Log.d(LOG_TAG, "updateListViewWithPath()");
        
        DbxManager dbxManager = DbxManager.getInstance(getApplicationContext());
        
        List<DbxFileInfo> fileInfoList = dbxManager.listFolder(path);        
        if (fileInfoList == null) return;
        
        List<DbxListItem> itemList = new ArrayList<DbxListItem>();

        this.currentPath = path;
        
        if (!currentPath.equals(DbxPath.ROOT)) {
            Drawable icon = getResources().getDrawable(R.drawable.parent_folder_icon);
            itemList.add(new DbxListItem(this,getResources().getString(R.string.parent_folder),icon));
        }
        
        for (DbxFileInfo fileInfo : fileInfoList) {
            if (fileInfo.isFolder || fileInfo.path.getName().contains(".jpg")) {
                itemList.add(new DbxListItem(this,fileInfo));
            }
        }
        
        DbxArrayAdapter arrayAdapter = (DbxArrayAdapter)getListAdapter();
        
        if (arrayAdapter == null) {
            arrayAdapter = new DbxArrayAdapter(this, R.layout.dbx_folder_content_listitem, itemList);
            setListAdapter(arrayAdapter);
        }
        else {
            arrayAdapter.clear();
            arrayAdapter.addAll(itemList);
            arrayAdapter.notifyDataSetChanged();
        }
        
        this.setTitle(currentPath.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        switch (requestCode) {
        
        case DbxManager.DBX_MANAGER_LINKACCOUNT_REQUEST_CODE:
            if (resultCode == RESULT_OK) {
                updateListViewWithPath(DbxPath.ROOT);
            } else {
                finish();
            }
            break;

        default:
            break;
        }
    }



    @Override
    public void onPathChange(DbxFileSystem fs, DbxPath path, Mode mode) {
        updateListViewWithPath(path);
    }
    
    
    
}
