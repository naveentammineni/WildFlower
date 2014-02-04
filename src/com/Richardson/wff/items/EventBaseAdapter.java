package com.Richardson.wff.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EventBaseAdapter extends BaseAdapter
{
  public ArrayList<EventListItem> listItems = new ArrayList();

  public void add(EventListItem paramEventListItem)
  {
    this.listItems.add(paramEventListItem);
  }

  public int getCount()
  {
    return this.listItems.size();
  }

  public Object getItem(int paramInt)
  {
    return this.listItems.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (((EventListItem)this.listItems.get(paramInt)).isHeader())
    {
      if ((paramView == null) || ((String)paramView.getTag() != "header"))
        paramView = ((LayoutInflater)paramViewGroup.getContext().getSystemService("layout_inflater")).inflate(2130903070, null);
      ((TextView)paramView.findViewById(2131165315)).setText(((EventListItem)this.listItems.get(paramInt)).getName());
      paramView.setTag("header");
      return paramView;
    }
    if ((paramView == null) || ((String)paramView.getTag() != "item"))
      paramView = ((LayoutInflater)paramViewGroup.getContext().getSystemService("layout_inflater")).inflate(2130903071, null);
    ((TextView)paramView.findViewById(2131165317)).setText(((EventListItem)this.listItems.get(paramInt)).getName());
    String str1 = ((EventListItem)this.listItems.get(paramInt)).getLocation();
    String str3 = "";
    if (((EventListItem)this.listItems.get(paramInt)).hasTime())
    {
      String str2 = new SimpleDateFormat("h:mm", Locale.US).format(((EventListItem)this.listItems.get(paramInt)).getDate().getTime());
      str3 = str1 + " @ " + str2;
      if (((EventListItem)this.listItems.get(paramInt)).getDate().get(9) == 0)
        str1 = str3 + " AM";
    }
    else
    {
      ((TextView)paramView.findViewById(2131165318)).setText(str1);
      if (((EventListItem)this.listItems.get(paramInt)).getImage() == null)
      ((ImageView)paramView.findViewById(2131165316)).setImageBitmap(((EventListItem)this.listItems.get(paramInt)).getImage());
      paramView.setTag("item");
      str1 = str3 + " PM";
    }
    ((ImageView)paramView.findViewById(2131165316)).setImageResource(2130837791);
      return paramView;
      
    }
}