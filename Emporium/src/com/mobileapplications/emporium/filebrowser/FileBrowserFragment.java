package com.mobileapplications.emporium.filebrowser;

import android.app.ListFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dropbox.sync.android.DbxFileInfo;
import com.mobileapplications.emporium.dropbox.DbxListItem;

public class FileBrowserFragment extends ListFragment {
    
    // Variables used for logging.
    private static final String LOG_TAG = "FileBrowserFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.d(LOG_TAG,"onListItemClick: position = " + position);
        
//        ListAdapter listAdapter = getListView().getAdapter();
//        DbxListItem listItem = (DbxListItem)listAdapter.getItem(position);
//        DbxFileInfo fileInfo = listItem.getDbxFileInfo();
//        
//        if (listItem.getText().equals("Parent Folder")) {
//            updateListViewWithPath(currentPath.getParent());
//        }
//        else {
//            if (fileInfo != null) {
//                if (fileInfo.isFolder) {
//                    updateListViewWithPath(fileInfo.path);
//                }
//            }
//        }
    }

}
