package com.Richardson.wff.helpers;

import android.os.AsyncTask;
import com.Richardson.wff.items.TwitterItem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class TwitterDownloader extends AsyncTask<String, String, String>
{
  TwitterDownloadDoneListener twitterDownloadDoneListener;
  ArrayList<TwitterItem> twitterItems = new ArrayList();

  public TwitterDownloader(TwitterDownloadDoneListener paramTwitterDownloadDoneListener)
  {
    this.twitterDownloadDoneListener = paramTwitterDownloadDoneListener;
  }

  protected String doInBackground(String[] paramArrayOfString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfString.length;
    int j = 0;
    while (true)
    {
      if (j >= i)
        return localStringBuilder.toString();
      String str1 = paramArrayOfString[j];
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      try
      {
        HttpResponse localHttpResponse = localDefaultHttpClient.execute(new HttpGet(str1));
        if (localHttpResponse.getStatusLine().getStatusCode() == 200)
        {
          BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localHttpResponse.getEntity().getContent()));
          while (true)
          {
            String str2 = localBufferedReader.readLine();
            if (str2 == null)
              break;
            localStringBuilder.append(str2);
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        j++;
      }
    }
  }

  protected void onPostExecute(String paramString)
  {
    try
    {
      JsonParser localJsonParser = new JsonParser(new JSONArray(paramString), TwitterItem.class);
      localJsonParser.assignSetters();
      localJsonParser.assignSetter(TwitterItem.class.getMethod("setRetweeted_Status", new Class[] { JSONObject.class }), "retweeted_status");
      this.twitterItems = localJsonParser.parseArray();
      this.twitterDownloadDoneListener.onTwitterDownloadDone(this.twitterItems);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static abstract interface TwitterDownloadDoneListener
  {
    public abstract void onTwitterDownloadDone(ArrayList<TwitterItem> paramArrayList);
  }
}