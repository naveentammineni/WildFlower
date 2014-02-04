package com.Richardson.wff.helpers;

import android.content.Context;
import android.os.AsyncTask;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class CachedJson
{
  Context context;
  String pathString;

  public CachedJson(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.pathString = paramString;
  }

  public void loadJson(onJsonLoadedListener paramonJsonLoadedListener)
  {
    AsyncTask local1 = new AsyncTask()
    {
      protected Object[] doInBackground(Object[] paramArrayOfObject)
      {
        try
        {
          Scanner localScanner = new Scanner(CachedJson.this.context.openFileInput((String)paramArrayOfObject[0] + ".json"));
          StringBuilder localStringBuilder = new StringBuilder();
          while (true)
          {
            if (!localScanner.hasNext())
            {
              Object[] arrayOfObject = new Object[2];
              arrayOfObject[0] = new JSONObject(localStringBuilder.toString());
              arrayOfObject[1] = paramArrayOfObject[1];
              return arrayOfObject;
            }
            localStringBuilder.append(localScanner.nextLine());
          }
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          return null;
        }
        catch (JSONException localJSONException)
        {
        }
        return null;
      }

      protected void onPostExecute(Object[] paramArrayOfObject)
      {
        try
        {
          if (paramArrayOfObject[1] != null)
            ((CachedJson.onJsonLoadedListener)paramArrayOfObject[1]).onJsonLoaded((JSONObject)paramArrayOfObject[0]);
          return;
        }
        catch (Exception localException)
        {
        }
      }
    };
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.pathString;
    arrayOfObject[1] = paramonJsonLoadedListener;
    local1.execute(arrayOfObject);
  }

  public void saveJson(String paramString)
  {
    try
    {
      saveJson(new JSONObject(paramString));
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  public void saveJson(JSONObject paramJSONObject)
  {
    AsyncTask local2 = new AsyncTask()
    {
      protected Boolean doInBackground(Object[] paramArrayOfObject)
      {
        try
        {
          CachedJson.this.context.openFileOutput((String)paramArrayOfObject[0] + ".json", 0).write(((JSONObject)paramArrayOfObject[1]).toString().getBytes());
          Boolean localBoolean = Boolean.valueOf(true);
          return localBoolean;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          return Boolean.valueOf(false);
        }
        catch (IOException localIOException)
        {
        }
        return Boolean.valueOf(false);
      }
    };
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.pathString;
    arrayOfObject[1] = paramJSONObject;
    local2.execute(arrayOfObject);
  }

  public static abstract interface onJsonLoadedListener
  {
    public abstract void onJsonLoaded(JSONObject paramJSONObject);
  }
}