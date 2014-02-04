package com.Richardson.wff;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import java.util.GregorianCalendar;

public class LocationService extends Service
{
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  public void resendIntent(int paramInt)
  {
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar(2013, 4, 20);
    GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
    LocationManager localLocationManager = (LocationManager)getSystemService("location");
    Intent localIntent = new Intent(this, LocationService.class);
    localIntent.putExtra("loc", paramInt);
    PendingIntent localPendingIntent = PendingIntent.getService(this, paramInt, localIntent, 268435456);
    localLocationManager.addProximityAlert(com.Richardson.wff.helpers.LocationArrays.achieveLat[paramInt], com.Richardson.wff.helpers.LocationArrays.achieveLong[paramInt], (float)com.Richardson.wff.helpers.LocationArrays.achieveRadius[paramInt], localGregorianCalendar1.getTimeInMillis() - localGregorianCalendar2.getTimeInMillis(), localPendingIntent);
  }
}