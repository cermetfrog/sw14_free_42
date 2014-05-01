package com.mobileapplications.emporium.dropbox;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxSyncStatus;

public class DbxManager implements DbxFileSystem.SyncStatusListener, 
                                   DbxAccountManager.AccountListener {

    // Static Constants
    private static final String LOG_DBX_TAG = "DbxManager";
    
    // Dropbox specific 'App-Key' and 'App-Secret'
    private static final String dbxAppKey = "x9c7fitdx40c8x6";
    private static final String dbxAppSecret = "iywgu4x5n1df198";
    
    private static DbxManager dbxManager;
    
    // ========================================================================
    // Private Fields
    
    private DbxAccountManager dbxAccountMgr;
    private DbxFileSystem dbxFileSystem;
    
    private Context context;
    
    // ========================================================================
    // Public Static Methods
    
    public static DbxManager getInstance(Context context) {
        if (dbxManager == null) {
            dbxManager = new DbxManager(context);
        }
        return dbxManager;
    }
    
    // ========================================================================
    // Private Constructor
    
    private DbxManager(Context context) {
        this.context = context;
        try {
            dbxAccountMgr = DbxAccountManager.getInstance(context, dbxAppKey, dbxAppSecret);
            dbxAccountMgr.addListener(this);
        }
        catch (DbxAccountManager.ConfigurationMismatchException e) {
            Log.d(LOG_DBX_TAG, "ConfigurationMismatchException when getting DbxAccountManager.");
        }
    }
    
    // ========================================================================
    // Public Methods
    
    public void linkAccount(Activity activity, int callbackRequestCode) {
        if (dbxAccountMgr == null) return;
        
        try {
            dbxAccountMgr.startLink(activity, callbackRequestCode);
        }
        catch (DbxAccountManager.MultipleAccountsException e) {
            Log.d(LOG_DBX_TAG, "MultipleAccountsException while trying to link DbxAccountManager with account.");
            getDbxFileSystemForAccount(dbxAccountMgr.getLinkedAccount());
        }
    }
    
    public boolean hasLinkedAccount() {
        if (dbxAccountMgr == null) {
            return false;
        }
        else {
            return dbxAccountMgr.hasLinkedAccount();
        }
    }
    
    public void unlinkAccount() {
        if (dbxAccountMgr == null) return;
        
        dbxFileSystem = null;
        dbxAccountMgr.unlink();
    }
    
    public List<DbxFileInfo> listFolder(DbxPath path) {
        
        if (dbxFileSystem == null) return null;
        
        List<DbxFileInfo> fileInfoList = null;
        try {
            fileInfoList = dbxFileSystem.listFolder(path);
        }
        catch (DbxException.NotFound e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
        }
        catch (DbxException.InvalidParameter e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
        }
        catch (DbxException e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
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
        
        return fileInfoList;
    }
    
    public void addPathListener(DbxFileSystem.PathListener l, DbxPath path,
            DbxFileSystem.PathListener.Mode mode) {
        
        if (dbxFileSystem == null) return;
        
        dbxFileSystem.addPathListener(l, path, mode);
    }
    
    // ========================================================================
    // Private Methods
    
    private void getDbxFileSystemForAccount(DbxAccount acct) {
        
        if (acct == null) {
            return;
        }
        
        try {
            dbxFileSystem = DbxFileSystem.forAccount(acct);
            dbxFileSystem.addSyncStatusListener(this);
        }
        catch (DbxException.Unauthorized e) {
            Log.d(LOG_DBX_TAG, e.getMessage());
        }
    }
    
    // ========================================================================
    // Event Listener
    
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
        Log.d(LOG_DBX_TAG, "onLinkedAccountChange.");
        
        if (acct.isLinked()) {
            getDbxFileSystemForAccount(acct);
        }
        else {
            dbxFileSystem = null;
        }
    }
}
