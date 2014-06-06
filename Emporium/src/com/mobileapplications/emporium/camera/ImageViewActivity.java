package com.mobileapplications.emporium.camera;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.mobileapplications.emporium.R;

public class ImageViewActivity extends Activity {
    
    public static final String TAG_IMAGEVIEW_SOURCE_TYPE = "imageViewSourceType";
    public static final String TAG_IMAGEVIEW_PATH = "imageViewSourceType";
    
    public static final int TAG_SOURCE_SDCARD = 1000;
    public static final int TAG_SOURCE_DROPBOX = 1001;
    
    private String currentImagePath;
    private int sourceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("fileInfo");
        
        currentImagePath = bundle.getString(TAG_IMAGEVIEW_PATH);
        sourceType = bundle.getInt(TAG_IMAGEVIEW_SOURCE_TYPE);
        
        updateImageView(new File(currentImagePath));
    }
    
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }



    private void updateImageView(File file) {
        
        if (file == null) return;
        
        ImageView iv = (ImageView)findViewById(R.id.imageView01);
        iv.setImageURI(Uri.fromFile(file));
    }
}
