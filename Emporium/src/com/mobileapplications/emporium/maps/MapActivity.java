package com.mobileapplications.emporium.maps;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobileapplications.emporium.R;
import com.mobileapplications.emporium.filebrowser.GPSCoordinates;

public class MapActivity extends Activity
{
		private GoogleMap googleMap;
		private GPSTracker track;
		private LatLng latlng;
		private File outputPath;
		
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        track = new GPSTracker(this);
	        setContentView(R.layout.activity_map);
	        setUpMapIfNeeded();
	    }
	    
	    private void setUpMapIfNeeded() {
			// TODO Auto-generated method stub
			if(googleMap == null)
			{
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			}
			
			if(googleMap != null)
			{
				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0,0),0));
				if(!track.canGetLocation())
					track.showSettingsAlert();
				else
					setUpMap();
			}
		}
	    
		public void setUpMap() {
			// TODO Auto-generated method stub
        	Bundle gpsbundle = new Bundle();
        	gpsbundle = this.getIntent().getBundleExtra("gpscoordinates");
	        outputPath = (File) this.getIntent().getSerializableExtra("output_path");
			googleMap.setMyLocationEnabled(true);
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			
        	if(gpsbundle != null)
        	{
            	latlng = new LatLng(gpsbundle.getDouble(GPSCoordinates.TAG_LATITUDE), gpsbundle.getDouble(GPSCoordinates.TAG_LONGITUDE));
            	googleMap.addMarker(new MarkerOptions().position(latlng).title("Picture"));
        	}else if(outputPath != null)
        	{
        		List<LatLng> GPSTagList = GPSTags.retGPSTagList(outputPath);
        		for(LatLng GPSTag : GPSTagList)
        		{
        			googleMap.addMarker(new MarkerOptions().position(GPSTag).title("Picture"));
        		}
        		latlng = new LatLng(track.getLatitude(), track.getLongitude());
        	}
       
        	if(latlng.latitude != 0 && latlng.longitude != 0)
        		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 9));
        	else
        		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 0));
			
			track.stopUsingGPS();
		}

	    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	    {
	        if(requestCode == 0)
	        {     
	        	track.getLocation();
	        	setUpMap();
	        }
	    }
	    
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.map_activity, menu);
	        return true;
	    }
	    @Override
	    protected void onRestart() {
	        // TODO Auto-generated method stub
	        super.onRestart();
	    }
	    @Override
	    protected void onResume() {
	        // TODO Auto-generated method stub
	        super.onResume();
	    }
	    
	    @Override
	    protected void onPause() {
	        // TODO Auto-generated method stub
	        super.onPause();
	    }
	    
	    @Override
	    protected void onStop() {
	        // TODO Auto-generated method stub
	        super.onStop();
	    }

	    @Override
	    protected void onDestroy() {
	        // TODO Auto-generated method stub
	        super.onDestroy();
	    }

		public GoogleMap getGoogleMap() {
			return googleMap;
		}

		public GPSTracker getTrack() {
			return track;
		}
		
		public float getZoom()
		{
			return googleMap.getCameraPosition().zoom;
		}
	    
}









