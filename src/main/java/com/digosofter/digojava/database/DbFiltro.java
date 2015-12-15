package com.digosofter.digojava.database;

import java.util.GregorianCalendar;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.Utils.EnmDataFormato;
import com.digosofter.digojava.erro.Erro;

public class DbFiltro extends Objeto {

  public enum EnmCondicao {

    AND,
    IS,
    IS_NOT,
    OR,
  }

  public enum EnmOperador {

    DIFERENTE,
    IGUAL,
    IS_NOT_NULL,
    IS_NULL,
    MAIOR,
    MAIOR_IGUAL,
    MENOR,
    MENOR_IGUAL,
  }

  private boolean _booSelect;
  private DbColuna _clnFiltro;
  private EnmCondicao _enmCondicao = EnmCondicao.AND;
  private EnmOperador _enmOperador = EnmOperador.IGUAL;
  private String _sqlFiltro;
  private String _strFiltro;
  private String _strOperador;

  public DbFiltro(DbColuna clnFiltro, EnmOperador enmOperador, int intFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setEnmOperador(enmOperador);
      this.setStrFiltro(String.valueOf(intFiltro));
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    } finally {
    }
  }

  public DbFiltro(DbColuna clnFiltro, EnmOperador enmOperador, String strFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setEnmOperador(enmOperador);
      this.setStrFiltro(strFiltro);
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    } finally {
    }
  }

  public DbFiltro(DbColuna clnFiltro, GregorianCalendar dttFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(Utils.getStrDataFormatada(dttFiltro, EnmDataFormato.YYYY_MM_DD_HH_MM_SS));
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    } finally {
    }
  }

  public DbFiltro(DbColuna clnFiltro, int intFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(String.valueOf(intFiltro));
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    } finally {
    }
  }

  public DbFiltro(DbColuna clnFiltro, String strFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(strFiltro);
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    } finally {
    }
  }

  public DbFiltro(String strSubSelect) {

    try {

      this.setBooSelect(true);
      this.setStrFiltro(strSubSelect);
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    } finally {
    }
  }

  public DbFiltro(DbColuna clnFiltro, boolean booFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(booFiltro ? "1" : "0");
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    } finally {
    }
  }

  private boolean getBooSelect() {

    return _booSelect;
  }

  private DbColuna getClnFiltro() {

    return _clnFiltro;
  }

  private EnmCondicao getEnmCondicao() {

    return _enmCondicao;
  }

  private EnmOperador getEnmOperador() {

    return _enmOperador;
  }

  /**
   * Retorna string com o filtro formatado para uso em sql's.
   */
  public String getSqlFiltro(boolean booPrimeiroTermo) {

    try {

      if (!Utils.getBooStrVazia(_sqlFiltro)) {

        return _sqlFiltro;
      }

      if (this.getBooSelect()) {

        _sqlFiltro = this.getSqlFiltroSelect(booPrimeiroTermo);

        return _sqlFiltro;
      }

      _sqlFiltro = this.getSqlFiltroNaoSelect(booPrimeiroTermo);

      return _sqlFiltro;
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    } finally {
    }

    return null;
  }

  private String getSqlFiltroNaoSelect(boolean booPrimeiroTermo) {

    String strResultado;

    try {

      strResultado = "_condicao _tbl_nome._cln_nome _operador '_filtro'";

      strResultado = strResultado.replace("_condicao", !booPrimeiroTermo ? this.getStrCondicao() : Utils.STR_VAZIA);
      strResultado = strResultado.replace("_tbl_nome", this.getClnFiltro().getTbl().getStrNomeSql());
      strResultado = strResultado.replace("_cln_nome", this.getClnFiltro().getStrNomeSql());
      strResultado = strResultado.replace("_operador", this.getStrOperador());
      strResultado = strResultado.replace("_filtro", this.getStrFiltro());
      strResultado = booPrimeiroTermo ? strResultado.substring(1) : strResultado;

      return strResultado;
    } catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);

    } finally {
    }

    return null;
  }

  private String getSqlFiltroSelect(boolean booPrimeiroTermo) {

    String strResultado;

    try {

      strResultado = "_condicao (_sub_select)";
      strResultado = strResultado.replace("_condicao", !booPrimeiroTermo ? this.getStrCondicao() : Utils.STR_VAZIA);
      strResultado = strResultado.replace("_sub_select", this.getStrFiltro());
      strResultado = booPrimeiroTermo ? strResultado.substring(1) : strResultado;

      return strResultado;
    } catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);

    } finally {
    }

    return null;
  }

  private String getStrCondicao() {

    try {

      switch (this.getEnmCondicao()) {

        case IS:
          return "is";

        case IS_NOT:
          return "is not";

        case OR:
          return "or";

        default:
          return "and";
      }
    } catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    } finally {
    }

    return null;
  }

  private String getStrFiltro() {

    return _strFiltro;
  }

  private String getStrOperador() {

    try {

      if (!Utils.getBooStrVazia(_strOperador)) {

        return _strOperador;
      }

      switch (this.getEnmOperador()) {

        case DIFERENTE:
          _strOperador = "<>";
          return _strOperador;

        case IS_NOT_NULL:
          _strOperador = "is not null";
          return _strOperador;

        case IS_NULL:
          _strOperador = "is null";
          return _strOperador;

        case MAIOR:
          _strOperador = ">";
          return _strOperador;

        case MAIOR_IGUAL:
          _strOperador = ">=";
          return _strOperador;

        case MENOR:
          _strOperador = "<";
          return _strOperador;

        case MENOR_IGUAL:
          _strOperador = "<=";
          return _strOperador;

        default:
          _strOperador = "=";
          return _strOperador;
      }
    } catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    } finally {
    }

    return _strOperador;
  }

  private void setBooSelect(boolean booSelect) {

    _booSelect = booSelect;
  }

  private void setClnFiltro(DbColuna clnFiltro) {

    _clnFiltro = clnFiltro;
  }

  public void setEnmCondicao(EnmCondicao enmCondicao) {

    _enmCondicao = enmCondicao;
  }

  public void setEnmOperador(EnmOperador enmOperador) {

    _enmOperador = enmOperador;
  }

  private void setStrFiltro(String strFiltro) {

    _strFiltro = strFiltro;
  }
}