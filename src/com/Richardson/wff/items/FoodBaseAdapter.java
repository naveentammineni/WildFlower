package com.Richardson.wff.items;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class FoodBaseAdapter extends BaseAdapter
{
  public ArrayList<FoodListItem> listItems = new ArrayList();

  public void add(FoodListItem paramFoodListItem)
  {
    this.listItems.add(paramFoodListItem);
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
    if (((FoodListItem)this.listItems.get(paramInt)).isHeader())
    {
      if ((paramView == null) || ((String)paramView.getTag() != "header"))
        paramView = ((LayoutInflater)paramViewGroup.getContext().getSystemService("layout_inflater")).inflate(2130903074, null);
      ((TextView)paramView.findViewById(2131165320)).setText(((FoodListItem)this.listItems.get(paramInt)).getName());
      paramView.setTag("header");
      return paramView;
    }
    if ((paramView == null) || ((String)paramView.getTag() != "item"))
      paramView = ((LayoutInflater)paramViewGroup.getContext().getSystemService("layout_inflater")).inflate(2130903075, null);
    ((TextView)paramView.findViewById(2131165322)).setText(((FoodListItem)this.listItems.get(paramInt)).getName());
    ((TextView)paramView.findViewById(2131165323)).setText(((FoodListItem)this.listItems.get(paramInt)).getDescription());
    if (((FoodListItem)this.listItems.get(paramInt)).getImage() != null)
      ((ImageView)paramView.findViewById(2131165321)).setImageBitmap(((FoodListItem)this.listItems.get(paramInt)).getImage());
    while (true)
    {
      paramView.setTag("item");
      ((ImageView)paramView.findViewById(2131165321)).setImageBitmap(BitmapFactory.decodeResource(paramViewGroup.getContext().getResources(), 2130837791));
      return paramView;
      
    }
  }
}