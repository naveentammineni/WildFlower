package com.Richardson.wff;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

public class TabInfoDisplay extends Fragment
{
  String description;

  public void loadLayoutElements(View paramView)
  {
    ((TextView)paramView.findViewById(2131165367)).setText(this.description);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903126);
    loadLayoutElements(localView);
    return localView;
  }

  public void setArguments(Bundle paramBundle)
  {
    this.description = paramBundle.getString("desc");
    super.setArguments(paramBundle);
  }
}