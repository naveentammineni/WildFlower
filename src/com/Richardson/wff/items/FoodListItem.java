package com.Richardson.wff.items;

import android.graphics.Bitmap;

public class FoodListItem
{
  private String description;
  private Bitmap image;
  private String imageURL;
  private boolean isHeader = false;
  private String name;

  public FoodListItem()
  {
  }

  public FoodListItem(String paramString)
  {
    this.isHeader = true;
    this.name = paramString;
  }

  public FoodListItem(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.description = paramString2;
  }

  public FoodListItem(String paramString1, String paramString2, Bitmap paramBitmap)
  {
    this.name = paramString1;
    this.description = paramString2;
    this.image = paramBitmap;
  }

  public String getDescription()
  {
    return this.description;
  }

  public Bitmap getImage()
  {
    return this.image;
  }

  public String getImageURL()
  {
    return this.imageURL;
  }

  public String getName()
  {
    return this.name;
  }

  public boolean isHeader()
  {
    return this.isHeader;
  }

  public void setBitmapImage(Bitmap paramBitmap)
  {
    this.image = paramBitmap;
  }

  public void setImage(String paramString)
  {
    this.imageURL = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString.replaceAll("&amp;", "&");
  }

  public void setType(String paramString)
  {
    this.description = paramString;
  }
}