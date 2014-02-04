package com.Richardson.wff.helpers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.Richardson.wff.items.EventListItem;

public class Downloader extends AsyncTask<String, String, Boolean>
{
  Context context;
  onDownloadDoneListener downloadDoneListener;
  ArrayList<EventListItem> eventListItems = new ArrayList();
  String jsonString;

  public Downloader(Context paramContext, onDownloadDoneListener paramonDownloadDoneListener)
  {
    this.context = paramContext;
    this.downloadDoneListener = paramonDownloadDoneListener;
  }

  private boolean isNetworkAvailable()
  {
	  boolean bool=false;
	  Context localContext = this.context;
	  if (localContext != null)
	  {	
		  NetworkInfo localNetworkInfo = ((ConnectivityManager)this.context.getSystemService("connectivity")).getActiveNetworkInfo();
		   if (localNetworkInfo != null)
		  {
			  bool = localNetworkInfo.isConnected();
		  }
	  }
    return bool;
  }

  protected Boolean doInBackground(String[] paramArrayOfString)
  {
	 
	  
	  if (!isNetworkAvailable())
	      return null;
	    String str = paramArrayOfString[0];
	    try
	    {
	      if (!str.contains("http://"))
	        str = "http://" + str;
	      URLConnection localURLConnection = new URL(str).openConnection();
	      localURLConnection.connect();
	      InputStream localInputStream = localURLConnection.getInputStream();
	      BitmapFactory.Options localOptions = new BitmapFactory.Options();
	      localOptions.inPurgeable = true;
	      localOptions.inInputShareable = true;
	      Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream, null, localOptions);
	      return true;
	    }
	    catch (Exception localException)
	    {
	      Log.d("Image Download", "Unable to download " + str);
	    }
	    return false;
  }

  protected void onPostExecute(Boolean paramBoolean)
  {
	  super.onPostExecute(paramBoolean);
	    if (this.downloadDoneListener != null)
	    {
	      if (paramBoolean.booleanValue() && this.eventListItems != null)
	        this.downloadDoneListener.onDownloadDone(this.eventListItems, this.jsonString);
	    }
	    else 
	    	this.downloadDoneListener.onDownloadFailed();
  }

  public static abstract interface onDownloadDoneListener
  {
    public abstract void onDownloadDone(ArrayList<EventListItem> paramArrayList, String paramString);

    public abstract void onDownloadFailed();
  }
}