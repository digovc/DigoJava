package com.digosofter.digojava.database;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class DataBase extends Objeto {

  public abstract void execSql(String sql);

  public boolean execSqlGetBoo(String sql) {

    String str;

    try {

      str = this.execSqlGetStr(sql);

      if (Utils.getBooStrVazia(str)) {

        return false;
      }

      switch (str.toLowerCase()) {

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

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return false;
  }

  public double execSqlGetDbl(String sql) {

    String str;

    try {

      str = this.execSqlGetStr(sql);
      str = !Utils.getBooStrVazia(str) ? str : "0";

      return Double.valueOf(str);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return 0;
  }

  public int execSqlGetInt(String sql) {

    return (int) this.execSqlGetDbl(sql);
  }

  public abstract String execSqlGetStr(String sql);
}