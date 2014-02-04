package com.Richardson.wff;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.Richardson.wff.helpers.ImageDownloader;
import com.Richardson.wff.helpers.ImageDownloader.EventimageHolder;
import com.Richardson.wff.helpers.ImageDownloader.onImageDownloadDoneListener;
import com.Richardson.wff.items.EventBaseAdapter;
import com.Richardson.wff.items.EventListItem;
import com.actionbarsherlock.app.ActionBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ListView;

public class TabEvents extends Fragment
{
  ImageDownloader.onImageDownloadDoneListener downloadDoneListener = new ImageDownloader.onImageDownloadDoneListener()
  {
    public void onDownloadFailed()
    {
      if (TabEvents.this.imageDownloadStack.size() > 0)
        TabEvents.this.imageDownloadStack.remove(0);
      TabEvents.this.startImageDownload();
    }

    public void onImageDownloadDone(Bitmap paramBitmap)
    {
      if (TabEvents.this.imageDownloadStack.size() > 0)
      {
        if (((ImageDownloader.EventimageHolder)TabEvents.this.imageDownloadStack.get(0)).item != null)
          ((ImageDownloader.EventimageHolder)TabEvents.this.imageDownloadStack.get(0)).item.setImage(paramBitmap);
        TabEvents.this.imageDownloadStack.remove(0);
      }
      TabEvents.this.eventAdapter.notifyDataSetChanged();
      TabEvents.this.startImageDownload();
    }
  };
  boolean downloaded = false;
  EventBaseAdapter eventAdapter = new EventBaseAdapter();
  public ArrayList<ImageDownloader.EventimageHolder> imageDownloadStack = new ArrayList();
  ListView listView;

  public void addDateHeaders(EventBaseAdapter paramEventBaseAdapter)
  {
    String[] arrayOfString = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    removeHeaders(paramEventBaseAdapter);
    int i = 0;
    int j = paramEventBaseAdapter.listItems.size();
    GregorianCalendar localGregorianCalendar = null;
    if (i >= j);
     for (int m = 0; ; m++)
    {
      if (m >= paramEventBaseAdapter.listItems.size())
      {
        
        if (!((EventListItem)paramEventBaseAdapter.listItems.get(i)).isHeader())
        {
          localGregorianCalendar = ((EventListItem)paramEventBaseAdapter.listItems.get(i)).getDate();
          int k = localGregorianCalendar.get(7);
          paramEventBaseAdapter.listItems.add(i, new EventListItem(arrayOfString[(k - 1)]));
          break ;
        }
        i++;
        break;
      }
      if ((((EventListItem)paramEventBaseAdapter.listItems.get(m)).isHeader()) || (localGregorianCalendar.get(6) == ((EventListItem)paramEventBaseAdapter.listItems.get(m)).getDate().get(6)))
        continue;
      localGregorianCalendar = ((EventListItem)paramEventBaseAdapter.listItems.get(m)).getDate();
      String str = arrayOfString[(-1 + localGregorianCalendar.get(7))];
      paramEventBaseAdapter.listItems.add(m, new EventListItem(str));
    }
  }

  public void fillImageQueue(ArrayList<EventListItem> paramArrayList)
  {
    if (isVisible())
      this.imageDownloadStack.clear();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
        return;
      if ((((EventListItem)paramArrayList.get(i)).getImage() != null) || (((EventListItem)paramArrayList.get(i)).isHeader()))
        continue;
      this.imageDownloadStack.add(new ImageDownloader.EventimageHolder(((EventListItem)paramArrayList.get(i)).getImageURL(), (EventListItem)paramArrayList.get(i)));
    }
  }

  public void loadLayoutElements(View paramView)
  {
    this.listView = ((ListView)paramView.findViewById(2131165365));
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        if (!((EventListItem)TabEvents.this.eventAdapter.listItems.get(paramInt)).isHeader())
        {
          TabEventView localTabEventView = new TabEventView();
          localTabEventView.setEventItem((EventListItem)TabEvents.this.eventAdapter.listItems.get(paramInt));
          FragmentTransaction localFragmentTransaction = TabEvents.this.getSupportFragmentManager().beginTransaction();
          localFragmentTransaction.replace(2131165285, localTabEventView);
          localFragmentTransaction.addToBackStack(null);
          getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
          localFragmentTransaction.commit();
        }
      }
    });
    this.listView.setAdapter(this.eventAdapter);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903124);
    loadLayoutElements(localView);
    return localView;
  }

  public void onResume()
  {
    super.onResume();
    fillImageQueue(this.eventAdapter.listItems);
    startImageDownload();
  }

  public void removeHeaders(EventBaseAdapter paramEventBaseAdapter)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramEventBaseAdapter.listItems.size())
        return;
      if (!((EventListItem)paramEventBaseAdapter.listItems.get(i)).isHeader())
        continue;
      paramEventBaseAdapter.listItems.remove(i);
      i--;
    }
  }

  public void startImageDownload()
  {
    if ((this.imageDownloadStack.size() > 0) && (isVisible()))
    {
      ImageDownloader localImageDownloader = new ImageDownloader(getActivity(), this.downloadDoneListener);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = ((ImageDownloader.EventimageHolder)this.imageDownloadStack.get(0)).URLString;
      localImageDownloader.execute(arrayOfString);
    }
  }

  public void update()
  {
    this.eventAdapter.notifyDataSetChanged();
  }

  public void update(ArrayList<EventListItem> paramArrayList)
  {
    this.eventAdapter.listItems = paramArrayList;
    Collections.sort(this.eventAdapter.listItems);
    addDateHeaders(this.eventAdapter);
    fillImageQueue(this.eventAdapter.listItems);
    startImageDownload();
    update();
  }
}