package com.Richardson.wff;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import java.util.Timer;
import java.util.TimerTask;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Button;

public class TabMain extends Fragment
{
  ActionBar actionBar;
  String[] headlinesStrings = { "ViewPoint Bank Stage-Friday@10:00pm", "ViewPoint Bank Stage-Saturday@10:00pm", "Metro PCS Stage-Friday@9:00pm", "Metro PCS Stage-Saturday@9:30pm", "Special Thanks to our Sponsors!" };
  HorizontalScrollView horizontalScrollView;
  LinearLayout imageHolderLinearLayout;
  int scrollTo = 0;
  Timer timer;

  public void animate()
  {
    this.timer = new Timer();
    this.timer.scheduleAtFixedRate(new TimerTask()
    {
      public void run()
      {
        int i = TabMain.this.horizontalScrollView.getScrollX();
        int j = TabMain.this.imageHolderLinearLayout.getMeasuredWidth();
        int k = j / 5;
        if (k == 0)
          k = 1;
        TabMain.this.scrollTo = (k + (i - i % k));
        if (TabMain.this.scrollTo >= j)
          TabMain.this.scrollTo = 0;
        try
        {
          ((TextView)TabMain.this.getView().findViewById(2131165376)).post(new Runnable()
          {
            public void run()
            {
              ((TextView)TabMain.this.getView().findViewById(2131165376)).setText(TabMain.this.headlinesStrings[(TabMain.this.scrollTo / (TabMain.this.imageHolderLinearLayout.getMeasuredWidth() / 5))]);
              ((TextView)TabMain.this.getView().findViewById(2131165376)).requestFocus();
            }
          });
          TabMain.this.horizontalScrollView.post(new Runnable()
          {
            public void run()
            {
              TabMain.this.horizontalScrollView.smoothScrollTo(TabMain.this.scrollTo, 0);
            }
          });
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
    , 0L, 8000L);
  }

  public void loadLayoutElements(View paramView)
  {
    this.horizontalScrollView = ((HorizontalScrollView)paramView.findViewById(2131165369));
    this.horizontalScrollView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        int j=0, k=0, m=0;
        if ((paramMotionEvent.getAction() == 3) || (paramMotionEvent.getAction() == 1) || (paramMotionEvent.getAction() == 4))
        {
          int i = TabMain.this.horizontalScrollView.getScrollX();
          j = TabMain.this.imageHolderLinearLayout.getMeasuredWidth() / 5;
          if (j == 0)
            j = 1;
          k = i - (i % j);
          m = i + j -( i % j);
          if ((i - k) > (j / 2)) {
        	  ((TextView)TabMain.this.getView().findViewById(2131165376)).setText(TabMain.this.headlinesStrings[(k / j)]);
        	  ((TextView)TabMain.this.getView().findViewById(2131165376)).requestFocus();
        	  TabMain.this.scrollTo = k;
        	  TabMain.this.horizontalScrollView.post(new Runnable()
        	  {
        		  public void run()
        		  {
        			  TabMain.this.horizontalScrollView.smoothScrollTo(TabMain.this.scrollTo, 0);
        		  }
        	  });
          }
            TabMain.this.scrollTo = m;
               ((TextView)TabMain.this.getView().findViewById(2131165376)).setText(TabMain.this.headlinesStrings[(m / j)]);
              ((TextView)TabMain.this.getView().findViewById(2131165376)).requestFocus();
              TabMain.this.horizontalScrollView.post(new Runnable() {
            	  public void run()
            	  {
            		  TabMain.this.horizontalScrollView.smoothScrollTo(TabMain.this.scrollTo, 0);
            	  }
              	});
        }
        return true;
      }
    });
    Runnable local3 = new Runnable()
    {
      public void run()
      {
        try
        {
          ((ImageView)TabMain.this.getView().findViewById(2131165371)).setLayoutParams(new LinearLayout.LayoutParams(TabMain.this.horizontalScrollView.getWidth(), 260 * TabMain.this.horizontalScrollView.getWidth() / 600));
          ((ImageView)TabMain.this.getView().findViewById(2131165372)).setLayoutParams(new LinearLayout.LayoutParams(TabMain.this.horizontalScrollView.getWidth(), 260 * TabMain.this.horizontalScrollView.getWidth() / 600));
          ((ImageView)TabMain.this.getView().findViewById(2131165373)).setLayoutParams(new LinearLayout.LayoutParams(TabMain.this.horizontalScrollView.getWidth(), 260 * TabMain.this.horizontalScrollView.getWidth() / 600));
          ((ImageView)TabMain.this.getView().findViewById(2131165374)).setLayoutParams(new LinearLayout.LayoutParams(TabMain.this.horizontalScrollView.getWidth(), 260 * TabMain.this.horizontalScrollView.getWidth() / 600));
          ((ImageView)TabMain.this.getView().findViewById(2131165375)).setLayoutParams(new LinearLayout.LayoutParams(TabMain.this.horizontalScrollView.getWidth(), 260 * TabMain.this.horizontalScrollView.getWidth() / 600));
          return;
        }
        catch (NullPointerException localNullPointerException)
        {
        }
      }
    };
    this.imageHolderLinearLayout = ((LinearLayout)paramView.findViewById(2131165370));
    this.horizontalScrollView.post(local3);
    ((Button)paramView.findViewById(2131165378)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        TabMain.this.actionBar.setSelectedNavigationItem(1);
      }
    });
    ((Button)paramView.findViewById(2131165379)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setData(Uri.parse("https://tix.extremetix.com/Online/mEvents.jsp?siteID=1722"));
        TabMain.this.startActivity(localIntent);
      }
    });
    ((Button)paramView.findViewById(2131165380)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        FragmentTransaction localFragmentTransaction = TabMain.this.getSupportActivity().getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.replace(2131165285, new TabMap());
        localFragmentTransaction.addToBackStack(null);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        localFragmentTransaction.commit();
      }
    });
    org.holoeverywhere.preference.SharedPreferences localSharedPreferences = getSharedPreferences("prefs", 0);
    LocationManager localLocationManager = (LocationManager)getActivity().getSystemService("location");
    TextView localTextView = (TextView)paramView.findViewById(2131165377);
    if (localLocationManager.isProviderEnabled("gps"))
    {
      if (!localSharedPreferences.getString("achieve", "").contains("-0-"))
        localTextView.setText("Visit the " + com.Richardson.wff.helpers.LocationArrays.achieveName[0] + "!");
      while (true)
      {
        localTextView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            getActivity().getActionBar().setSelectedNavigationItem(3);
          }
        });
        if (!localSharedPreferences.getString("achieve", "").contains("-1-"))
        {
          localTextView.setText("Visit the " + com.Richardson.wff.helpers.LocationArrays.achieveName[1] + "!");
          continue;
        }
        if (!localSharedPreferences.getString("achieve", "").contains("-2-"))
        {
          localTextView.setText("Visit the " + com.Richardson.wff.helpers.LocationArrays.achieveName[2] + "!");
          continue;
        }
        if (!localSharedPreferences.getString("achieve", "").contains("-3-"))
        {
          localTextView.setText("Visit the " + com.Richardson.wff.helpers.LocationArrays.achieveName[3] + "!");
          continue;
        }
        if (localSharedPreferences.getString("achieve", "").contains("-4-"))
          continue;
        localTextView.setText("Visit the " + com.Richardson.wff.helpers.LocationArrays.achieveName[4] + "!");
      }
    }
    localTextView.setText("Enable your GPS for achievements!");
    localTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        TabMain.this.startActivity(localIntent);
      }
    });
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903128);
    this.actionBar = ((Activity)getActivity()).getSupportActionBar();
    loadLayoutElements(localView);
    animate();
    return localView;
  }

  public void onPause()
  {
    this.timer.cancel();
    this.timer.purge();
    super.onPause();
  }
}