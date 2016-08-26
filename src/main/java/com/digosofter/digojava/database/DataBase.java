package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;

public abstract class DataBase extends Objeto
{
  public abstract void execSql(String sql);

  public boolean execSqlGetBoo(String sql)
  {
    if (Utils.getBooStrVazia(sql))
    {
      return false;
    }

    return Utils.getBoo(this.execSqlGetStr(sql));
  }

  public double execSqlGetDbl(String sql)
  {
    if (Utils.getBooStrVazia(sql))
    {
      return 0;
    }

    String strResultado = this.execSqlGetStr(sql);

    if (Utils.getBooStrVazia(strResultado))
    {
      return 0;
    }

    return Double.valueOf(strResultado);
  }

  public int execSqlGetInt(String sql)
  {
    return (int) this.execSqlGetDbl(sql);
  }

  public abstract String execSqlGetStr(String sql);
}