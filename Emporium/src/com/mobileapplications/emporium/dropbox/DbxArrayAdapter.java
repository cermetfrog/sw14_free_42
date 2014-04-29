package com.mobileapplications.emporium.dropbox;

import java.util.List;

import com.mobileapplications.emporium.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DbxArrayAdapter extends ArrayAdapter<DbxListItem> {

    public DbxArrayAdapter(Context context, int textViewResourceId, List<DbxListItem> items) {
        super(context, textViewResourceId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = 
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            view = inflater.inflate(R.layout.dbx_folder_content_listitem, null);
        }
        
        ImageView iv = (ImageView)view.findViewById(R.id.dbxListItemImageView);
        TextView tv = (TextView)view.findViewById(R.id.dbxListItemTextView);
        
        DbxListItem listItem = getItem(position);
        
        iv.setImageDrawable(listItem.getDrawable());
        tv.setText(listItem.getText());
        
        return view;
    }
}
