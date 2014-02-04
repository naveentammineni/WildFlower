package com.Richardson.wff;

import com.Richardson.wff.helpers.LocationArrays;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TabMap extends SupportMapFragment
{
  public void onResume()
  {
    getMap().setMyLocationEnabled(true);
    getMap().setMapType(4);
    getMap().moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(32.986761999999999D, -96.707497000000004D), 16.0F, 0.0F, 0.0F)));
    for (int i = 0; ; i++)
    {
      if (i >= LocationArrays.latArray.length)
      {
        getMap().setIndoorEnabled(false);
        super.onResume();
        return;
      }
      MarkerOptions localMarkerOptions = new MarkerOptions();
      localMarkerOptions.position(new LatLng(LocationArrays.latArray[i], LocationArrays.longArray[i]));
      localMarkerOptions.title(LocationArrays.descArray[i]);
      getMap().addMarker(localMarkerOptions);
    }
  }
}