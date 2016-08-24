package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;

public abstract class DataBase extends Objeto
{
  public abstract void execSql(String sql);

  public boolean execSqlGetBoo(String sql)
  {
    String str;

    str = this.execSqlGetStr(sql);
    if (Utils.getBooStrVazia(str))
    {
      return false;
    }
    switch (str.toLowerCase())
    {
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

  public double execSqlGetDbl(String sql)
  {
    String str;

    str = this.execSqlGetStr(sql);
    str = !Utils.getBooStrVazia(str) ? str : "0";

    return Double.valueOf(str);
  }

  public int execSqlGetInt(String sql)
  {
    return (int) this.execSqlGetDbl(sql);
  }

  public abstract String execSqlGetStr(String sql);
}