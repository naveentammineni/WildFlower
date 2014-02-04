package com.Richardson.wff.helpers;

public class LocationArrays
{
  public static final double[] achieveLat;
  public static final double[] achieveLong;
  public static final String[] achieveName;
  public static final double[] achieveRadius;
  public static final String[] descArray;
  public static final double[] latArray = { 32.985300000000002D, 32.984999999999999D, 32.984608000000001D, 32.986308000000001D, 32.986441999999997D, 32.984079000000001D, 32.985221000000003D };
  public static final double[] longArray = { -96.710541000000006D, -96.708943000000005D, -96.708382D, -96.706345999999996D, -96.708389999999994D, -96.708271999999994D, -96.709460000000007D };

  static
  {
    descArray = new String[] { "MetroPCS Stage", "Information", "Taste Of Texas Food Garden", "ViewPoint Bank Ampitheatre", "Bud Light Stage", "Courtyard Stage", "Blue Cross Blue Shield of Texas" };
    achieveLat = new double[] { 32.985300000000002D, 32.986441999999997D, 32.984608000000001D, 32.984079000000001D, 32.986308000000001D };
    achieveLong = new double[] { -96.710541000000006D, -96.708389999999994D, -96.708382D, -96.708271999999994D, -96.706345999999996D };
    achieveRadius = new double[] { 30.0D, 30.0D, 30.0D, 30.0D, 30.0D };
    achieveName = new String[] { "MetroPCS Stage", "Bud Light Stage", "Food Court", "Courtyard Stage", "Viewpoint Bank Stage" };
  }
}