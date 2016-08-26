package com.digosofter.digojava.database;

import com.digosofter.digojava.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

public abstract class UtilsDataBase
{
  public static boolean getBoo(ResultSet rst, String strClnNome)
  {
    if (rst == null)
    {
      return false;
    }

    if (Utils.getBooStrVazia(strClnNome))
    {
      return false;
    }

    try
    {
      if (rst.findColumn(strClnNome) < 0)
      {
        return false;
      }

      String strValor = rst.getString(rst.findColumn(strClnNome));

      return Utils.getBoo(strValor);
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return false;
  }

  public static double getDbl(ResultSet rst, String strClnNome)
  {
    if (rst == null)
    {
      return 0;
    }

    if (Utils.getBooStrVazia(strClnNome))
    {
      return 0;
    }

    try
    {
      if (rst.findColumn(strClnNome) < 0)
      {
        return 0;
      }

      if (Utils.getBooStrVazia(strClnNome))
      {
        return 0;
      }

      return rst.getDouble(rst.findColumn(strClnNome));
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    return 0;
  }

  public static GregorianCalendar getDtt(ResultSet rst, String strClnNome)
  {
    if (rst == null)
    {
      return null;
    }

    if (Utils.getBooStrVazia(strClnNome))
    {
      return null;
    }

    try
    {
      if (rst.findColumn(strClnNome) < 0)
      {
        return null;
      }

      GregorianCalendar dttResultado = new GregorianCalendar();

      if (rst.getDate(strClnNome) == null)
      {
        return null;
      }

      dttResultado.setTime(rst.getDate(strClnNome));

      return dttResultado;
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }

  public static int getInt(ResultSet rst, String strClnNome)
  {
    if (rst == null)
    {
      return 0;
    }

    if (Utils.getBooStrVazia(strClnNome))
    {
      return 0;
    }

    try
    {
      if (rst.findColumn(strClnNome) < 0)
      {
        return 0;
      }

      return rst.getInt(rst.findColumn(strClnNome));
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return 0;
  }

  public static String getStr(ResultSet rst, String strClnNome)
  {
    if (rst == null)
    {
      return null;
    }

    if (Utils.getBooStrVazia(strClnNome))
    {
      return null;
    }

    try
    {
      if (rst.findColumn(strClnNome) < 0)
      {
        return null;
      }

      return rst.getString(rst.findColumn(strClnNome));
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    return null;
  }
}