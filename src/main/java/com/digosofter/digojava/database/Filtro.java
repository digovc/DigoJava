package com.digosofter.digojava.database;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.Utils.EnmDataFormato;
import com.digosofter.digojava.erro.Erro;

import java.util.GregorianCalendar;

public class Filtro extends Objeto {

  public enum EnmCondicao {

    AND,
    IS,
    IS_NOT,
    OR,
  }

  public enum EnmOperador {

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

  public Filtro(Coluna clnFiltro, EnmOperador enmOperador, int intFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setEnmOperador(enmOperador);
      this.setStrFiltro(String.valueOf(intFiltro));
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public Filtro(Coluna clnFiltro, EnmOperador enmOperador, String strFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setEnmOperador(enmOperador);
      this.setStrFiltro(strFiltro);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public Filtro(Coluna clnFiltro, GregorianCalendar dttFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(Utils.getStrDataFormatada(dttFiltro, EnmDataFormato.YYYY_MM_DD_HH_MM_SS));
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    }
    finally {
    }
  }

  public Filtro(Coluna clnFiltro, int intFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(String.valueOf(intFiltro));
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    }
    finally {
    }
  }

  public Filtro(Coluna clnFiltro, String strFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(strFiltro);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    }
    finally {
    }
  }

  public Filtro(String strSubSelect) {

    try {

      this.setBooSelect(true);
      this.setStrFiltro(strSubSelect);
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    }
    finally {
    }
  }

  public Filtro(Coluna clnFiltro, boolean booFiltro) {

    try {

      this.setClnFiltro(clnFiltro);
      this.setStrFiltro(booFiltro ? "1" : "0");
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(121), ex);
    }
    finally {
    }
  }

  private boolean getBooSelect() {

    return _booSelect;
  }

  private Coluna getClnFiltro() {

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
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return null;
  }

  private String getSqlFiltroNaoSelect(boolean booPrimeiroTermo) {

    String strResultado;

    try {

      strResultado = "_condicao _tbl_nome._cln_nome _operador _filtro";

      strResultado = strResultado.replace("_condicao", !booPrimeiroTermo ? this.getStrCondicao() : Utils.STR_VAZIA);
      strResultado = strResultado.replace("_tbl_nome", this.getClnFiltro().getTbl().getSqlNome());
      strResultado = strResultado.replace("_cln_nome", this.getClnFiltro().getSqlNome());
      strResultado = strResultado.replace("_operador", this.getStrOperador());
      strResultado = strResultado.replace("_filtro", this.getStrFiltroFormatado());

      strResultado = booPrimeiroTermo ? strResultado.substring(1) : strResultado;

      return strResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);

    }
    finally {
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
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);

    }
    finally {
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
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  private String getStrFiltro() {

    return _strFiltro;
  }

  private String getStrFiltroAspas() {

    try {

      if (Utils.getBooStrVazia(this.getStrFiltro())) {

        return "null";
      }

      return "'" + this.getStrFiltro() + "'";
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
    return null;
  }

  private String getStrFiltroFormatado() {

    try {

      if (_strFiltroFormatado != null) {

        return _strFiltroFormatado;
      }

      switch (this.getEnmOperador()) {

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
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strFiltroFormatado;
  }

  private String getStrFiltroFormatadoContem() {

    String strResultado;

    try {

      if (Utils.getBooStrVazia(this.getStrFiltro())) {

        return Utils.STR_VAZIA;
      }

      strResultado = "'%_filtro%'";

      strResultado = strResultado.replace("_filtro", this.getStrFiltro());

      return strResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  private String getStrFiltroFormatadoPrefixo() {

    String strResultado;

    try {

      if (Utils.getBooStrVazia(this.getStrFiltro())) {

        return Utils.STR_VAZIA;
      }

      strResultado = "'%_filtro'";

      strResultado = strResultado.replace("_filtro", this.getStrFiltro());

      return strResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  private String getStrFiltroFormatadoSufixo() {

    String strResultado;

    try {

      if (Utils.getBooStrVazia(this.getStrFiltro())) {

        return Utils.STR_VAZIA;
      }

      strResultado = "'_filtro%'";

      strResultado = strResultado.replace("_filtro", this.getStrFiltro());

      return strResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  private String getStrOperador() {

    try {

      if (!Utils.getBooStrVazia(_strOperador)) {

        return _strOperador;
      }

      switch (this.getEnmOperador()) {

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
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _strOperador;
  }

  private void setBooSelect(boolean booSelect) {

    _booSelect = booSelect;
  }

  private void setClnFiltro(Coluna clnFiltro) {

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