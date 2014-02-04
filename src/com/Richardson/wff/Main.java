package com.Richardson.wff;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import com.Richardson.wff.helpers.CachedJson;
import com.Richardson.wff.helpers.CachedJson.onJsonLoadedListener;
import com.Richardson.wff.helpers.Downloader;
import com.Richardson.wff.helpers.Downloader.onDownloadDoneListener;
import com.Richardson.wff.helpers.FoodDownloader;
import com.Richardson.wff.helpers.FoodDownloader.onfoodDownloadDoneListener;
import com.Richardson.wff.helpers.JsonParser;
import com.Richardson.wff.helpers.LocationArrays;
import com.Richardson.wff.items.EventBaseAdapter;
import com.Richardson.wff.items.EventListItem;
import com.Richardson.wff.items.FoodBaseAdapter;
import com.Richardson.wff.items.FoodListItem;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.SharedPreferences;
import org.json.JSONObject;

public class Main extends Activity
{
  TabEvents eventsFragment;
  ActionBar.Tab eventsTab;
  TabFood foodFragment;
  ActionBar.Tab foodTab;
  TabInfo infoFragment;
  ActionBar.Tab infoTab;
  TabMain mainFragment;
  ActionBar.Tab mainTab;
  ActionBar.Tab socialTab;

  public int convertToDp(int paramInt)
  {
    return (int)(0.5F + getResources().getDisplayMetrics().density * paramInt);
  }

