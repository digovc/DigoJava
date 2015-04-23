package com.digosofter.digojava.database;

import java.util.ArrayList;
import java.util.List;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class DbTabela extends Objeto {

  private boolean _booMenuAdicionar;
  private boolean _booMenuAlterar;
  private boolean _booMenuApagar;
  private DbColuna _clnChavePrimaria;
  private DbColuna _clnNome;
  private DbColuna _clnOrdem;
  private int _intQtdLinha;
  private List<DbColuna> _lstCln;
  private List<DbColuna> _lstClnCadastro;
  private List<DbColuna> _lstClnConsulta;
  private List<TblOnChangeListener> _lstEvtTblOnChangeListener;
  private List<DbFiltro> _lstFilCadastro;
  private List<DbFiltro> _lstFilConsulta;
  private DataBase _objDb;
  private String _strNomeSql;
  private String _strPesquisa;

  protected DbTabela(String strNome) {

    try {

      App.getI().addTbl(this);

      this.setStrNome(strNome);
      this.inicializarColuna(-1);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(122), ex);
    }
    finally {
    }
  }

  public void addEvtTblOnChangeListener(TblOnChangeListener evtTblOnChangeListener) {

    try {

      if (evtTblOnChangeListener == null) {

        return;
      }

      if (this.getLstEvtTblOnChangeListener().contains(evtTblOnChangeListener)) {

        return;
      }

      this.getLstEvtTblOnChangeListener().add(evtTblOnChangeListener);

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
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _clnOrdem;
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

      _lstCln = new ArrayList<DbColuna>();
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _lstCln;
  }

  public void apagar(int intId) {

    TblOnChangeArg arg;

    try {

      arg = new TblOnChangeArg();
      arg.setIntRegistroId(intId);

      this.OnApagarRegDispatcher(arg);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public List<DbColuna> getLstClnCadastro() {

    try {

      if (_lstClnCadastro != null) {

        return _lstClnCadastro;
      }

      _lstClnCadastro = new ArrayList<DbColuna>();
      _lstClnCadastro.add(this.getClnNome());

      for (DbColuna cln : this.getLstCln()) {

        if (cln.getBooChavePrimaria()) {

          continue;
        }

        if (cln.getBooClnNome()) {

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

      _lstClnConsulta = new ArrayList<DbColuna>();

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

        if (cln.getBooClnNome()) {

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

  private List<TblOnChangeListener> getLstEvtTblOnChangeListener() {

    try {

      if (_lstEvtTblOnChangeListener != null) {

        return _lstEvtTblOnChangeListener;
      }

      _lstEvtTblOnChangeListener = new ArrayList<TblOnChangeListener>();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstEvtTblOnChangeListener;
  }

  public List<DbFiltro> getLstFilCadastro() {

    try {

      if (_lstFilCadastro != null) {

        return _lstFilCadastro;
      }

      _lstFilCadastro = new ArrayList<DbFiltro>();
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

  public String getStrClnNome(String strNomeSimplificado) {

    String strResultado = null;

    try {

      strResultado = Utils.STR_VAZIA;

      for (DbColuna cln : this.getLstCln()) {

        if (!cln.getStrNomeSql().equals(strNomeSimplificado)) {

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

    return strResultado;
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

        cln.setStrValor(null);
      }
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(130), ex);
    }
    finally {
    }
  }

  protected void OnAdicionarRegDispatcher(TblOnChangeArg arg) {

    try {

      for (TblOnChangeListener evt : this.getLstEvtTblOnChangeListener()) {

        if (evt == null) {

          return;
        }

        evt.OnAdicionarReg(arg);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void OnApagarRegDispatcher(TblOnChangeArg arg) {

    try {

      for (TblOnChangeListener evt : this.getLstEvtTblOnChangeListener()) {

        if (evt == null) {

          return;
        }

        evt.OnApagarReg(arg);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void OnAtualizarRegDispatcher(TblOnChangeArg arg) {

    try {

      for (TblOnChangeListener evt : this.getLstEvtTblOnChangeListener()) {

        if (evt == null) {

          return;
        }

        evt.OnAtualizarReg(arg);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public void removeEvtTblOnChangeListener(TblOnChangeListener evtTblOnChangeListener) {

    try {

      if (evtTblOnChangeListener == null) {

        return;
      }

      if (!this.getLstEvtTblOnChangeListener().contains(evtTblOnChangeListener)) {

        return;
      }

      this.getLstEvtTblOnChangeListener().remove(evtTblOnChangeListener);
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

  public void setObjDb(DataBase objDb) {

    _objDb = objDb;
  }

  public void setStrPesquisa(String strPesquisa) {

    _strPesquisa = strPesquisa;
  }
}