package com.mobileapplications.emporium.dropbox;

import java.util.ArrayList;
import java.util.List;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxSyncStatus;
import com.mobileapplications.emporium.R;
import com.mobileapplications.emporium.R.menu;

import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class DbxFolderContentListActivity extends ListActivity  
implements DbxFileSystem.SyncStatusListener,
           DbxAccountManager.AccountListener {
    
    // Variables used for logging.
    private static final String LOG_DBX_TAG = "Dropbox-API";

    // Dropbox specific 'App-Key' and 'App-Secret'
    private static final String dbxAppKey = "x9c7fitdx40c8x6";
    private static final String dbxAppSecret = "iywgu4x5n1df198";
    
    // Dropbox constants
    private static final int DBX_START_LINK_CRC = 1;

    
    private DbxAccountManager dbxAccountMgr;

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
        
        getDbxAccountMgr();
    }
    
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        dbxStartLink();
        
        if (dbxAccountMgr.hasLinkedAccount()) {
            listFolder(new DbxPath("/"));
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
    
    
    private void getDbxAccountMgr() {
                
        try {
            dbxAccountMgr = DbxAccountManager.getInstance(getApplicationContext(), dbxAppKey, dbxAppSecret);
        }
        catch (DbxAccountManager.ConfigurationMismatchException e) {
            // TODO Exception handling
            Log.d(LOG_DBX_TAG, "ConfigurationMismatchException when getting DbxAccountManager.");
            return;
        }
    }
    
    private void dbxStartLink() {
        
        if (dbxAccountMgr == null) return;
        
        try {
            dbxAccountMgr.startLink(this, DBX_START_LINK_CRC);
        }
        catch (DbxAccountManager.MultipleAccountsException e) {
            // TODO Exception handling
            Log.d(LOG_DBX_TAG, "MultipleAccountsException while trying to link DbxAccountManager with account.");
            return;
        }
    }
    
    private void listFolder(DbxPath path) {
        
        if (dbxAccountMgr == null) return;
        
        DbxFileSystem dbxFileSystem;
        List<DbxFileInfo> fileInfoList;
        
        try {
            dbxFileSystem = DbxFileSystem.forAccount(dbxAccountMgr.getLinkedAccount());
            
            // Add syncStatusListener
            dbxFileSystem.addSyncStatusListener(this);
            
            fileInfoList = dbxFileSystem.listFolder(path);
        }
        catch (DbxException.Unauthorized e) {
            // TODO Exception handling
            Log.d(LOG_DBX_TAG, "Unauthorized exception while creating DbxFileSystemObject.");
            return;
        }
        catch (DbxException.NotFound e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
            return;
        }
        catch (DbxException.InvalidParameter e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
            return;
        }
        catch (DbxException e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
            return;
        }
        
        for (DbxFileInfo fileInfo : fileInfoList) {
            Log.d(LOG_DBX_TAG,"---------------------------------------------");
            Log.d(LOG_DBX_TAG, "idFolder:     " + fileInfo.isFolder);
            Log.d(LOG_DBX_TAG, "path:         " + fileInfo.path.getName());
            Log.d(LOG_DBX_TAG, "size:         " + fileInfo.size + " Byte");
            Log.d(LOG_DBX_TAG, "modifiedTime: " + fileInfo.modifiedTime);
            Log.d(LOG_DBX_TAG, "thumbExists:  " + fileInfo.thumbExists);
            Log.d(LOG_DBX_TAG, "iconName:     " + fileInfo.iconName);
            Log.d(LOG_DBX_TAG, "toString:     " + fileInfo.toString());
        }
        
        updateListViewWithDbxFileInfoList(fileInfoList);
    }
    
    
    private void updateListViewWithDbxFileInfoList(List<DbxFileInfo> fileInfoList) {
        
        if (fileInfoList == null) return;
        
        ArrayAdapter<DbxFileInfo> arrayAdapter = 
                new ArrayAdapter<DbxFileInfo>(this, R.layout.dbx_folder_content_list_textview, fileInfoList);
        
        getListView().setAdapter(arrayAdapter);
    }
    
    
    @Override
    public void onSyncStatusChange(DbxFileSystem fs) {
        
        try {
            Log.d(LOG_DBX_TAG, "Cached state has been synced: " + fs.hasSynced());
            DbxSyncStatus syncStatus = fs.getSyncStatus();
            Log.d(LOG_DBX_TAG, "isSyncActive(): " + syncStatus.isSyncActive);
        }
        catch (DbxException e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
        }
    }
    
    @Override
    public void onLinkedAccountChange(DbxAccountManager mgr, DbxAccount acct) {
        
        if (!acct.isLinked()) return;
        
        Log.d(LOG_DBX_TAG, "onLinkedAccountChange");
        
        DbxPath path;
        try {
            path = new DbxPath("/");
        }
        catch (DbxPath.InvalidPathException e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
            return;
        }
        
        listFolder(path);
    }

}
