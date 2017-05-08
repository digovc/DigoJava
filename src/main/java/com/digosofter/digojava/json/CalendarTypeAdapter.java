package com.digosofter.digojava.json;

import com.digosofter.digojava.Utils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Calendar;

public class CalendarTypeAdapter implements JsonDeserializer<Calendar>, JsonSerializer<Calendar>
{
  @Override
  public Calendar deserialize(final JsonElement jsn, final Type clsTypeOfT, final JsonDeserializationContext objContext) throws JsonParseException
  {
    if (jsn == null)
    {
      return null;
    }

    return Utils.strToDtt(jsn.getAsString());
  }

  @Override
  public JsonElement serialize(final Calendar dtt, final Type clsTypeOfSrc, final JsonSerializationContext objContext)
  {
    if (dtt == null)
    {
      return null;
    }

    return new JsonPrimitive(Utils.getStrDataFormatada(dtt, Utils.EnmDataFormato.YYYY_MM_DD_HH_T_MM_SS_Z));
  }
}