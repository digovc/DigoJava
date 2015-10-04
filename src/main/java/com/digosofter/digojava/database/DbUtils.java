package com.digosofter.digojava.database;

import java.sql.ResultSet;
import java.util.GregorianCalendar;

import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class DbUtils {

  public static boolean getBoo(ResultSet rst, String strClnNome) {

    String strValor;

    try {

      if (rst == null) {

        return false;
      }

      if (rst.findColumn(strClnNome) < 0) {

        return false;
      }

      if (Utils.getBooStrVazia(strClnNome)) {

        return false;
      }

      strValor = rst.getString(rst.findColumn(strClnNome));

      if (Utils.getBooStrVazia(strValor)) {

        return false;
      }

      switch (strValor.toLowerCase(Utils.LOCAL_BRASIL)) {

        case "1":
        case "s":
        case "sim":
        case "t":
        case "true":
          return true;

        default:
          return false;
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return false;
  }

  public static double getDbl(ResultSet rst, String strClnNome) {

    try {

      if (rst == null) {

        return 0;
      }

      if (rst.findColumn(strClnNome) < 0) {

        return 0;
      }

      if (Utils.getBooStrVazia(strClnNome)) {

        return 0;
      }

      return rst.getDouble(rst.findColumn(strClnNome));
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return 0;
  }

  public static GregorianCalendar getDtt(ResultSet rst, String strClnNome) {

    GregorianCalendar dttResultado;

    try {

      if (rst == null) {

        return null;
      }

      if (rst.findColumn(strClnNome) < 0) {

        return null;
      }

      if (Utils.getBooStrVazia(strClnNome)) {

        return null;
      }

      dttResultado = new GregorianCalendar();

      if (rst.getDate(strClnNome) == null) {

        return null;
      }

      dttResultado.setTime(rst.getDate(strClnNome));

      return dttResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public static int getInt(ResultSet rst, String strClnNome) {

    try {

      if (rst == null) {

        return 0;
      }

      if (rst.findColumn(strClnNome) < 0) {

        return 0;
      }

      if (Utils.getBooStrVazia(strClnNome)) {

        return 0;
      }

      return rst.getInt(rst.findColumn(strClnNome));
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return 0;
  }

  public static String getStr(ResultSet rst, String strClnNome) {

    try {

      if (rst == null) {

        return null;
      }

      if (rst.findColumn(strClnNome) < 0) {

        return null;
      }

      if (Utils.getBooStrVazia(strClnNome)) {

        return null;
      }

      return rst.getString(rst.findColumn(strClnNome));
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }
}