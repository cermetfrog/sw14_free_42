package com.mobileapplications.emporium.maps;

import android.app.Activity;
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
		private boolean canGetLocation;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
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
				setUpMap();
			}
		}
		private void setUpMap() {
			// TODO Auto-generated method stub
			googleMap.setMyLocationEnabled(true);
			
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			
			Criteria criteria = new Criteria();
			
			String provider = locationManager.getBestProvider(criteria, true);
			
			Location myLocation = locationManager.getLastKnownLocation(provider);
			
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			
			/*double latitude = myLocation.getLatitude();
			
			double longitude = myLocation.getLongitude();
			
			LatLng latlng = new LatLng(latitude, longitude);
			
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
			
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
			
			googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Mahmoud&Schuster are programming"));*/
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
	    
	    public Location getLocation() {
	        try {
	            LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

	            // getting GPS status
	            boolean isGPSEnabled = locationManager
	                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

	            // getting network status
	            boolean isNetworkEnabled = locationManager
	                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	            if (!isGPSEnabled && !isNetworkEnabled) {
	                // no network provider is enabled
	            } else {
	                this.canGetLocation = true;
	                if (isNetworkEnabled) {
	                    locationManager.requestLocationUpdates(
	                            LocationManager.NETWORK_PROVIDER,
	                            MIN_TIME_BW_UPDATES,
	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                    Log.d("Network", "Network Enabled");
	                    if (locationManager != null) {
	                        location = locationManager
	                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                        if (location != null) {
	                            latitude = location.getLatitude();
	                            longitude = location.getLongitude();
	                        }
	                    }
	                }
	                // if GPS Enabled get lat/long using GPS Services
	                if (isGPSEnabled) {
	                    if (location == null) {
	                        locationManager.requestLocationUpdates(
	                                LocationManager.GPS_PROVIDER,
	                                MIN_TIME_BW_UPDATES,
	                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                        Log.d("GPS", "GPS Enabled");
	                        if (locationManager != null) {
	                            location = locationManager
	                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                            if (location != null) {
	                                latitude = location.getLatitude();
	                                longitude = location.getLongitude();
	                            }
	                        }
	                    }
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return location;
	    }
	    

}









