package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.Utils.EnmDataFormato;

import java.util.GregorianCalendar;

public class Filtro extends Objeto
{
  public enum EnmCondicao
  {
    AND,
    IS,
    IS_NOT,
    OR,
  }

  public enum EnmOperador
  {
    CONTEM,
    DIFERENTE,
    IGUAL,
    IS_NOT_NULL,
    IS_NULL,
    MAIOR,
    MAIOR_IGUAL,
    MENOR,
    MENOR_IGUAL,
    PREFIXO,
    SUFIXO,
  }

  private boolean _booSelect;
  private Coluna _clnFiltro;
  private EnmCondicao _enmCondicao = EnmCondicao.AND;
  private EnmOperador _enmOperador = EnmOperador.IGUAL;
  private String _sqlFiltro;
  private String _strFiltro;
  private String _strFiltroFormatado;
  private String _strOperador;

  public Filtro(Coluna clnFiltro, boolean booFiltro)
  {
    this(clnFiltro, (booFiltro ? "1" : "0"));
  }

  public Filtro(Coluna clnFiltro, int intFiltro, EnmOperador enmOperador)
  {
    this(clnFiltro, String.valueOf(intFiltro), enmOperador);
  }

  public Filtro(Coluna clnFiltro, String strFiltro, EnmOperador enmOperador)
  {
    this.setClnFiltro(clnFiltro);
    this.setEnmOperador(enmOperador);
    this.setStrFiltro(strFiltro);
  }

  public Filtro(Coluna clnFiltro, GregorianCalendar dttFiltro)
  {
    this(clnFiltro, (Utils.getStrDataFormatada(dttFiltro, EnmDataFormato.YYYY_MM_DD_HH_MM_SS)));
  }

  public Filtro(Coluna clnFiltro, int intFiltro)
  {
    this(clnFiltro, intFiltro, EnmOperador.IGUAL);
  }

  public Filtro(Coluna clnFiltro, String strFiltro)
  {
    this.setClnFiltro(clnFiltro);
    this.setStrFiltro(strFiltro);
  }

  public Filtro(String strSubSelect)
  {
    this.setBooSelect(true);
    this.setStrFiltro(strSubSelect);
  }

  private boolean getBooSelect()
  {
    return _booSelect;
  }

  private Coluna getClnFiltro()
  {
    return _clnFiltro;
  }

  private EnmCondicao getEnmCondicao()
  {
    return _enmCondicao;
  }

  private EnmOperador getEnmOperador()
  {
    return _enmOperador;
  }

  /**
   * Retorna string com o filtro formatado para uso em sql's.
   */
  public String getSqlFiltro(boolean booPrimeiroTermo)
  {
    if (_sqlFiltro != null)
    {
      return _sqlFiltro;
    }

    if (this.getBooSelect())
    {
      _sqlFiltro = this.getSqlFiltroSelect(booPrimeiroTermo);

      return _sqlFiltro;
    }

    _sqlFiltro = this.getSqlFiltroNaoSelect(booPrimeiroTermo);

    return _sqlFiltro;
  }

  private String getSqlFiltroNaoSelect(boolean booPrimeiroTermo)
  {
    String strResultado = "_condicao _tbl_nome._cln_nome _operador _filtro";

    strResultado = strResultado.replace("_condicao", !booPrimeiroTermo ? this.getStrCondicao() : Utils.STR_VAZIA);
    strResultado = strResultado.replace("_tbl_nome", this.getClnFiltro().getTbl().getSqlNome());
    strResultado = strResultado.replace("_cln_nome", this.getClnFiltro().getSqlNome());
    strResultado = strResultado.replace("_operador", this.getStrOperador());
    strResultado = strResultado.replace("_filtro", this.getStrFiltroFormatado());

    return booPrimeiroTermo ? strResultado.substring(1) : strResultado;
  }

  private String getSqlFiltroSelect(boolean booPrimeiroTermo)
  {
    String strResultado = "_condicao (_sub_select)";

    strResultado = strResultado.replace("_condicao", !booPrimeiroTermo ? this.getStrCondicao() : Utils.STR_VAZIA);
    strResultado = strResultado.replace("_sub_select", this.getStrFiltro());

    return booPrimeiroTermo ? strResultado.substring(1) : strResultado;
  }

  private String getStrCondicao()
  {
    switch (this.getEnmCondicao())
    {
      case IS:
        return "is";

      case IS_NOT:
        return "is not";

      case OR:
        return "or";

      default:
        return "and";
    }
  }

  private String getStrFiltro()
  {
    return _strFiltro;
  }

  private String getStrFiltroAspas()
  {
    if (Utils.getBooStrVazia(this.getStrFiltro()))
    {
      return "null";
    }

    return ("'" + this.getStrFiltro() + "'");
  }

  private String getStrFiltroFormatado()
  {
    if (_strFiltroFormatado != null)
    {
      return _strFiltroFormatado;
    }

    switch (this.getEnmOperador())
    {
      case CONTEM:
        return _strFiltroFormatado = this.getStrFiltroFormatadoContem();

      case PREFIXO:
        return _strFiltroFormatado = this.getStrFiltroFormatadoPrefixo();

      case SUFIXO:
        return _strFiltroFormatado = this.getStrFiltroFormatadoSufixo();

      case MAIOR:
      case MAIOR_IGUAL:
      case MENOR:
      case MENOR_IGUAL:
        return _strFiltroFormatado = this.getStrFiltro();

      default:
        return _strFiltroFormatado = this.getStrFiltroAspas();
    }
  }

  private String getStrFiltroFormatadoContem()
  {
    if (Utils.getBooStrVazia(this.getStrFiltro()))
    {
      return null;
    }

    String strResultado = "'%_filtro%'";

    return strResultado.replace("_filtro", this.getStrFiltro());
  }

  private String getStrFiltroFormatadoPrefixo()
  {
    if (Utils.getBooStrVazia(this.getStrFiltro()))
    {
      return null;
    }

    String strResultado = "'%_filtro'";

    return strResultado.replace("_filtro", this.getStrFiltro());
  }

  private String getStrFiltroFormatadoSufixo()
  {
    if (Utils.getBooStrVazia(this.getStrFiltro()))
    {
      return null;
    }

    String strResultado = "'_filtro%'";

    return strResultado.replace("_filtro", this.getStrFiltro());
  }

  private String getStrOperador()
  {
    if (_strOperador != null)
    {
      return _strOperador;
    }

    switch (this.getEnmOperador())
    {
      case CONTEM:
      case SUFIXO:
      case PREFIXO:
        return _strOperador = "LIKE";

      case DIFERENTE:
        return _strOperador = "<>";

      case IS_NOT_NULL:
        return _strOperador = "is not null";

      case IS_NULL:
        return _strOperador = "is null";

      case MAIOR:
        return _strOperador = ">";

      case MAIOR_IGUAL:
        return _strOperador = ">=";

      case MENOR:
        return _strOperador = "<";

      case MENOR_IGUAL:
        return _strOperador = "<=";

      default:
        return _strOperador = "=";
    }
  }

  private void setBooSelect(boolean booSelect)
  {
    _booSelect = booSelect;
  }

  private void setClnFiltro(Coluna clnFiltro)
  {
    _clnFiltro = clnFiltro;
  }

  public void setEnmCondicao(EnmCondicao enmCondicao)
  {
    _enmCondicao = enmCondicao;
  }

  public void setEnmOperador(EnmOperador enmOperador)
  {
    _enmOperador = enmOperador;
  }

  private void setStrFiltro(String strFiltro)
  {
    _strFiltro = strFiltro;
  }
}