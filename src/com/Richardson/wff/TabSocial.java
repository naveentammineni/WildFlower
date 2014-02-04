package com.Richardson.wff;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.Richardson.wff.helpers.ImageDownloader;
import com.Richardson.wff.helpers.ImageDownloader.onImageDownloadDoneListener;
import com.Richardson.wff.helpers.TwitterDownloader;
import com.Richardson.wff.helpers.TwitterDownloader.TwitterDownloadDoneListener;
import com.Richardson.wff.items.TwitterItem;
import java.util.ArrayList;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ProgressBar;

public class TabSocial extends Fragment
{
  CheckBox achieveFoodCourtBox;
  ImageButton achieveFoodCourtButton;
  CheckBox achieveMainStageBox;
  ImageButton achieveMainStageButton;
  CheckBox achieveMetroStageBox;
  ImageButton achieveMetroStageButton;
  ArrayList<imageHolder> imageQueueArrayList;
  LinearLayout socialAchieveHolder;
  LinearLayout twitterHolderLayout;
  ArrayList<TwitterItem> twitterItems;
  LinearLayout twitterLoadingLayout;
  ProgressBar twitterProgressBar;

  private boolean isNetworkAvailable()
  {
	  boolean bool=false;
	  FragmentActivity localFragmentActivity = getActivity();
	  if (localFragmentActivity != null)
	  {
		  NetworkInfo localNetworkInfo = ((ConnectivityManager)getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
		   if (localNetworkInfo != null)
		  {
			  bool = localNetworkInfo.isConnected();
		  }
	  }
    return bool;
  }

  public void fillimageDownloadQueue(ArrayList<TwitterItem> paramArrayList)
  {
    this.imageQueueArrayList.clear();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
        return;
      if (((TwitterItem)paramArrayList.get(i)).imageSet())
        continue;
      this.imageQueueArrayList.add(new imageHolder(((TwitterItem)paramArrayList.get(i)).getImageURL(), ((TwitterItem)paramArrayList.get(i)).getImageView()));
      if (!((TwitterItem)paramArrayList.get(i)).isHasPostImage())
        continue;
      this.imageQueueArrayList.add(new imageHolder(((TwitterItem)paramArrayList.get(i)).getPostImageURL(), ((TwitterItem)paramArrayList.get(i)).getPostImageView()));
    }
  }

