package com.Richardson.wff.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class InfoBaseAdapter extends BaseAdapter
{
  ArrayList<InfoItem> items = new ArrayList();

  public void add(InfoItem paramInfoItem)
  {
    this.items.add(paramInfoItem);
  }

  public int getCount()
  {
    return this.items.size();
  }

  public Object getItem(int paramInt)
  {
    return this.items.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public ArrayList<InfoItem> getItems()
  {
    return this.items;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = ((LayoutInflater)paramViewGroup.getContext().getSystemService("layout_inflater")).inflate(2130903079, null);
    ((TextView)paramView.findViewById(2131165328)).setText(((InfoItem)this.items.get(paramInt)).getTitle());
    return paramView;
  }
}