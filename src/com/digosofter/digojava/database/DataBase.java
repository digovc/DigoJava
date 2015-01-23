package com.digosofter.digojava.database;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class DataBase extends Objeto {

  public boolean execSqlGetBoo(String sql) {

    String str;

    try {

      str = this.execSqlGetStr(sql);

      if (Utils.getBooStrVazia(str)) {

        return false;
      }

      switch (str.toLowerCase()) {
        case "true":
        case "t":
        case "sim":
        case "s":
        case "1":
          return true;

        default:
          return false;
      }
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return false;
  }

  public double execSqlGetDbl(String sql) {

    double dblResultado = 0;
    String str;

    try {

      str = this.execSqlGetStr(sql);
      str = !Utils.getBooStrVazia(str) ? str : "0";

      dblResultado = Double.valueOf(str);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return dblResultado;
  }

  public int execSqlGetInt(String sql) {

    return (int) this.execSqlGetDbl(sql);
  }

  public abstract String execSqlGetStr(String sql);
}