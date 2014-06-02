package com.mobileapplications.emporium.camera;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.mobileapplications.emporium.R;
import com.mobileapplications.emporium.filebrowser.FileManager;
import com.mobileapplications.emporium.filebrowser.PhotoGridFragment;
import com.mobileapplications.emporium.filebrowser.PhotoGridFragment.PhotoGridListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class CameraActivity extends Activity
    implements PhotoGridListener {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    
    private static String LOG_TAG = "CameraActivity";

    private Uri fileUri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        
        Log.d(LOG_TAG, "onCreate()");

        updatePhotoGridFragment(Uri.fromFile(FileManager.getOutputMediaFolder()));
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }
    
    private void updatePhotoGridFragment(Uri folderUri) {
        
        PhotoGridFragment photoGridFragment = 
                (PhotoGridFragment)getFragmentManager().findFragmentById(R.id.photoGridFragment);
        
        photoGridFragment.updateGridWithUri(folderUri);
    }
    
    public void takeAPictureOnClick(View view) {
        startCamera();
    }
    
    private void startCamera() {
        
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = FileManager.getOutputMediaFileUri(FileManager.MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        
    }
    
    
}
