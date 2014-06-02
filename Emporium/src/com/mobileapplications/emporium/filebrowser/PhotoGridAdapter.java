package com.mobileapplications.emporium.filebrowser;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileapplications.emporium.R;

public class PhotoGridAdapter extends ArrayAdapter<PhotoGridItem> {
    
    private static String LOG_TAG = "PhotoGridAdapter";

    
    public PhotoGridAdapter(Context context, int resource, PhotoGridItem[] objects) {
        super(context,resource,objects);
    }
    
    public PhotoGridAdapter(Context context, int resource, List<PhotoGridItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        Log.d(LOG_TAG,"getView()");
        
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = 
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            view = inflater.inflate(R.layout.fragment_photo_grid_item, null);
        }
        
        ImageView iv = (ImageView)view.findViewById(R.id.photoGridItemImageView);
        TextView tv = (TextView)view.findViewById(R.id.photoGridItemTextView);
        
        PhotoGridItem listItem = getItem(position);
        
        iv.setImageURI(listItem.getFileUri());
        tv.setText(listItem.getText());
        
        return view;
    }

    
}