  public void loadCachedJson()
  {
    new CachedJson(this, "events").loadJson(new CachedJson.onJsonLoadedListener()
    {
      public void onJsonLoaded(JSONObject paramJSONObject)
      {
        try
        {
          JsonParser localJsonParser = new JsonParser(paramJSONObject.getJSONArray("events"), EventListItem.class);
          localJsonParser.assignSetters();
          if (!Main.this.eventsFragment.downloaded)
            Main.this.eventsFragment.update(localJsonParser.parseArray());
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
    new CachedJson(this, "food").loadJson(new CachedJson.onJsonLoadedListener()
    {
      public void onJsonLoaded(JSONObject paramJSONObject)
      {
        try
        {
          JsonParser localJsonParser = new JsonParser(paramJSONObject.getJSONArray("events"), FoodListItem.class);
          localJsonParser.assignSetters();
          if (!Main.this.foodFragment.downloaded)
            Main.this.foodFragment.update(localJsonParser.parseArray());
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }

  public void loadLayoutElements(Bundle paramBundle)
  {
    ActionBar localActionBar = getSupportActionBar();
    localActionBar.setNavigationMode(2);
    localActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FE8800")));
    localActionBar.setTitle("Wildflower Festival");
    loadTabs(paramBundle);
    loadCachedJson();
    startDownload();
  }

  public void loadTabs(Bundle paramBundle)
  {
    ActionBar localActionBar = getSupportActionBar();
    this.mainFragment = new TabMain();
    this.mainTab = localActionBar.newTab();
    this.mainTab.setText("Main");
    this.mainTab.setTabListener(new SimpleTabListener(this.mainFragment, 2131165285));
    localActionBar.addTab(this.mainTab);
    this.eventsFragment = new TabEvents();
    this.eventsTab = localActionBar.newTab();
    this.eventsTab.setText("Events");
    this.eventsTab.setTabListener(new SimpleTabListener(this.eventsFragment, 2131165285));
    localActionBar.addTab(this.eventsTab);
    this.foodFragment = new TabFood();
    this.foodTab = localActionBar.newTab();
    this.foodTab.setText("Food");
    this.foodTab.setTabListener(new SimpleTabListener(this.foodFragment, 2131165285));
    localActionBar.addTab(this.foodTab);
    this.socialTab = localActionBar.newTab();
    this.socialTab.setTabListener(new SimpleTabListener(new TabSocial(), 2131165285));
    this.socialTab.setText("Social");
    this.infoFragment = new TabInfo();
    this.infoTab = localActionBar.newTab();
    this.infoTab.setTabListener(new SimpleTabListener(this.infoFragment, 2131165285));
    this.infoTab.setText("Info");
    localActionBar.addTab(this.infoTab);
    localActionBar.selectTab(this.mainTab);
    if ((getIntent().getExtras() != null) && (getIntent().getExtras().containsKey("tab")))
      localActionBar.setSelectedNavigationItem(getIntent().getExtras().getInt("tab"));
    if ((paramBundle != null) && (paramBundle.containsKey("tab")))
      localActionBar.setSelectedNavigationItem(paramBundle.getInt("tab"));
  }

  public void onBackPressed()
  {
    if (getSupportFragmentManager().getBackStackEntryCount() > 0)
    {
      getSupportFragmentManager().popBackStack();
      if (getSupportFragmentManager().getBackStackEntryCount() == 1)
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
      return;
    }
    super.onBackPressed();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903063);
    EasyTracker.getInstance(getApplicationContext()).activityStart(this);
    loadLayoutElements(paramBundle);
  }

  protected void onDestroy()
  {
    EasyTracker.getInstance(getApplicationContext()).activityStop(this);
    super.onDestroy();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if ((paramMenuItem.getItemId() == 16908332) && (getSupportFragmentManager().getBackStackEntryCount() > 0))
    {
      getSupportFragmentManager().popBackStack();
      if (getSupportFragmentManager().getBackStackEntryCount() == 1)
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putInt("tab", getSupportActionBar().getSelectedTab().getPosition());
    super.onSaveInstanceState(paramBundle);
  }

  public void setupGeofencing()
  {
    GregorianCalendar localGregorianCalendar1 = new GregorianCalendar(2013, 4, 20);
    GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
    new GregorianCalendar(2013, 4, 16);
    LocationManager localLocationManager = (LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE);;
    if ((localGregorianCalendar1.compareTo(localGregorianCalendar2) > 0) && (!getSharedPreferences("prefs", 0).getBoolean("locset", false)))
    {
      Log.d("Location Intents", "Creating Location Intents");
      localLocationManager = (LocationManager)getSystemService("location");
    }
    for (int i = 0; ; i++)
    {
      if (i >= LocationArrays.achieveLat.length)
      {
        org.holoeverywhere.preference.SharedPreferences.Editor localEditor = getSharedPreferences("prefs", 0).edit();
        localEditor.putBoolean("locset", true);
        localEditor.commit();
        return;
      }
      Intent localIntent = new Intent(this, LocationService.class);
      localIntent.putExtra("loc", i);
      PendingIntent localPendingIntent = PendingIntent.getService(this, i, localIntent, 268435456);
      localLocationManager.addProximityAlert(LocationArrays.achieveLat[i], LocationArrays.achieveLong[i], (float)LocationArrays.achieveRadius[i], localGregorianCalendar1.getTimeInMillis() - localGregorianCalendar2.getTimeInMillis(), localPendingIntent);
    }
  }

  public void startDownload()
  {
    new Downloader(this, new Downloader.onDownloadDoneListener()
    {
      public void onDownloadDone(ArrayList<EventListItem> paramArrayList, String paramString)
      {
        Log.d("Debug", "Done downloading Json");
        int i = 1;
        int j = 0;
        if (j >= paramArrayList.size())
        {
          if (i == 0)
          {
            Main.this.eventsFragment.update(paramArrayList);
            Main.this.eventsFragment.downloaded = true;
            new CachedJson(Main.this, "events").saveJson(paramString);
            return;
          }
        }
        else
        {
          int k = 0;
          for (int m = 0; ; m++)
          {
            if (m >= Main.this.eventsFragment.eventAdapter.listItems.size())
            {
              if (k == 0)
                i = 0;
              j++;
              break;
            }
            if (!((EventListItem)paramArrayList.get(j)).equals(Main.this.eventsFragment.eventAdapter.listItems.get(m)))
              continue;
            k = 1;
          }
        }
        Log.d("Debug", "Downloaded same as cache");
      }

      public void onDownloadFailed()
      {
        Toast.makeText(Main.this, "Unable to download updated events", 0).show();
      }
    }).execute(new String[0]);
    new FoodDownloader(this, new FoodDownloader.onfoodDownloadDoneListener()
    {
      public void onFoodDownloadDone(ArrayList<FoodListItem> paramArrayList, String paramString)
      {
        Log.d("Debug", "Done downloading food Json");
        int i = 1;
        int j = 0;
        if (j >= paramArrayList.size())
        {
          if (i == 0)
          {
            Main.this.foodFragment.update(paramArrayList);
            Main.this.foodFragment.downloaded = true;
            new CachedJson(Main.this, "food").saveJson(paramString);
            return;
          }
        }
        else
        {
          int k = 0;
          for (int m = 0; ; m++)
          {
            if (m >= Main.this.foodFragment.eventAdapter.listItems.size())
            {
              if (k == 0)
                i = 0;
              j++;
              break;
            }
            if (!((FoodListItem)paramArrayList.get(j)).equals(Main.this.foodFragment.eventAdapter.listItems.get(m)))
              continue;
            k = 1;
          }
        }
        Log.d("Debug", "Downloaded food same as cache");
      }

      public void onFoodDownloadFailed()
      {
      }
    }).execute(new String[] { "" });
  }

  class SimpleTabListener
    implements ActionBar.TabListener
  {
    Fragment fragment;
    boolean reload = false;
    int viewId;

    public SimpleTabListener(Fragment paramInt, int arg3)
    {
      this.fragment = paramInt;
      this.viewId = arg3;
    }

    public void onTabReselected(ActionBar.Tab paramTab, FragmentTransaction paramFragmentTransaction)
    {
      if (this.reload)
        onTabSelected(paramTab, paramFragmentTransaction);
    }

    public void onTabSelected(ActionBar.Tab paramTab, FragmentTransaction paramFragmentTransaction)
    {
      String[] arrayOfString = this.fragment.getClass().toString().split("\\.");
      Log.d("analytics", arrayOfString[(-1 + arrayOfString.length)]);
      //EasyTracker.getInstance(getApplicationContext()).sendView(arrayOfString[(-1 + arrayOfString.length)]);
      paramFragmentTransaction.replace(this.viewId, this.fragment);
      Main.this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
      Main.this.getSupportFragmentManager();
      Main.this.getSupportFragmentManager().popBackStack(null, 1);
    }

    public void onTabUnselected(ActionBar.Tab paramTab, FragmentTransaction paramFragmentTransaction)
    {
      paramFragmentTransaction.remove(this.fragment);
    }

    public void setReloadOnReselect(boolean paramBoolean)
    {
      this.reload = paramBoolean;
    }
  }
}