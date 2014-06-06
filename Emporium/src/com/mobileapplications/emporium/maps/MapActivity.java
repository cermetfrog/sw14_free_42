package com.mobileapplications.emporium.maps;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobileapplications.emporium.R;

public class MapActivity extends Activity
{
		private  GoogleMap googleMap;
		private GPSTracker track;
		private LatLng latlng;
		private LatLng coord;
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        coord = null;
	       // = new LatLng();
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
				if(!track.canGetLocation())
					track.showSettingsAlert();
				else
					setUpMap();
			}
		}
		public void setUpMap() {
			// TODO Auto-generated method stub
			googleMap.setMyLocationEnabled(true);
			
			latlng = new LatLng(track.getLatitude(), track.getLongitude());
						
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
			
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
			track.stopUsingGPS();
			//googleMap.addMarker(new MarkerOptions().position(latlng).title("Mahmoud&Schuster are programming"));
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
	    
}









