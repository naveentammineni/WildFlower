package com.Richardson.wff.helpers;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser<E>
{
  private Class<E> class1;
  public String json;
  private JSONArray jsonArray;
  private JSONObject jsonObject;
  private ArrayList<String> keys;
  private ArrayList<Method> setters;

  public JsonParser(JSONArray paramJSONArray, Class<E> paramClass)
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() > 0));
    try
    {
      this.jsonObject = paramJSONArray.getJSONObject(0);
      this.jsonArray = paramJSONArray;
      this.keys = new ArrayList();
      this.setters = new ArrayList();
      this.class1 = paramClass;
    }
    catch (JSONException localJSONException)
    {
        localJSONException.printStackTrace();
    }
    throw new NoArgConstructorUndefinedException(paramClass);
  }

  public JsonParser(JSONObject paramJSONObject, Class<E> paramClass)
  {
    this.jsonObject = paramJSONObject;
    this.keys = new ArrayList();
    this.setters = new ArrayList();
    this.class1 = paramClass;
    try
    {
      this.class1.getConstructor(new Class[0]);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.e("JsonParser Error", paramClass.getName() + " does not contain a no-arg constructor!");
      localNoSuchMethodException.printStackTrace();
    }
    throw new NoArgConstructorUndefinedException(paramClass);
  }

  private E parse(JSONObject paramJSONObject)
  {
    while (true)
    {
      E localObject;
      int i;
      try
      {
        localObject = this.class1.newInstance();
        i = 0;
        if (i >= this.setters.size())
          return localObject;
      }
      catch (InstantiationException localInstantiationException)
      {
        Log.e("Json Parser", "Unable to instatiate - Make sure that there is a no-arg constructor");
        localInstantiationException.printStackTrace();
        return null;
      }
      catch (IllegalAccessException localIllegalAccessException1)
      {
        localIllegalAccessException1.printStackTrace();
        return null;
      }
      try
      {
        if (paramJSONObject.has((String)this.keys.get(i)))
        {
          Method localMethod = (Method)this.setters.get(i);
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = ((Method)this.setters.get(i)).getParameterTypes()[0].cast(paramJSONObject.get((String)this.keys.get(i)));
          localMethod.invoke(localObject, arrayOfObject);
        }
        i++;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        while (true)
          localIllegalArgumentException.printStackTrace();
      }
      catch (IllegalAccessException localIllegalAccessException2)
      {
        while (true)
          localIllegalAccessException2.printStackTrace();
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        while (true)
        {
          Log.d("Debug", ((Method)this.setters.get(i)).toString());
          localInvocationTargetException.printStackTrace();
        }
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }
  }

  public void assignSetter(Method paramMethod, String paramString)
  {
    this.keys.add(paramString);
    this.setters.add(paramMethod);
  }

  public void assignSetters()
  {
    assignSetters(this.class1.getMethods());
  }

  public void assignSetters(Method[] paramArrayOfMethod)
  {
    int i = 0;
    if (i >= paramArrayOfMethod.length)
      return;
    String str1;
    Iterator localIterator;
    if ((paramArrayOfMethod[i].getName().length() > 3) && (paramArrayOfMethod[i].getName().substring(0, 3).equals("set")))
    {
      str1 = paramArrayOfMethod[i].getName().substring(3, paramArrayOfMethod[i].getName().length());
      localIterator = this.jsonObject.keys();
       while(localIterator.hasNext()){
    	   i++;
    	   String str2 = (String)localIterator.next();
    	   if (!str2.toLowerCase().equals(str1.toLowerCase())){
    		   assignSetter(paramArrayOfMethod[i], str2);
    	   }
       }
    }
  }

  public E parse()
  {
    return parse(this.jsonObject);
  }

  public ArrayList<E> parseArray()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (true)
    {
      if (i >= this.jsonArray.length())
        return localArrayList;
      try
      {
        localArrayList.add(parse(this.jsonArray.getJSONObject(i)));
        i++;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
      }
    }
  }
}