package com.Richardson.wff;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.Richardson.wff.helpers.ImageDownloader;
import com.Richardson.wff.helpers.ImageDownloader.FoodImageHolder;
import com.Richardson.wff.helpers.ImageDownloader.onImageDownloadDoneListener;
import com.Richardson.wff.items.FoodBaseAdapter;
import com.Richardson.wff.items.FoodListItem;
import java.util.ArrayList;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ListView;

public class TabFood extends Fragment
{
  ImageDownloader.onImageDownloadDoneListener doneListener = new ImageDownloader.onImageDownloadDoneListener()
  {
    public void onDownloadFailed()
    {
      if (TabFood.this.imageDownloadStack.size() > 0)
        TabFood.this.imageDownloadStack.remove(0);
      TabFood.this.startImageDownload();
    }

    public void onImageDownloadDone(Bitmap paramBitmap)
    {
      if (TabFood.this.imageDownloadStack.size() > 0)
      {
        if (((ImageDownloader.FoodImageHolder)TabFood.this.imageDownloadStack.get(0)).item != null)
          ((ImageDownloader.FoodImageHolder)TabFood.this.imageDownloadStack.get(0)).item.setBitmapImage(paramBitmap);
        TabFood.this.imageDownloadStack.remove(0);
      }
      TabFood.this.eventAdapter.notifyDataSetChanged();
      TabFood.this.startImageDownload();
    }
  };
  public boolean downloaded = false;
  FoodBaseAdapter eventAdapter = new FoodBaseAdapter();
  public ArrayList<ImageDownloader.FoodImageHolder> imageDownloadStack = new ArrayList();
  ListView listView;

  public void fillImageQueue(ArrayList<FoodListItem> paramArrayList)
  {
    if (isVisible())
      this.imageDownloadStack.clear();
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
        return;
      if (((FoodListItem)paramArrayList.get(i)).getImage() != null)
        continue;
      this.imageDownloadStack.add(new ImageDownloader.FoodImageHolder(((FoodListItem)paramArrayList.get(i)).getImageURL(), (FoodListItem)paramArrayList.get(i)));
    }
  }

  public void loadLayoutElements(View paramView)
  {
    this.listView = ((ListView)paramView.findViewById(2131165366));
    this.listView.setAdapter(this.eventAdapter);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903125);
    loadLayoutElements(localView);
    return localView;
  }

  public void onResume()
  {
    fillImageQueue(this.eventAdapter.listItems);
    startImageDownload();
    super.onResume();
  }

  public void startImageDownload()
  {
    if ((this.imageDownloadStack.size() > 0) && (isVisible()))
    {
      ImageDownloader localImageDownloader = new ImageDownloader(getActivity(), this.doneListener);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = ((ImageDownloader.FoodImageHolder)this.imageDownloadStack.get(0)).URLString;
      localImageDownloader.execute(arrayOfString);
    }
  }

  public void update()
  {
    this.eventAdapter.notifyDataSetChanged();
  }

  public void update(ArrayList<FoodListItem> paramArrayList)
  {
    this.eventAdapter.listItems = paramArrayList;
    fillImageQueue(this.eventAdapter.listItems);
    startImageDownload();
    update();
  }
}