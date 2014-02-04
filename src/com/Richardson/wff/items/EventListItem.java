package com.Richardson.wff.items;

import android.graphics.Bitmap;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EventListItem
  implements Comparable<EventListItem>
{
  private static final int LISTEN_TYPE_MP3 = 2;
  private static final int LISTEN_TYPE_YOUTUBE = 1;
  private String bandURL;
  private GregorianCalendar datetimeCalendar = new GregorianCalendar();
  private boolean hasTime = false;
  private String id;
  private Bitmap image;
  private String imageURL;
  private boolean isHeader = false;
  private int listenType = 0;
  private String listenURL;
  private String location;
  private String name;

  public EventListItem()
  {
    this.isHeader = false;
  }

  public EventListItem(String paramString)
  {
    this.isHeader = true;
    this.name = paramString;
  }

  public EventListItem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.name = paramString1;
    setStartTime(paramString2);
    this.location = paramString4;
    this.id = paramString5;
    setEventDate(paramString3);
    this.hasTime = true;
  }

  public EventListItem(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Bitmap paramBitmap)
  {
    this.name = paramString1;
    setStartTime(paramString2);
    this.location = paramString4;
    this.image = paramBitmap;
    this.id = paramString5;
    setEventDate(paramString3);
    this.hasTime = true;
  }

  public int compareTo(EventListItem paramEventListItem)
  {
    return this.datetimeCalendar.compareTo(paramEventListItem.datetimeCalendar);
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof EventListItem))
    {
      EventListItem localEventListItem = (EventListItem)paramObject;
      return (this.name.equals(localEventListItem.getName())) && (this.datetimeCalendar.get(10) == localEventListItem.getDate().get(10)) && (this.datetimeCalendar.get(12) == localEventListItem.getDate().get(12)) && (this.datetimeCalendar.get(6) == localEventListItem.getDate().get(6)) && (this.datetimeCalendar.get(1) == localEventListItem.getDate().get(1)) && (this.id.equals(localEventListItem.getId())) && (this.location.equals(localEventListItem.getLocation())) && (this.imageURL.equals(localEventListItem.getImageURL()));
    }
    return false;
  }

  public GregorianCalendar getDate()
  {
    return this.datetimeCalendar;
  }

  public String getId()
  {
    return this.id;
  }

  public Bitmap getImage()
  {
    return this.image;
  }

  public String getImageURL()
  {
    return this.imageURL;
  }

  public int getListenType()
  {
    return this.listenType;
  }

  public String getListenURL()
  {
    return this.listenURL;
  }

  public String getLocation()
  {
    return this.location;
  }

  public String getName()
  {
    return this.name;
  }

  public String getWebsite()
  {
    return this.bandURL;
  }

  public boolean hasTime()
  {
    return this.hasTime;
  }

  public boolean isHeader()
  {
    return this.isHeader;
  }

  public void setAudioLink(String paramString)
  {
    this.listenURL = paramString;
    if (!this.listenURL.startsWith("http://"))
      this.listenURL = ("http://" + this.listenURL);
    String[] arrayOfString = this.listenURL.split("\\.");
    
    for(int i=0;i >= arrayOfString.length;i++){
        if (!arrayOfString[arrayOfString.length-1].equals("mp3"))
        	this.listenType = 2;
        if (arrayOfString[i].equals("youtube"))
          this.listenType = 1;
    }
  }

  public void setBandLink(String paramString)
  {
    this.bandURL = paramString;
  }

  public void setBandPic(String paramString)
  {
    this.imageURL = paramString;
  }

  public void setEventDate(String paramString)
  {
    try
    {
      String[] arrayOfString = paramString.split("/");
      this.datetimeCalendar.set(Integer.parseInt(arrayOfString[2]), -1 + Integer.parseInt(arrayOfString[0]), Integer.parseInt(arrayOfString[1]));
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void setEventID(String paramString)
  {
    this.id = paramString;
  }

  public void setHeader(boolean paramBoolean)
  {
    this.isHeader = paramBoolean;
  }

  public void setImage(Bitmap paramBitmap)
  {
    this.image = paramBitmap;
  }

  public void setStage(String paramString)
  {
    this.location = paramString;
  }

  public void setStartTime(String paramString)
  {
    try
    {
      String[] arrayOfString1 = paramString.split(" ");
      String[] arrayOfString2 = arrayOfString1[0].split(":");
      int i = Integer.parseInt(arrayOfString2[1]);
      int j = Integer.parseInt(arrayOfString2[0]);
      if ((arrayOfString1[1].toLowerCase(Locale.getDefault()).equals("pm")) && (j < 12))
        j += 12;
      if ((arrayOfString1[1].toLowerCase(Locale.getDefault()).equals("am")) && (j == 12))
        j = 0;
      this.datetimeCalendar.set(11, j);
      this.datetimeCalendar.set(12, i);
      this.hasTime = true;
      return;
    }
    catch (Exception localException)
    {
      this.datetimeCalendar.set(10, 0);
      this.datetimeCalendar.set(12, 0);
      this.hasTime = false;
    }
  }

  public void setTitle(String paramString)
  {
    this.name = paramString;
  }
}