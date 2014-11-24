package com.digosofter.digojava.database;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public class DataBase extends Objeto {

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

  public String execSqlGetStr(String sql) {

    String strResultado = Utils.STR_VAZIA;

    try {

      // TODO: Not implemented.

    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return strResultado;
  }
}
