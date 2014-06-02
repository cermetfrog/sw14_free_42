package com.mobileapplications.emporium.filebrowser;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mobileapplications.emporium.R;

public class PhotoGridFragment extends Fragment 
    implements GridView.OnItemClickListener {
    
    public interface PhotoGridListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }
    
    private static String LOG_TAG = "PhotoGridFragment";
    
    GridView mGridView;
    PhotoGridListener mListener;
    
//    public static PhotoGridFragment newInstance(Uri rootFolderUri) {
//        if (rootFolderUri == null) return null;
//        PhotoGridFragment newFragment = new PhotoGridFragment();
//        newFragment.rootFolderUri = rootFolderUri;
//        return newFragment;
//    }
    
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
            mListener = (PhotoGridListener)activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement PhotoGridListener");
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(LOG_TAG, "onCreate()");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        Log.d(LOG_TAG, "onCreateView()");

        return inflater.inflate(R.layout.fragment_photo_grid, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        Log.d(LOG_TAG, "onActivityCreated()");
        
        GridView gridView = (GridView)getActivity().findViewById(R.id.photoGridView);
        gridView.setBackgroundColor(Color.BLUE);
        gridView.setOnItemClickListener(this);
    }
    
    private GridView getGridView() {
        if (mGridView == null) {
            mGridView = (GridView)getActivity().findViewById(R.id.photoGridView);
        }
        return mGridView;
    }
    
    public void updateGridWithUri(Uri folderUri) {
        
        GridView gridView = getGridView();
        
        if (gridView == null || folderUri == null) return;
        
        URI juri = null;
        
        try {
            juri = new URI(folderUri.toString());
        }
        catch (URISyntaxException ex) {
            return;
        }
        
        File currentFolder = new File(juri);
        File[] fileList = currentFolder.listFiles();
        
        List<PhotoGridItem> itemList = PhotoGridItem.newInstance(fileList);
        
        PhotoGridAdapter adapter = (PhotoGridAdapter)gridView.getAdapter();
        if (adapter == null) {
            adapter = new PhotoGridAdapter(getActivity(), R.layout.fragment_photo_grid_item, itemList);
            gridView.setAdapter(adapter);
        }
        else {
            adapter.clear();
            adapter.addAll(itemList);
            adapter.notifyDataSetChanged();
        }
        
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener == null) return;
        mListener.onItemClick(parent, view, position, id);
    }
    
}
