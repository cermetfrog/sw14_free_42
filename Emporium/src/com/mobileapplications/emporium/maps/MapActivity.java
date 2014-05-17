package com.mobileapplications.emporium.maps;

import com.mobileapplications.emporium.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MapActivity extends Activity
{
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_map);
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
