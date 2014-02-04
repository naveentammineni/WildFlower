package com.Richardson.wff.items;

public class InfoItem
{
  private String description;
  private String title;

  public InfoItem(String paramString)
  {
    this.title = paramString;
  }

  public InfoItem(String paramString1, String paramString2)
  {
    this.title = paramString1;
    this.description = paramString2;
  }

  public String getDescription()
  {
    return this.description;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}