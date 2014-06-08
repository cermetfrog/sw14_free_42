package com.mobileapplications.emporium.camera;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.mobileapplications.emporium.R;
import com.mobileapplications.emporium.dropbox.DbxFolderChooser;
import com.mobileapplications.emporium.filebrowser.ExifInfo;
import com.mobileapplications.emporium.filebrowser.GPSCoordinates;
import com.mobileapplications.emporium.maps.MapActivity;

public class ImageViewActivity extends Activity {
    
    public static final String TAG_IMAGEVIEW_SOURCE_TYPE = "imageViewSourceType";
    public static final String TAG_IMAGEVIEW_PATH = "imageViewPath";
    
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_view_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          
    	Intent intent = null;
    	
        switch(item.getItemId()) {
            case R.id.action_info:
                showExifInfoDialog();
                return true;
                
            case R.id.action_show_on_map:
                GPSCoordinates gps = GPSCoordinates.fromImage(new File(currentImagePath));
                if (gps == null) {
                    showNoGPSDataDialog();
                    return true;
                }
                
                Bundle gpsbundle = new Bundle();
                gpsbundle.putDouble(GPSCoordinates.TAG_LONGITUDE, gps.getLongitude());
                gpsbundle.putDouble(GPSCoordinates.TAG_LATITUDE, gps.getLatitude());
                gpsbundle.putString(GPSCoordinates.TAG_LONGITUDE_REF, gps.getLongRef());
                gpsbundle.putString(GPSCoordinates.TAG_LATITUDE_REF, gps.getLatRef());
                
            	if(gpsbundle != null)
            	{
	            	intent = new Intent(this, MapActivity.class);
	            	intent.putExtra("gpscoordinates", gpsbundle);
	            	startActivity(intent);
            	}	
                return true;
                
            case R.id.action_share_dropbox:
                intent = new Intent(this,DbxFolderChooser.class);
                intent.putExtra(DbxFolderChooser.TAG_BUNDLE_KEY_IMAGE_PATH, currentImagePath);
                startActivity(intent);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateImageView(File file) {
        
        if (file == null) return;
        
        ImageView iv = (ImageView)findViewById(R.id.imageView01);
        iv.setImageURI(Uri.fromFile(file));
    }
    
    private void showExifInfoDialog() {
        
        ExifInfo exifinfo = ExifInfo.fromImage(new File(currentImagePath));
        if (exifinfo == null) return;
        
        ImageViewExifInfoDialog dialog = ImageViewExifInfoDialog.newInstance(
                R.string.meta_info, exifinfo.toString());
        dialog.show(getFragmentManager(),"showExifDialogFragment");
    }
    
    
    private void showNoGPSDataDialog() {
        
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("NO GPS Information");
  
        // Setting Dialog Message
        alertDialog.setMessage("This picture does not contain any GPS information... :(");
        alertDialog.setNeutralButton("Okay", new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    
}
