package com.digosofter.digojava.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class DbTabela<T extends Dominio> extends Objeto {

  private boolean _booMenuAdicionar;
  private boolean _booMenuAlterar;
  private boolean _booMenuApagar;
  private DbColuna _clnChavePrimaria;
  private DbColuna _clnNome;
  private DbColuna _clnOrdem;
  private Class<T> _clsDominio;
  private int _intQtdLinha;
  private List<DbColuna> _lstCln;
  private List<DbColuna> _lstClnCadastro;
  private List<DbColuna> _lstClnConsulta;
  private List<DbColuna> _lstClnConsultaOrdenado;
  private List<DbColuna> _lstClnOrdenado;
  private List<OnChangeListener> _lstEvtOnChangeListener;
  private List<DbFiltro> _lstFilCadastro;
  private List<DbFiltro> _lstFilConsulta;
  private DataBase _objDb;
  private String _strNomeSql;
  private String _strPesquisa;

  protected DbTabela(String strNome, Class<T> clsDominio) {

    try {

      this.setClsDominio(clsDominio);
      this.setStrNome(strNome);
      this.inicializarColuna(-1);
      this.addAppLstTbl();

    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(122), ex);
    }
    finally {
    }
  }

  protected void addAppLstTbl() {

    try {

      App.getI().addTbl(this);

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public void addCln(DbColuna cln) {

    try {

      if (cln == null) {

        return;
      }

      if (!this.equals(cln.getTbl())) {

        return;
      }

      if (this.getLstCln().contains(cln)) {

        return;
      }

      this.getLstCln().add(cln);

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);

    }
    finally {
    }
  }

  public void addOnChangeListener(OnChangeListener evtOnChangeListener) {

    try {

      if (evtOnChangeListener == null) {

        return;
      }

      if (this.getLstEvtOnChangeListener().contains(evtOnChangeListener)) {

        return;
      }

      this.getLstEvtOnChangeListener().add(evtOnChangeListener);

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public void apagar(int intId) {

    OnChangeArg e;

    try {

      e = new OnChangeArg();

      e.setIntRegistroId(intId);

      this.dispararOnApagarReg(e);

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void dispararOnAdicionarReg(OnChangeArg e) {

    try {

      for (OnChangeListener evt : this.getLstEvtOnChangeListener()) {

        if (evt == null) {

          continue;
        }

        evt.onAdicionarReg(e);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void dispararOnApagarReg(OnChangeArg e) {

    try {

      for (OnChangeListener evt : this.getLstEvtOnChangeListener()) {

        if (evt == null) {

          continue;
        }

        evt.onApagarReg(e);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void dispararOnAtualizarReg(OnChangeArg e) {

    try {

      for (OnChangeListener evt : this.getLstEvtOnChangeListener()) {

        if (evt == null) {

          continue;
        }

        evt.onAtualizarReg(e);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public boolean getBooMenuAdicionar() {

    return _booMenuAdicionar;
  }

  public boolean getBooMenuAlterar() {

    return _booMenuAlterar;
  }

  public boolean getBooMenuApagar() {

    return _booMenuApagar;
  }

  public DbColuna getClnChavePrimaria() {

    try {

      if (_clnChavePrimaria != null) {

        return _clnChavePrimaria;
      }

      _clnChavePrimaria = this.getLstCln().get(0);
      _clnChavePrimaria.setBooChavePrimaria(true);

    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _clnChavePrimaria;
  }

  public DbColuna getClnNome() {

    try {

      if (_clnNome != null) {

        return _clnNome;
      }

      _clnNome = this.getClnChavePrimaria();
      _clnNome.setBooNome(true);

    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _clnNome;
  }

  public DbColuna getClnOrdem() {

    try {

      if (_clnOrdem != null) {

        return _clnOrdem;
      }

      _clnOrdem = this.getClnNome();
      _clnOrdem.setBooOrdem(true);

    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _clnOrdem;
  }

  protected Class<T> getClsDominio() {

    return _clsDominio;
  }

  public int getIntQtdLinha() {

    String sql;

    try {

      sql = "select count(1) from _tbl_nome;";
      sql = sql.replace("_tbl_nome", this.getStrNomeSql());

      _intQtdLinha = this.getObjDb().execSqlGetInt(sql);

    }
    catch (Exception ex) {

      _intQtdLinha = 0;
    }
    finally {
    }

    return _intQtdLinha;
  }

  public List<DbColuna> getLstCln() {

    try {

      if (_lstCln != null) {

        return _lstCln;
      }

      _lstClnOrdenado = null;

      _lstCln = new ArrayList<>();

    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _lstCln;
  }

  public List<DbColuna> getLstClnCadastro() {

    try {

      if (_lstClnCadastro != null) {

        return _lstClnCadastro;
      }

      _lstClnCadastro = new ArrayList<>();

      _lstClnCadastro.add(this.getClnNome());

      for (DbColuna cln : this.getLstCln()) {

        if (cln.getBooChavePrimaria()) {

          continue;
        }

        if (cln.getBooNome()) {

          continue;
        }

        if (!cln.getBooVisivelCadastro()) {

          continue;
        }

        if (_lstClnCadastro.contains(cln)) {

          continue;
        }

        _lstClnCadastro.add(cln);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstClnCadastro;
  }

  public List<DbColuna> getLstClnConsulta() {

    try {

      if (_lstClnConsulta != null) {

        return _lstClnConsulta;
      }

      _lstClnConsultaOrdenado = null;

      _lstClnConsulta = new ArrayList<>();

      _lstClnConsulta.add(this.getClnChavePrimaria());
      _lstClnConsulta.add(this.getClnNome());

      for (DbColuna cln : this.getLstCln()) {

        if (cln == null) {

          continue;
        }

        if (!cln.getBooVisivelConsulta()) {

          continue;
        }

        if (cln.getBooChavePrimaria()) {

          continue;
        }

        if (cln.getBooNome()) {

          continue;
        }

        if (_lstClnConsulta.contains(cln)) {

          continue;
        }

        _lstClnConsulta.add(cln);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstClnConsulta;
  }

  public List<DbColuna> getLstClnConsultaOrdenado() {

    try {

      if (_lstClnConsultaOrdenado != null) {

        return _lstClnConsultaOrdenado;
      }

      _lstClnConsultaOrdenado = this.getLstClnConsulta();

      Collections.sort(_lstClnConsultaOrdenado, new Comparator<DbColuna>() {

        @Override
        public int compare(DbColuna cln1, DbColuna cln2) {

          return (cln1.getIntOrdem() - cln2.getIntOrdem());
        }
      });
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _lstClnConsultaOrdenado;
  }

  public List<DbColuna> getLstClnOrdenado() {

    try {

      if (_lstClnOrdenado != null) {

        return _lstClnOrdenado;
      }

      _lstClnOrdenado = this.getLstCln();

      Collections.sort(_lstClnOrdenado, new Comparator<DbColuna>() {

        @Override
        public int compare(DbColuna cln1, DbColuna cln2) {

          return cln1.getStrNomeExibicao().compareTo(cln2.getStrNomeExibicao());
        }
      });
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _lstClnOrdenado;
  }

  private List<OnChangeListener> getLstEvtOnChangeListener() {

    try {

      if (_lstEvtOnChangeListener != null) {

        return _lstEvtOnChangeListener;
      }

      _lstEvtOnChangeListener = new ArrayList<>();

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstEvtOnChangeListener;
  }

  public List<DbFiltro> getLstFilCadastro() {

    try {

      if (_lstFilCadastro != null) {

        return _lstFilCadastro;
      }

      _lstFilCadastro = new ArrayList<>();

    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _lstFilCadastro;
  }

  public List<DbFiltro> getLstFilConsulta() {

    try {

      if (_lstFilConsulta != null) {

        return _lstFilConsulta;
      }

      _lstFilConsulta = new ArrayList<>();

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstFilConsulta;
  }

  public DataBase getObjDb() {

    return _objDb;
  }

  public String getStrClnNome(String strNomeSql) {

    try {

      for (DbColuna cln : this.getLstCln()) {

        if (!cln.getStrNomeSql().equals(strNomeSql)) {

          continue;
        }

        return cln.getStrNomeExibicao();
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(128), ex);
    }
    finally {
    }

    return null;
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

  public String getStrPesquisa() {

    return _strPesquisa;
  }

  protected int inicializarColuna(int intOrdem) {

    return intOrdem;
  }

  /**
   * Limpa os valores de todas as colunas da tabela.
   */
  public void limparColunas() {

    try {

      for (DbColuna cln : this.getLstCln()) {

        if (cln == null) {

          continue;
        }

        cln.limpar();
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(130), ex);
    }
    finally {
    }
  }

  public void removerOnChangeListener(OnChangeListener evtOnChangeListener) {

    try {

      if (evtOnChangeListener == null) {

        return;
      }

      if (!this.getLstEvtOnChangeListener().contains(evtOnChangeListener)) {

        return;
      }

      this.getLstEvtOnChangeListener().remove(evtOnChangeListener);

    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void setBooMenuAdicionar(boolean booMenuAdicionar) {

    _booMenuAdicionar = booMenuAdicionar;
  }

  protected void setBooMenuAlterar(boolean booMenuAlterar) {

    _booMenuAlterar = booMenuAlterar;
  }

  protected void setBooMenuApagar(boolean booMenuApagar) {

    _booMenuApagar = booMenuApagar;
  }

  public void setClnChavePrimaria(DbColuna clnChavePrimaria) {

    _clnChavePrimaria = clnChavePrimaria;
  }

  public void setClnNome(DbColuna clnNome) {

    _clnNome = clnNome;
  }

  public void setClnOrdem(DbColuna clnOrdem) {

    _clnOrdem = clnOrdem;
  }

  protected void setClsDominio(Class<T> clsDominio) {

    _clsDominio = clsDominio;
  }

  void setLstClnCadastro(List<DbColuna> lstClnCadastro) {

    _lstClnCadastro = lstClnCadastro;
  }

  void setLstClnConsulta(List<DbColuna> lstClnConsulta) {

    _lstClnConsulta = lstClnConsulta;
  }

  public void setObjDb(DataBase objDb) {

    _objDb = objDb;
  }

  public void setStrPesquisa(String strPesquisa) {

    _strPesquisa = strPesquisa;
  }
}