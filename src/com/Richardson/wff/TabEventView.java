package com.Richardson.wff;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.Richardson.wff.items.EventListItem;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

public class TabEventView extends Fragment
{
  EventListItem eventListItem;
  String timeString;

  public void loadLayoutElements(View paramView)
  {
    ((TextView)paramView.findViewById(2131165360)).setText(this.eventListItem.getName());
    if (this.eventListItem.getName().equals(""))
      ((TextView)paramView.findViewById(2131165360)).setVisibility(1);
    ((TextView)paramView.findViewById(2131165361)).setText(this.eventListItem.getLocation());
    if (this.eventListItem.getLocation().equals(""))
      ((TextView)paramView.findViewById(2131165361)).setVisibility(1);
    if (this.eventListItem.getListenType() == 0)
      ((Button)paramView.findViewById(2131165364)).setVisibility(1);
    ((Button)paramView.findViewById(2131165364)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        TabEventView.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(TabEventView.this.eventListItem.getListenURL())));
      }
    });
    String[] arrayOfString = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("h:mm", Locale.US);
    Date localDate = this.eventListItem.getDate().getTime();
    this.timeString = (arrayOfString[(-1 + this.eventListItem.getDate().get(7))] + " @ " + localSimpleDateFormat.format(localDate));
    if (this.eventListItem.hasTime())
      ((TextView)paramView.findViewById(2131165362)).setText(this.timeString);
    while (true)
    {
      if (this.eventListItem.getImage() != null)
        ((ImageView)paramView.findViewById(2131165359)).setImageBitmap(this.eventListItem.getImage());
      if (this.eventListItem.getWebsite().equals(""))
        break;
      ((Button)paramView.findViewById(2131165363)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          String str = TabEventView.this.eventListItem.getWebsite();
          if ((!str.startsWith("http://")) && (!str.startsWith("https://")))
            str = "http://" + str;
          Intent localIntent = new Intent("android.intent.action.VIEW");
          localIntent.setData(Uri.parse(str));
          TabEventView.this.startActivity(localIntent);
        }
      });
      ((TextView)paramView.findViewById(2131165362)).setVisibility(1);
    }
    ((Button)paramView.findViewById(2131165363)).setVisibility(1);
  }

  public void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      this.eventListItem = new EventListItem();
      if (paramBundle.containsKey("name"))
        this.eventListItem.setTitle(paramBundle.getString("name"));
      if (paramBundle.containsKey("location"))
        this.eventListItem.setStage(paramBundle.getString("location"));
      if (paramBundle.containsKey("time"))
        this.timeString = paramBundle.getString("time");
      if (paramBundle.containsKey("image"))
      {
        byte[] arrayOfByte = paramBundle.getByteArray("image");
        this.eventListItem.setImage(BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length));
      }
      if (paramBundle.containsKey("web"))
        this.eventListItem.setBandLink(paramBundle.getString("web"));
      paramBundle.containsKey("listenURL");
    }
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903123);
    loadLayoutElements(localView);
    return localView;
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putString("name", this.eventListItem.getName());
    paramBundle.putString("location", this.eventListItem.getLocation());
    if (this.eventListItem.hasTime())
    {
      String[] arrayOfString = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("h:mm", Locale.US);
      Date localDate = this.eventListItem.getDate().getTime();
      this.timeString = (arrayOfString[this.eventListItem.getDate().get(7)] + " @ " + localSimpleDateFormat.format(localDate));
      paramBundle.putString("time", this.timeString);
    }
    paramBundle.putString("web", this.eventListItem.getWebsite());
    paramBundle.putString("listenURL", this.eventListItem.getListenURL());
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      this.eventListItem.getImage().compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
      paramBundle.putByteArray("image", localByteArrayOutputStream.toByteArray());
      label222: super.onSaveInstanceState(paramBundle);
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
    	localNullPointerException.printStackTrace();
    }
  }

  public void setEventItem(EventListItem paramEventListItem)
  {
    this.eventListItem = paramEventListItem;
  }
}