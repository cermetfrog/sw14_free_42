package com.mobileapplications.emporium.maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.mobileapplications.emporium.filebrowser.GPSCoordinates;

public class GPSTags {
	
	public static List<LatLng> GPSTagList = new ArrayList<LatLng>();
	
	public static List<LatLng> retGPSTagList(File outputPath)
	{
		if(outputPath.exists())
		{
			for(final File fileEntry : outputPath.listFiles())
			{
				if(!fileEntry.isDirectory())
				{
					GPSCoordinates gps = GPSCoordinates.fromImage(fileEntry);
					
					if(gps != null)
					{
						GPSTagList.add(new LatLng(gps.getLatitude(), gps.getLongitude()));
					}
				}
			}
			
		}	
		return GPSTagList;
	}

}
