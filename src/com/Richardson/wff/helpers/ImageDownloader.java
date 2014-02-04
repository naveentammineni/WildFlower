package com.Richardson.wff.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import com.Richardson.wff.items.EventListItem;
import com.Richardson.wff.items.FoodListItem;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader extends AsyncTask<String, Integer, Bitmap>
{
  Context context;
  onImageDownloadDoneListener listener;

  public ImageDownloader(Context paramContext, onImageDownloadDoneListener paramonImageDownloadDoneListener)
  {
    this.context = paramContext;
    this.listener = paramonImageDownloadDoneListener;
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

  protected Bitmap doInBackground(String[] paramArrayOfString)
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
      return localBitmap;
    }
    catch (Exception localException)
    {
      Log.d("Image Download", "Unable to download " + str);
    }
    return null;
  }

  protected void onPostExecute(Bitmap paramBitmap)
  {
    super.onPostExecute(paramBitmap);
    if (this.listener != null)
    {
      if (paramBitmap == null)
        this.listener.onDownloadFailed();
    }
    else
      return;
    this.listener.onImageDownloadDone(paramBitmap);
  }

  protected void onPreExecute()
  {
    super.onPreExecute();
  }

  public static class EventimageHolder
  {
    public String URLString;
    public EventListItem item;

    public EventimageHolder(String paramString, EventListItem paramEventListItem)
    {
      this.URLString = paramString;
      this.item = paramEventListItem;
    }
  }

  public static class FoodImageHolder
  {
    public String URLString;
    public FoodListItem item;

    public FoodImageHolder(String paramString, FoodListItem paramFoodListItem)
    {
      this.URLString = paramString;
      this.item = paramFoodListItem;
    }
  }

  public static abstract interface onImageDownloadDoneListener
  {
    public abstract void onDownloadFailed();

    public abstract void onImageDownloadDone(Bitmap paramBitmap);
  }
}