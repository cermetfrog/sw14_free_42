package com.mobileapplications.emporium;

import com.mobileapplications.emporium.camera.CameraActivity;
import com.mobileapplications.emporium.dropbox.DbxFolderContentListActivity;
import com.mobileapplications.emporium.maps.MapActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    public void cameraButtonOnClick(View view) {
        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);
    }
    
    public void mapButtonOnClick(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
    
    public void dropboxButtonOnClick(View view) {
        Intent intent = new Intent(this, DbxFolderContentListActivity.class);
        
        startActivity(intent);
    }
}
