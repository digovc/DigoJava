package com.digosofter.digojava.json;

import com.digosofter.digojava.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.Calendar;

public final class Json
{
  private static Json _i;

  public static Json getI()
  {
    if (_i != null)
    {
      return _i;
    }

    _i = new Json();

    return _i;
  }

  private Gson _objGson;
  private GsonBuilder _objGsonBuilder;

  public <T> T fromJson(String jsn, Type cls)
  {
    if (Utils.getBooStrVazia(jsn))
    {
      return null;
    }

    return this.getObjGson().fromJson(jsn, cls);
  }

  public <T> T fromJson(JsonElement jsnElement, Type cls)
  {
    if (jsnElement == null)
    {
      return null;
    }

    return this.getObjGson().fromJson(jsnElement, cls);
  }

  private Gson getObjGson()
  {
    if (_objGson != null)
    {
      return _objGson;
    }

    _objGson = this.getObjGsonBuilder().create();

    return _objGson;
  }

  private GsonBuilder getObjGsonBuilder()
  {
    if (_objGsonBuilder != null)
    {
      return _objGsonBuilder;
    }

    _objGsonBuilder = new GsonBuilder();

    _objGsonBuilder.registerTypeHierarchyAdapter(Calendar.class, new CalendarTypeAdapter());
    _objGsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    return _objGsonBuilder;
  }

  public String toJson(Object obj)
  {
    return this.getObjGson().toJson(obj);
  }
}