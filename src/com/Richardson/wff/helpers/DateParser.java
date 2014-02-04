package com.Richardson.wff.helpers;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

public class DateParser extends DateFormat
{
  public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
  {
    return null;
  }

  public Date parse(String paramString, ParsePosition paramParsePosition)
  {
    String[] arrayOfString = paramString.split("/");
    this.calendar.set(Integer.parseInt(arrayOfString[2]), Integer.parseInt(arrayOfString[1]), Integer.parseInt(arrayOfString[0]));
    return this.calendar.getTime();
  }
}