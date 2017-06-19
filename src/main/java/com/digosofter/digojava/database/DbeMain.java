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
    this.iniciar();
  }

  public void addTbl(TabelaMain<?> tbl)
  {
    if (tbl == null)
    {
      return;
    }

    if (this.getLstTbl().contains(tbl))
    {
      return;
    }

    this.getLstTbl().add(tbl);
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

    return _lstTbl;
  }

  /**
   * Retorna a tabela que tem o id de objeto igual ao parâmetro @param intTblObjetoId.
   *
   * @param intTblObjetoId Código do objeto da tabela que se deseja retornar.
   */
  public TabelaMain getTbl(int intTblObjetoId)
  {
    if (intTblObjetoId < 0)
    {
      return null;
    }

    for (TabelaMain tbl : this.getLstTbl())
    {
      if (tbl == null)
      {
        continue;
      }

      if (tbl.getIntObjetoId() != intTblObjetoId)
      {
        continue;
      }

      return tbl;
    }

    return null;
  }

  protected void inicializar()
  {
  }

  private void iniciar()
  {
    this.inicializar();
  }
}