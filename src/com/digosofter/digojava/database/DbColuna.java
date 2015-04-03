package com.digosofter.digojava.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public class DbColuna extends Objeto {

  public static enum EnmTipo {

    BIGINT,
    BIGSERIAL,
    BOOLEAN,
    CHAR,
    DATE,
    DATE_TIME,
    DECIMAL,
    DOUBLE,
    INTEGER,
    INTERVAL,
    MONEY,
    NUMERIC,
    REAL,
    SERIAL,
    SMALLINT,
    TEXT,
    TIME_WITH_TIME_ZONE,
    TIME_WITHOUT_TIME_ZONE,
    TIMESTAMP_WITH_TIME_ZONE,
    TIMESTAMP_WITHOUT_TIME_ZONE,
    VARCHAR,
    XML
  }

  public static enum EnmTipoGrupo {

    ALPHANUMERICO,
    NUMERICO,
    TEMPORAL
  }

  private boolean _booChavePrimaria;
  private boolean _booClnNome;
  private boolean _booMonetario;
  private boolean _booNotNull;
  private boolean _booOrdem;
  private boolean _booOrdemDecrecente;
  private boolean _booPercentual;
  private boolean _booSenha;
  private boolean _booVisivelCadastro = true;
  private boolean _booVisivelConsulta;
  private DbColuna _clnRef;
  private EnmTipo _enmTipo;
  private EnmTipoGrupo _enmTipoGrupo;
  private int _intFrmLinha = 1;
  private int _intFrmLinhaPeso = 1;
  private int _intOrdem;
  private int _intTamanhoCampo = 100;
  private List<Integer> _lstIntOpcaoValor;
  private List<String> _lstStrOpcao;
  private String _sqlSubSelectColunaRef;
  private String _sqlTipo;
  private String _strGrupoNome;
  private String _strNomeSql;
  private String _strTblNomeClnNome;
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

  /**
   * Adiciona uma opção para lista, tornando o campo selecionável por combobox.
   */
  public void adicionarOpcao(int intValor, String strNome) {

    try {

      this.getLstIntOpcaoValor().add(intValor);
      this.getLstStrOpcao().add(strNome);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
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

  private boolean getBooMonetario() {

    return _booMonetario;
  }

  public boolean getBooNotNull() {

    return _booNotNull;
  }

  public boolean getBooOrdem() {

    return _booOrdem;
  }

  public boolean getBooOrdemDecrecente() {

    return _booOrdemDecrecente;
  }

  private boolean getBooPercentual() {

    return _booPercentual;
  }

  public boolean getBooSenha() {

    return _booSenha;
  }

  public boolean getBooValor() {

    try {

      switch (this.getStrValor().toLowerCase()) {
        case "true":
        case "t":
        case "sim":
        case "s":
        case "1":
          return true;
        default:
          return false;
      }
    }
    catch (Exception ex) {

      return false;
    }
    finally {
    }
  }

  public boolean getBooVisivelCadastro() {

    return _booVisivelCadastro;
  }

  public boolean getBooVisivelConsulta() {

    return _booVisivelConsulta;
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

  protected EnmTipoGrupo getEnmTipoGrupo() {

    try {

      // TODO: Completar com o restante dos tipos.
      switch (this.getEnmTipo()) {
        case INTEGER:
        case INTERVAL:
        case BIGINT:
        case BIGSERIAL:
        case MONEY:
        case DECIMAL:
        case DOUBLE:
        case NUMERIC:
        case SMALLINT:
        case REAL:
        case SERIAL:
          _enmTipoGrupo = EnmTipoGrupo.NUMERICO;
          break;
        case TIME_WITH_TIME_ZONE:
        case TIME_WITHOUT_TIME_ZONE:
        case TIMESTAMP_WITH_TIME_ZONE:
        case TIMESTAMP_WITHOUT_TIME_ZONE:
        case DATE:
        case DATE_TIME:
          _enmTipoGrupo = EnmTipoGrupo.TEMPORAL;
          break;
        default:
          _enmTipoGrupo = EnmTipoGrupo.ALPHANUMERICO;
          break;
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _enmTipoGrupo;
  }

  public int getIntFrmLinha() {

    return _intFrmLinha;
  }

  public int getIntFrmLinhaPeso() {

    return _intFrmLinhaPeso;
  }

  public int getIntOrdem() {

    return _intOrdem;
  }

  public int getIntTamanhoCampo() {

    return _intTamanhoCampo;
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

  protected List<Integer> getLstIntOpcaoValor() {

    try {

      if (_lstIntOpcaoValor != null) {

        return _lstIntOpcaoValor;
      }

      _lstIntOpcaoValor = new ArrayList<Integer>();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstIntOpcaoValor;
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

  public String getSqlSubSelectColunaRef() {

    try {

      if (_sqlSubSelectColunaRef != null) {

        return _sqlSubSelectColunaRef;
      }

      if (this.getClnRef() == null) {

        return Utils.STR_VAZIA;
      }

      _sqlSubSelectColunaRef = "(select _tbl_ref_nome._cln_ref_nome from _tbl_ref_nome where _tbl_ref_nome._cln_ref_pk = _tbl_nome._cln_nome) _cln_nome, ";

      _sqlSubSelectColunaRef = _sqlSubSelectColunaRef.replace("_tbl_ref_nome", this.getClnRef().getTbl().getStrNomeSql());
      _sqlSubSelectColunaRef = _sqlSubSelectColunaRef.replace("_cln_ref_nome", this.getClnRef().getTbl().getClnNome().getStrNomeSql());
      _sqlSubSelectColunaRef = _sqlSubSelectColunaRef.replace("_cln_ref_pk", this.getClnRef().getTbl().getClnChavePrimaria().getStrNomeSql());
      _sqlSubSelectColunaRef = _sqlSubSelectColunaRef.replace("_tbl_nome", this.getTbl().getStrNomeSql());
      _sqlSubSelectColunaRef = _sqlSubSelectColunaRef.replace("_cln_nome", this.getStrNomeSql());
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _sqlSubSelectColunaRef;
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

  public String getStrGrupoNome() {

    return _strGrupoNome;
  }

  public String getStrNomeSql() {

    try {

      if (!Utils.getBooStrVazia(_strNomeSql)) {

        return _strNomeSql;
      }

      _strNomeSql = this.getStrNomeSimplificado();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strNomeSql;
  }

  public String getStrTblNomeClnNome() {

    try {

      if (_strTblNomeClnNome != null) {

        return _strTblNomeClnNome;
      }

      _strTblNomeClnNome = "_tbl_nome._cln_nome, ";

      _strTblNomeClnNome = _strTblNomeClnNome.replace("_tbl_nome", this.getTbl().getStrNomeSql());
      _strTblNomeClnNome = _strTblNomeClnNome.replace("_cln_nome", this.getStrNomeSql());
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strTblNomeClnNome;
  }

  public String getStrValor() {

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
          _strValorExibicao = Utils.getStrDataFormatada(this.getDttValor(), Utils.EnmDataFormato.DD_MM_YYYY);
          break;
        case NUMERIC:
        case REAL:
          _strValorExibicao = this.getStrValorExibicaoNumeric();
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

  private String getStrValorExibicaoNumeric() {

    try {

      if (Utils.getBooStrVazia(this.getStrValor())) {

        return "0,00";
      }

      if (this.getBooMonetario()) {

        return this.getStrValorMonetario();
      }

      if (this.getBooPercentual()) {

        return this.getStrValorPercentual();
      }

      return this.getStrValor().replace(".", ",");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return "0,00";
  }

  public String getStrValorFormatado(String strValor) {

    String strResultado = "";

    try {

      switch (this.getEnmTipoGrupo()) {

        case ALPHANUMERICO:
          strResultado = strValor;
          break;

        case NUMERICO:
          strResultado = strValor;
          break;

        case TEMPORAL:
          strResultado = strValor;
          break;

        default:
          strResultado = strValor;
          break;
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return strResultado;
  }

  public String getStrValorMonetario() {

    try {

      if (Utils.getBooStrVazia(this.getStrValor())) {

        return "R$ 0,00";
      }

      return Utils.getStrValorMonetario(Double.parseDouble(this.getStrValor()));
    }
    catch (Exception ex) {

      return "R$ 0,00";
    }
    finally {
    }
  }

  private String getStrValorPercentual() {

    try {

      return this.getStrValorMonetario().replace("R$ ", "") + " %";
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public String getStrValorSql() {

    try {

      if (Utils.getBooStrVazia(this.getStrValor())) {

        return null;
      }

      // TODO: Fazer validações para impedir "sql injection".
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

      _strValorSql = _strValorSql.replace("'", "''");
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

      _booChavePrimaria = booChavePrimaria;

      if (_booChavePrimaria) {

        this.getTbl().getClnChavePrimaria()._booChavePrimaria = false;
        this.getTbl().setClnChavePrimaria(this);
      }
      else {

        this.getTbl().setClnChavePrimaria(null);
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setBooClnNome(boolean booClnNome) {

    try {

      _booClnNome = booClnNome;

      if (_booClnNome) {

        this.getTbl().getClnNome()._booClnNome = false;
        this.getTbl().setClnNome(this);
      }
      else {

        this.getTbl().setClnNome(null);
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setBooMonetario(boolean booMonetario) {

    _booMonetario = booMonetario;
  }

  public void setBooNotNull(boolean booNotNull) {

    _booNotNull = booNotNull;
  }

  public void setBooOrdem(boolean booOrdem) {

    try {

      _booOrdem = booOrdem;

      if (_booOrdem) {

        this.getTbl().getClnOrdem()._booOrdem = false;
        this.getTbl().setClnOrdem(this);
      }
      else {

        this.getTbl().setClnOrdem(null);
      }
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

  public void setBooPercentual(boolean booPercentual) {

    _booPercentual = booPercentual;
  }

  public void setBooSenha(boolean booSenha) {

    _booSenha = booSenha;
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

  public void setBooVisivelConsulta(boolean booVisivelConsulta) {

    _booVisivelConsulta = booVisivelConsulta;
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

  public void setDttValor(Calendar dttValor) {

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

  public void setIntFrmLinha(int intFrmLinha) {

    _intFrmLinha = intFrmLinha;
  }

  public void setIntFrmLinhaPeso(int intFrmLinhaPeso) {

    _intFrmLinhaPeso = intFrmLinhaPeso;
  }

  public void setIntOrdem(int intOrdem) {

    _intOrdem = intOrdem;
  }

  public void setIntTamanhoCampo(int intTamanhoCampo) {

    _intTamanhoCampo = intTamanhoCampo;
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

  public void setStrGrupoNome(String strGrupoNome) {

    _strGrupoNome = strGrupoNome;
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

      if (_tbl.getLstCln().contains(this)) {

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
}