package com.mobileapplications.emporium.dropbox;

import java.util.ArrayList;
import java.util.List;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.mobileapplications.emporium.R;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DbxFolderContentListActivity extends ListActivity 
    implements DbxFileSystem.PathListener {
    
    // Variables used for logging.
    private static final String LOG_TAG = "DbxFolderContentListActivity";
    
    // Dropbox constants
    private static final int DBX_START_LINK_CRC = 1;
    
    private DbxManager dbxManager;
    private DbxPath currentPath;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dbx_folder_content_list, menu);
        return true;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Uncomment to set custom layout.
        //setContentView(R.layout.activity_dbx_folder_content_list);
        
        dbxManager = DbxManager.getInstance(getApplicationContext());
        
        dbxManager.linkAccount(this, DBX_START_LINK_CRC);
    }
    
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        if (dbxManager.hasLinkedAccount()) {
            updateListViewWithPath(DbxPath.ROOT);
        }
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    
    private void updateListViewWithPath(DbxPath path) {
        Log.d(LOG_TAG, "updateListViewWithPath()");
        
        List<DbxFileInfo> fileInfoList = dbxManager.listFolder(path);        
        if (fileInfoList == null) return;
        
        List<DbxListItem> itemList = new ArrayList<DbxListItem>();

        this.currentPath = path;
        
        if (!currentPath.equals(DbxPath.ROOT)) {
            itemList.add(new DbxListItem(this,"Parent Folder",null));
        }

//        String title = "";
        
//        if (fileInfoList.size() > 0) {
//            DbxFileInfo fileInfo = fileInfoList.get(0);
//            DbxPath parentPath = fileInfo.path.getParent();
//            
//            if (parentPath != null) {
//                title = parentPath.getName();
//                if (!parentPath.isSameOrDescendantOf(DbxPath.ROOT)) {
//                    itemList.add(new DbxListItem(this,"Parent Folder",null));
//                }
//            }
//        }
        
        for (DbxFileInfo fileInfo : fileInfoList) {
            itemList.add(new DbxListItem(this,fileInfo));
        }
        
        DbxArrayAdapter arrayAdapter = 
                new DbxArrayAdapter(this, R.layout.dbx_folder_content_list_textview, itemList);
        
        getListView().setAdapter(arrayAdapter);
        
        this.setTitle(currentPath.getName());
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        Log.d(LOG_TAG,"onListItemClick: position = " + position);
        
        ListAdapter listAdapter = getListView().getAdapter();
        DbxListItem listItem = (DbxListItem)listAdapter.getItem(position);
        DbxFileInfo fileInfo = listItem.getDbxFileInfo();
        
        if (listItem.getText().equals("Parent Folder")) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "onActivityResult()");
        
        switch (requestCode) {
        
        case DBX_START_LINK_CRC:
            updateListViewWithPath(DbxPath.ROOT);
            break;

        default:
            break;
        }
    }
    
    @Override
    public void onPathChange(DbxFileSystem fs, DbxPath path, Mode mode) {
        Log.d(LOG_TAG, "onPathChange called.");
        updateListViewWithPath(path);
    }
}
