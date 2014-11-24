package com.digosofter.digojava.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public class DbColuna extends Objeto {

  public enum EnmTipo {
    BOOLEAN,
    DATE_TIME,
    INTEGER,
    NUMERIC,
    REAL,
    TEXT
  }

  private boolean _booChavePrimaria;
  private boolean _booClnNome;
  private boolean _booOrdemCadastro;
  private boolean _booOrdemDecrecente;
  private boolean _booVisivelCadastro;
  private DbColuna _clnRef;
  private EnmTipo _enmTipo;
  private int _intOrdem;
  private List<String> _lstStrOpcao;
  private String _sqlTipo;
  private String _strValor;
  private String _strValorDefault;
  private String _strValorExibicao;
  private String _strValorSql;
  private DbTabela _tbl;

  public DbColuna(String strNome, DbTabela tbl, EnmTipo enmTipo) {

    try {

      this.setStrNome(strNome);
      this.setTbl(tbl);
      this.setEnmTipo(enmTipo);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(120), ex);
    }
    finally {
    }
  }

  public boolean getBooChavePrimaria() {

    return _booChavePrimaria;
  }

  public boolean getBooClnNome() {

    return _booClnNome;
  }

  public boolean getBooOrdemCadastro() {

    return _booOrdemCadastro;
  }

  public boolean getBooOrdemDecrecente() {

    return _booOrdemDecrecente;
  }

  public boolean getBooValor() {

    boolean booResultado;

    try {

      booResultado = Boolean.parseBoolean(this.getStrValor());
    }
    catch (Exception ex) {
      return false;
    }
    finally {
    }

    return booResultado;
  }

  public boolean getBooVisivelCadastro() {

    return _booVisivelCadastro;
  }

  public char getChrValor() {

    char chrResultado = 0;

    try {

      chrResultado = this.getStrValor().charAt(0);
    }
    catch (Exception ex) {
      return 0;
    }
    finally {
    }

    return chrResultado;
  }

  public DbColuna getClnRef() {

    return _clnRef;
  }

  public double getDblValor() {

    double dlbResultado;

    try {

      dlbResultado = Double.parseDouble(this.getStrValor());
    }
    catch (Exception ex) {
      return 0;
    }
    finally {
    }

    return dlbResultado;
  }

  public GregorianCalendar getDttValor() {

    GregorianCalendar dttResultado = null;

    int intAno;
    int intDia;
    int intHora = 0;
    int intMes;
    int intMin = 0;
    int intSeg = 0;

    try {

      if (Utils.getBooStrVazia(this.getStrValor())) {
        return null;
      }

      intAno = Integer.parseInt(this.getStrValor().substring(0, 4));
      intMes = Integer.parseInt(this.getStrValor().substring(5, 7));
      intDia = Integer.parseInt(this.getStrValor().substring(8, 10));

      try {
        intHora = Integer.parseInt(this.getStrValor().substring(11, 13));
      }
      catch (Exception e) {
      }

      try {
        intMin = Integer.parseInt(this.getStrValor().substring(14, 16));
      }
      catch (Exception e) {
      }

      try {
        intSeg = Integer.parseInt(this.getStrValor().substring(17, 19));
      }
      catch (Exception e) {
      }

      dttResultado = new GregorianCalendar(intAno, intMes - 1, intDia, intHora, intMin, intSeg);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return dttResultado;
  }

  public EnmTipo getEnmTipo() {

    try {

      if (_enmTipo != null) {
        return _enmTipo;
      }

      _enmTipo = EnmTipo.TEXT;
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _enmTipo;
  }

  public int getIntOrdem() {

    return _intOrdem;
  }

  public int getIntValor() {

    int intResultado;

    try {

      intResultado = Integer.parseInt(this.getStrValor());
    }
    catch (Exception ex) {
      return 0;
    }
    finally {
    }

    return intResultado;
  }

  public List<String> getLstStrOpcao() {

    try {

      if (_lstStrOpcao != null) {
        return _lstStrOpcao;
      }

      _lstStrOpcao = new ArrayList<String>();
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _lstStrOpcao;
  }

  public String getSqlTipo() {

    try {

      if (!Utils.getBooStrVazia(_sqlTipo)) {
        return _sqlTipo;
      }

      switch (this.getEnmTipo()) {
        case BOOLEAN:
          _sqlTipo = "integer";
          break;
        case DATE_TIME:
          _sqlTipo = "text";
          break;
        case INTEGER:
          _sqlTipo = "integer";
          break;
        case NUMERIC:
          _sqlTipo = "numeric";
          break;
        case REAL:
          _sqlTipo = "real";
          break;
        case TEXT:
          _sqlTipo = "text";
          break;
        default:
          _sqlTipo = "text";
      }
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _sqlTipo;
  }

  public String getStrValor() {

    try {

      if (Utils.getBooStrVazia(_strValor)) {
        _strValor = Utils.STR_VAZIA;
      }
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _strValor;
  }

  public String getStrValorDefault() {

    try {

      if (Utils.getBooStrVazia(_strValorDefault)) {
        _strValorDefault = Utils.STR_VAZIA;
      }
    }
    catch (Exception ex) {
      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strValorDefault;
  }

  public String getStrValorExibicao() {

    try {

      switch (this.getEnmTipo()) {
        case BOOLEAN:
          _strValorExibicao = this.getBooValor() ? "Sim" : "Não";
          break;
        case DATE_TIME:
          _strValorExibicao = Utils.getStrDataFormatada(this.getDttValor().getTime(), Utils.EnmDataFormato.DD_MM_YYYY);
          break;
        case INTEGER:
          _strValorExibicao = this.getStrValor();
          break;
        case NUMERIC:
          _strValorExibicao = this.getStrValor();
          break;
        case REAL:
          _strValorExibicao = this.getStrValor();
          break;
        case TEXT:
          _strValorExibicao = this.getStrValor();
          break;
        default:
          _strValorExibicao = this.getStrValor();
          break;
      }
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _strValorExibicao;
  }

  public String getStrValorMonetario() {

    String strResultado = null;

    try {

      strResultado = Utils.getStrValorMonetario(Double.parseDouble(_strValor));
    }
    catch (Exception ex) {
      return "R$ 0,00";
    }
    finally {
    }

    return strResultado;
  }

  public String getStrValorSql() {

    try {

      switch (this.getEnmTipo()) {
        case BOOLEAN:
          _strValorSql = this.getStrValorSqlBoolean();
          break;
        case DATE_TIME:
          _strValorSql = this.getStrValor();
          break;
        case INTEGER:
          _strValorSql = this.getStrValor();
          break;
        case NUMERIC:
          _strValorSql = this.getStrValor();
          break;
        case REAL:
          _strValorSql = this.getStrValor();
          break;
        case TEXT:
          _strValorSql = this.getStrValor();
          break;
        default:
          _strValorSql = this.getStrValor();
          break;
      }

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);

    }
    finally {
    }

    return _strValorSql;
  }

  private String getStrValorSqlBoolean() {

    String strResultado = null;

    try {

      if (Utils.getBooStrVazia(this.getStrValor())) {
        return "0";
      }

      switch (this.getStrValor().toLowerCase(Locale.ROOT)) {
        case "true":
        case "t":
        case "sim":
        case "1":
          return "1";
        default:
          return "0";
      }

    }
    catch (Exception ex) {
      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return strResultado;
  }

  public DbTabela getTbl() {

    return _tbl;
  }

  public void setBooChavePrimaria(boolean booChavePrimaria) {

    try {

      if (booChavePrimaria) {

        for (DbColuna cln : this.getTbl().getLstCln()) {
          cln._booChavePrimaria = false;
        }

        this.getTbl().setClnChavePrimaria(this);
      }

      _booChavePrimaria = booChavePrimaria;
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setBooClnNome(boolean booClnNome) {

    try {

      if (booClnNome) {

        for (DbColuna cln : this.getTbl().getLstCln()) {
          cln._booClnNome = false;
        }

        this.getTbl().setClnNome(this);
      }

      _booClnNome = booClnNome;
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setBooOrdemCadastro(boolean booOrdemCadastro) {

    try {

      if (booOrdemCadastro) {

        for (DbColuna cln : this.getTbl().getLstCln()) {
          cln._booOrdemCadastro = false;
        }

        this.getTbl().setClnOrdemCadastro(this);
      }
      _booOrdemCadastro = booOrdemCadastro;
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setBooOrdemDecrecente(boolean booOrdemDecrecente) {

    _booOrdemDecrecente = booOrdemDecrecente;
  }

  public void setBooValor(Boolean booValor) {

    try {

      this.setStrValor(String.valueOf(booValor));
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setBooVisivelCadastro(boolean booVisivelCadastro) {

    _booVisivelCadastro = booVisivelCadastro;
  }

  public void setChrValor(char chrValor) {

    try {

      this.setStrValor(String.valueOf(chrValor));
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setClnRef(DbColuna clnRef) {

    _clnRef = clnRef;
  }

  public void setDblValor(double dblValor) {

    try {

      this.setStrValor(String.valueOf(dblValor));
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setDttValor(Date dttValor) {

    try {

      if (dttValor == null) {
        this.setStrValor(Utils.STR_VAZIA);
        return;
      }

      this.setStrValor(Utils.getStrDataFormatada(dttValor, Utils.EnmDataFormato.YYYY_MM_DD_HH_MM_SS));
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  private void setEnmTipo(EnmTipo enmTipo) {

    _enmTipo = enmTipo;
  }

  public void setIntOrdem(int intOrdem) {

    _intOrdem = intOrdem;
  }

  public void setIntValor(int intValor) {

    try {

      this.setStrValor(String.valueOf(intValor));
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setLstStrOpcao(List<String> lstStrOpcao) {

    _lstStrOpcao = lstStrOpcao;
  }

  public void setStrValor(String strValor) {

    _strValor = strValor;
  }

  public void setStrValorDefault(String strValorDefault) {

    _strValorDefault = strValorDefault;
  }

  public void setTbl(DbTabela tbl) {

    try {

      _tbl = tbl;

      if (_tbl == null) {
        return;
      }

      _tbl.getLstCln().add(this);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  @Override
  public String toString() {

    return this.getStrValor();
  }
}