  public void loadLayoutElements(View paramView)
  {
    org.holoeverywhere.preference.SharedPreferences localSharedPreferences = getSharedPreferences("prefs", 0);
    ((CheckBox)paramView.findViewById(2131165382)).setChecked(localSharedPreferences.getString("achieve", "").contains("-0-"));
    ((CheckBox)paramView.findViewById(2131165384)).setChecked(localSharedPreferences.getString("achieve", "").contains("-1-"));
    ((CheckBox)paramView.findViewById(2131165386)).setChecked(localSharedPreferences.getString("achieve", "").contains("-2-"));
    ((CheckBox)paramView.findViewById(2131165388)).setChecked(localSharedPreferences.getString("achieve", "").contains("-3-"));
    ((CheckBox)paramView.findViewById(2131165390)).setChecked(localSharedPreferences.getString("achieve", "").contains("-4-"));
    ((ImageButton)paramView.findViewById(2131165383)).setEnabled(localSharedPreferences.getString("achieve", "").contains("-0-"));
    ((ImageButton)paramView.findViewById(2131165385)).setEnabled(localSharedPreferences.getString("achieve", "").contains("-1-"));
    ((ImageButton)paramView.findViewById(2131165387)).setEnabled(localSharedPreferences.getString("achieve", "").contains("-2-"));
    ((ImageButton)paramView.findViewById(2131165389)).setEnabled(localSharedPreferences.getString("achieve", "").contains("-3-"));
    ((ImageButton)paramView.findViewById(2131165391)).setEnabled(localSharedPreferences.getString("achieve", "").contains("-4-"));
    ((ImageButton)paramView.findViewById(2131165383)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(TabSocial.this.getActivity(), TabCamera.class);
        localIntent.putExtra("frame", 0);
        TabSocial.this.startActivity(localIntent);
      }
    });
    ((ImageButton)paramView.findViewById(2131165385)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(TabSocial.this.getActivity(), TabCamera.class);
        localIntent.putExtra("frame", 1);
        TabSocial.this.startActivity(localIntent);
      }
    });
    ((ImageButton)paramView.findViewById(2131165387)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(TabSocial.this.getActivity(), TabCamera.class);
        localIntent.putExtra("frame", 2);
        TabSocial.this.startActivity(localIntent);
      }
    });
    ((ImageButton)paramView.findViewById(2131165389)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(TabSocial.this.getActivity(), TabCamera.class);
        localIntent.putExtra("frame", 3);
        TabSocial.this.startActivity(localIntent);
      }
    });
    ((ImageButton)paramView.findViewById(2131165391)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(TabSocial.this.getActivity(), TabCamera.class);
        localIntent.putExtra("frame", 4);
        TabSocial.this.startActivity(localIntent);
      }
    });
    this.twitterHolderLayout = ((LinearLayout)paramView.findViewById(2131165395));
    this.twitterProgressBar = ((ProgressBar)paramView.findViewById(2131165394));
    this.twitterLoadingLayout = ((LinearLayout)paramView.findViewById(2131165392));
    if (!isNetworkAvailable())
    {
      this.twitterProgressBar.setVisibility(4);
      ((TextView)paramView.findViewById(2131165393)).setText("Unable to Connect to Twitter!");
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903130, paramViewGroup, false);
    loadLayoutElements(localView);
    this.imageQueueArrayList = new ArrayList();
    return localView;
  }

  public void onPause()
  {
    this.twitterItems = null;
    super.onPause();
  }

  public void onResume()
  {
    View localView = getView();
    if (localView != null)
      loadLayoutElements(localView);
    new TwitterDownloader(new TwitterDownloader.TwitterDownloadDoneListener()
    {
      public void onTwitterDownloadDone(ArrayList<TwitterItem> paramArrayList)
      {
        try
        {
          TabSocial.this.twitterItems = paramArrayList;
          if (TabSocial.this.twitterHolderLayout.getChildCount() == 0);
          for (int i = 0; ; i++)
          {
            if (i >= paramArrayList.size())
            {
              TabSocial.this.twitterLoadingLayout.setVisibility(8);
              TabSocial.this.fillimageDownloadQueue(paramArrayList);
              TabSocial.this.startImageDownload();
              return;
            }
            TabSocial.this.twitterHolderLayout.addView(((TwitterItem)paramArrayList.get(i)).getView(TabSocial.this.getActivity()));
          }
        }
        catch (NullPointerException localNullPointerException)
        {
        }
      }
    }).execute(new String[] { "http://jtehlert.com/twitter-oauth.php" });
    super.onResume();
  }

  public void startImageDownload()
  {
    if (this.imageQueueArrayList.size() > 0)
    {
      ImageDownloader localImageDownloader = new ImageDownloader(getActivity(), new ImageDownloader.onImageDownloadDoneListener()
      {
        public void onDownloadFailed()
        {
          if (TabSocial.this.imageQueueArrayList.size() > 0)
            TabSocial.this.imageQueueArrayList.remove(0);
          TabSocial.this.startImageDownload();
        }

        public void onImageDownloadDone(Bitmap paramBitmap)
        {
          if (TabSocial.this.imageQueueArrayList.size() > 0)
          {
            if (((TabSocial.imageHolder)TabSocial.this.imageQueueArrayList.get(0)).view != null)
              ((TabSocial.imageHolder)TabSocial.this.imageQueueArrayList.get(0)).view.setImageBitmap(paramBitmap);
            TabSocial.this.imageQueueArrayList.remove(0);
          }
          TabSocial.this.startImageDownload();
        }
      });
      String[] arrayOfString = new String[1];
      arrayOfString[0] = ((imageHolder)this.imageQueueArrayList.get(0)).urlString;
      localImageDownloader.execute(arrayOfString);
    }
  }

  class imageHolder
  {
    String urlString;
    ImageView view;

    public imageHolder(String paramImageView, ImageView arg3)
    {
      this.urlString = paramImageView;
      this.view = arg3;
    }
  }
}