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

public class FileBrowserListAdapter extends ArrayAdapter<FileBrowserListItem> {
    
    private static String LOG_TAG = "FileBrowserListAdapter";

    
    public FileBrowserListAdapter(Context context, int resource, FileBrowserListItem[] objects) {
        super(context,resource,objects);
    }
    
    public FileBrowserListAdapter(Context context, int resource, List<FileBrowserListItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        Log.d(LOG_TAG,"getView()");
        
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = 
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            view = inflater.inflate(R.layout.file_browser_listitem, null);
        }
        
        ImageView iv = (ImageView)view.findViewById(R.id.fileBrowserListItemImageView);
        TextView tv = (TextView)view.findViewById(R.id.fileBrowserListItemTextView);
        
        FileBrowserListItem listItem = getItem(position);
        
        iv.setImageDrawable(listItem.getIcon());
        tv.setText(listItem.getText());
        
        return view;
    }

    
}
