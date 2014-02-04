package com.Richardson.wff;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.Richardson.wff.items.InfoBaseAdapter;
import com.Richardson.wff.items.InfoItem;
import com.actionbarsherlock.app.ActionBar;
import java.util.ArrayList;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ListView;

public class TabInfo extends Fragment
{
  InfoBaseAdapter infoBaseAdapter;
  ListView listView;

  public void addItemsToList()
  {
    this.infoBaseAdapter.add(new InfoItem("When", "Friday, May 17 - 6:00pm to midnight Saturday\n\nMay 18 – 11:00 am to midnight Sunday\n\nMay 19 - 12:30 pm to 8 pm"));
    this.infoBaseAdapter.add(new InfoItem("Where", "We are only a few minutes north of Dallas and are conveniently located off of Central Expressway (US 75) at the Galatyn Parkway exit. \n\nThe actual address is 2351 Performance Drive, Richardson, 75082"));
    this.infoBaseAdapter.add(new InfoItem("Getting There", "The Galatyn Park Station on the DART Red Line Light Rail system is adjacent to the festival’s Westside gateway! So, beat the traffic- park and ride from Dallas or Plano! \n\nFrom Dallas: Take US75 North (Central Expressway) and exit Galatyn Parkway/Renner Road in Richardson. The festival site is located just east of US 75 between Galatyn Parkway and Lookout Drive. Exit Galatyn Parkway and take a right. Follow the parking signs to either park on-site under covered parking or in the surrounding surface lots. \n\nFrom Plano/Oklahoma: Take US75 South into Richardson, exit Galatyn Parkway/Campbell Road in Richardson. The festival site is located just east of US 75 between Galatyn Parkway and Lookout Drive. Exit Galatyn Parkway and take a left. Follow the parking signs to either park on-site under covered parking or in the surrounding surface lots. \n\n"));
    this.infoBaseAdapter.add(new InfoItem("Gateways", "Bud Light Gateway (Will Call) - the Glenville Avenue entrance to Bank of America (just south of Lookout Drive). \n\nBlue Cross/Blue Shield of Texas Gateway - the DART light rail stop at Galatyn Station \n\nCredit Union of Texas Gateway - the intersection of Galatyn Parkway and Performance Drive\n\n"));
    this.infoBaseAdapter.add(new InfoItem("Do's and Don'ts", "In the interest of public safety, please help us out by not bringing coolers, food, drinks, pets, contraband, weapons, skateboards, rollerblades, scooters, laser pointers, Frisbees or professional camera equipment (any camera with a detachable lens). \n\nEach patron can bring in one factory sealed water bottle up to 1 liter. Snacks and drinks for toddlers will be allowed. \n\n\"Professional camera equipment\" is defined as any camera with a detachable lens. Snacks and drinks for toddlers are allowed. The festival does allow audio and video recorders, however, our entertainment contracts disallow audio and video recording within the designated concert zones. Unauthorized sales, solicitations, and distributions are prohibited. \n\nThe backstage area is off-limits to the public. Persons entering the site may be subject to search for any prohibited items. The facility reserves the right to refuse admission or eject any person violating the facility, local, state or federal laws or whose conduct is deemed illegal, disorderly, or offensive by management. \n\nPets: Be kind to your pets- please leave them at home when you visit the Festival. Only seeing-eye dogs and service dogs are allowed within the Festival site during Festival operating hours.Refund Policy: There are absolutely no refunds. Admission tickets are valid for their day of purchase only. \n\nRe-entry policy: If you plan on returning you must have your hand stamped when you exit. This stamp will allow you re-entry only for that particular day."));
    this.infoBaseAdapter.add(new InfoItem("Smoking", "For your convenience, smoking is allowed in designated smoking areas throughout the festival site; locations are identified on the site map."));
    this.infoBaseAdapter.add(new InfoItem("Seating", "All seating at Wildflower, both for indoor and outdoor venues, is \"general admission; first come, first serve.\" For outdoor seating, it's also considered \"festival viewing,\" and blankets, cushions, and chairs may be brought in, but please keep in mind that patrons may be standing in front of you during a performance. Chairs should be low to the ground and have backs at shoulder height. We want everyone to enjoy the concerts and have a great time. Be respectful of people around you and please share your space, get to know your neighbors, and make room for others!"));
    this.infoBaseAdapter.add(new InfoItem("Lost and Found", "The Information Booth serves as the Festival’s Lost and Found location. The Information Booth is located at the intersection of Performance Drive and Performance Court- the southeast corner of the Eisemann Center. Lost and Found items are kept here until the festival gates close on Sunday night. After the festival ends, please call 972-744-4580 to inquire about lost and found items."));
    this.infoBaseAdapter.add(new InfoItem("Found Children", "The City of Richardson Police Command Unit is located right inside the Credit Union of Texas Gateway, at the south end of the Taste of Texas Food Garden. We encourage your family to identify this spot (or other festival location) as a good place to meet in case anyone becomes separated. This is our Found Children's Area."));
    this.infoBaseAdapter.add(new InfoItem("First Aid", "First aid service is available within the Festival site at the City of Richardson’s Fire Department/EMS tent located near the intersection of Performance Drive and Performance Court."));
    this.infoBaseAdapter.add(new InfoItem("Parking", "Over 8,000 spaces are available within the immediate area surrounding Galatyn Park. Once you exit Galatyn Parkway signs will direct you to parking areas. There is a small fee ($5) for on-site covered parking and in the adjacent surface lots."));
    this.infoBaseAdapter.add(new InfoItem("Contact Us", "Wildflower! Arts and Music Festival 2100 E. Campbell Road Suite 100 Richardson, Texas 75081 \n\nPhone 972-744-4580 \n\nFAX 972-744-5827 \n\nEmail wildflower@cor.gov"));
    this.infoBaseAdapter.add(new InfoItem("Vendor/Concessions/Artists", "Whether you’re a professional exhibitor, concessionaire, arts and crafts vendor, or a business that wants to participate in our bid process, please contact us at wildflower@cor.gov. Be sure to include all of your current contact information, and please specify what type of vendor you are. We’ll add you to our mailing list."));
    this.infoBaseAdapter.add(new InfoItem("Cash or Coupons?", "All vendors accept cash, no coupons are needed at Wildflower! Besides, where would you RATHER be, in front of a stage or in line to buy coupons?"));
  }

  public void loadLayoutElements(View paramView)
  {
    this.listView = ((ListView)paramView.findViewById(2131165368));
    this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        TabInfoDisplay localTabInfoDisplay = new TabInfoDisplay();
        Bundle localBundle = new Bundle();
        localBundle.putString("desc", ((InfoItem)TabInfo.this.infoBaseAdapter.getItems().get(paramInt)).getDescription());
        localTabInfoDisplay.setArguments(localBundle);
        localTabInfoDisplay.setArguments(localBundle);
        FragmentTransaction localFragmentTransaction = TabInfo.this.getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.replace(2131165285, localTabInfoDisplay);
        localFragmentTransaction.addToBackStack(null);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        localFragmentTransaction.commit();
      }
    });
    this.listView.setAdapter(this.infoBaseAdapter);
    addItemsToList();
    this.listView.setAdapter(this.infoBaseAdapter);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903127);
    this.infoBaseAdapter = new InfoBaseAdapter();
    loadLayoutElements(localView);
    return localView;
  }
}