package com.Richardson.wff.items;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

public class TwitterItem
{
  boolean hasPostImage = false;
  boolean imageSet = false;
  String imageURL;
  ImageView imageView;
  boolean isexpanded = false;
  String message;
  String name;
  String postImageURL;
  ImageView postImageView;
  String profileURL;
  boolean retweeted = false;
  String time;

  public TwitterItem()
  {
  }

  public TwitterItem(String paramString1, String paramString2, String paramString3)
  {
    this.name = paramString1;
    this.message = paramString2;
    this.time = paramString3;
  }

  public TwitterItem(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.name = paramString1;
    this.message = paramString2;
    this.time = paramString3;
    this.profileURL = paramString4;
  }

  public TwitterItem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.name = paramString1;
    this.message = paramString2;
    this.time = paramString3;
    this.profileURL = paramString4;
    this.imageURL = paramString5;
  }

  public int convertToDp(Context paramContext, int paramInt)
  {
    return (int)(0.5F + paramContext.getResources().getDisplayMetrics().density * paramInt);
  }

  public String getImageURL()
  {
    return this.imageURL;
  }

  public ImageView getImageView()
  {
    return this.imageView;
  }

  public String getMessage()
  {
    return this.message;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPostImageURL()
  {
    return this.postImageURL;
  }

  public ImageView getPostImageView()
  {
    return this.postImageView;
  }

  public String getProfileURL()
  {
    return this.profileURL;
  }

  public String getTime()
  {
    return this.time;
  }

  public View getView(Context paramContext)
  {
    View localView = null;
    if (paramContext != null)
    {
      localView = ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903134, null);
      ((TextView)localView.findViewById(2131165402)).setText(this.name);
      ((TextView)localView.findViewById(2131165406)).setText(this.message);
      if (this.imageView != null)
    	  this.imageView = ((ImageView)localView.findViewById(2131165405));
      if (this.postImageView != null)
    	  this.postImageView = ((ImageView)localView.findViewById(2131165404));
    }
    while (true)
    {
      if (!this.hasPostImage)
      ((ImageView)localView.findViewById(2131165404)).setLayoutParams(new LinearLayout.LayoutParams(-1, convertToDp(paramContext, 50)));
      ((ImageView)localView.findViewById(2131165404)).setOnClickListener(new View.OnClickListener(){
        public void onClick(View paramView)
        {
          ImageView localImageView = (ImageView)paramView;
          double d1 = localImageView.getDrawable().getMinimumWidth();
          double d2 = localImageView.getDrawable().getMinimumHeight();
          double d3 = localImageView.getMeasuredWidth();
          if (TwitterItem.this.isexpanded)
          {
            TwitterItem.animator localanimator1 = new TwitterItem.animator(TwitterItem.this);
            Integer[] arrayOfInteger1 = new Integer[2];
            arrayOfInteger1[0] = Integer.valueOf(TwitterItem.this.convertToDp(paramView.getContext(), (int)(d2 * d3 / d1)));
            arrayOfInteger1[1] = Integer.valueOf(TwitterItem.this.convertToDp(paramView.getContext(), 50));
            localanimator1.execute(arrayOfInteger1);
            TwitterItem.this.isexpanded = false;
            return;
          }
          TwitterItem.animator localanimator2 = new TwitterItem.animator(TwitterItem.this);
          Integer[] arrayOfInteger2 = new Integer[2];
          arrayOfInteger2[0] = Integer.valueOf(TwitterItem.this.convertToDp(paramView.getContext(), 50));
          arrayOfInteger2[1] = Integer.valueOf(TwitterItem.this.convertToDp(paramView.getContext(), (int)(d2 * d3 / d1)));
          localanimator2.execute(arrayOfInteger2);
          TwitterItem.this.isexpanded = true;
        }
      });
      ((ImageView)localView.findViewById(2131165405)).setImageDrawable(this.imageView.getDrawable());
      ((ImageView)localView.findViewById(2131165404)).setImageDrawable(this.postImageView.getDrawable());
      ((ImageView)localView.findViewById(2131165404)).setVisibility(8);
      return localView;
    }
    
  }

  public boolean imageSet()
  {
    return this.imageSet;
  }

  public boolean isHasPostImage()
  {
    return this.hasPostImage;
  }

  public boolean isRetweeted()
  {
    return this.retweeted;
  }

  public void setCreated_At(String paramString)
  {
    this.time = paramString;
  }

  public void setHasPostImage(boolean paramBoolean)
  {
    this.hasPostImage = paramBoolean;
  }

  public void setImage(Bitmap paramBitmap)
  {
    this.imageSet = true;
    if (this.imageView != null)
      this.imageView.setImageBitmap(paramBitmap);
  }

  public void setPostImageURL(String paramString)
  {
    this.postImageURL = paramString;
  }

  public void setPostImageView(ImageView paramImageView)
  {
    this.postImageView = paramImageView;
  }

  public void setProfile_Image_Url(String paramString)
  {
    if (!this.retweeted)
      this.profileURL = paramString;
  }

  public void setRetweeted_Status(JSONObject paramJSONObject)
  {
    try
    {
      this.message = paramJSONObject.getString("text");
      JSONObject localJSONObject = paramJSONObject.getJSONObject("user");
      this.name = ("@" + localJSONObject.getString("screen_name"));
      this.imageURL = localJSONObject.getString("profile_image_url");
      this.retweeted = true;
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void setText(String paramString)
  {
    if (!this.retweeted)
      this.message = paramString;
  }

  public void setUser(JSONObject paramJSONObject)
  {
    if (!this.retweeted);
    try
    {
      this.name = ("@" + paramJSONObject.getString("screen_name"));
      this.imageURL = paramJSONObject.getString("profile_image_url");
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  public void setentities(JSONObject paramJSONObject)
  {
    try
    {
      if (paramJSONObject.has("media"))
      {
        JSONObject localJSONObject = paramJSONObject.getJSONArray("media").getJSONObject(0);
        if (localJSONObject.getString("type").equals("photo"))
        {
          this.hasPostImage = true;
          this.postImageURL = localJSONObject.getString("media_url");
        }
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  class animator extends AsyncTask<Integer, Integer, Boolean>
  {
	  TwitterItem twitterItem;
    animator(TwitterItem twitterItem)
    {
    	this.twitterItem = twitterItem;
    }

    protected Boolean doInBackground(Integer[] paramArrayOfInteger)
    {
      int i = paramArrayOfInteger[0].intValue();
      while (true)
      {
        if (i == paramArrayOfInteger[1].intValue())
          return Boolean.valueOf(true);
        i += (paramArrayOfInteger[1].intValue() - i) / Math.abs(paramArrayOfInteger[1].intValue() - i);
        Integer[] arrayOfInteger = new Integer[1];
        arrayOfInteger[0] = Integer.valueOf(i);
        publishProgress(arrayOfInteger);
      }
    }

    protected void onProgressUpdate(Integer[] paramArrayOfInteger)
    {
      if (TwitterItem.this.postImageView != null)
        TwitterItem.this.postImageView.setLayoutParams(new LinearLayout.LayoutParams(-1, paramArrayOfInteger[0].intValue()));
      super.onProgressUpdate(paramArrayOfInteger);
    }
  }
}