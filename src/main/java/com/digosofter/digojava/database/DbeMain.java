package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class DbeMain extends Objeto
{
  private List<TabelaMain> _lstTbl;

  protected DbeMain()
  {
  }

  public abstract void execSql(String sql);

  public boolean execSqlBoo(String sql)
  {
    if (Utils.getBooStrVazia(sql))
    {
      return false;
    }

    return Utils.getBoo(this.execSqlStr(sql));
  }

  public double execSqlDbl(String sql)
  {
    if (Utils.getBooStrVazia(sql))
    {
      return 0;
    }

    String strResultado = this.execSqlStr(sql);

    if (Utils.getBooStrVazia(strResultado))
    {
      return 0;
    }

    return Double.valueOf(strResultado);
  }

  public int execSqlInt(String sql)
  {
    return (int) this.execSqlDbl(sql);
  }

  public abstract String execSqlStr(String sql);

  /**
   * @return Retorna a lista de tabelas deste banco de dados.
   */
  public List<TabelaMain> getLstTbl()
  {
    if (_lstTbl != null)
    {
      return _lstTbl;
    }

    _lstTbl = new ArrayList<>();

    this.inicializarLstTbl(_lstTbl);

    return _lstTbl;
  }

  /**
   * Retorna a tabela que tem o id de objeto igual ao parâmetro @param intTabelaObjetoId.
   *
   * @param intTabelaObjetoId Código do objeto da tabela que se deseja retornar.
   */
  public TabelaMain getTbl(final int intTabelaObjetoId)
  {
    if (intTabelaObjetoId < 0)
    {
      return null;
    }

    for (TabelaMain tbl : this.getLstTbl())
    {
      TabelaMain tblResultado = tbl.getTbl(intTabelaObjetoId);

      if (tblResultado != null)
      {
        return tblResultado;
      }
    }

    return null;
  }

  protected void inicializar()
  {
    for (TabelaMain tbl : this.getLstTbl())
    {
      tbl.iniciar(this);
    }
  }

  protected abstract void inicializarLstTbl(final List<TabelaMain> lstTbl);

  public void iniciar()
  {
    this.inicializar();
  }
}