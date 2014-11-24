package com.digosofter.digojava.database;

import java.util.ArrayList;
import java.util.List;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class DbTabela extends Objeto {

  private boolean _booPermitirCadastroNovo;
  private DbColuna _clnChavePrimaria;
  private DbColuna _clnNome;
  private DbColuna _clnOrdemCadastro;
  private int _intQtdLinha;
  private List<DbColuna> _lstCln;
  private List<DbColuna> _lstClnCadastro;
  private ArrayList<DbFiltro> _lstDbFiltroTelaCadastro;
  private DataBase _objDb;
  private String _strPesquisaActConsulta;

  public DbTabela(String strNome) {

    try {

      App.getI().getLstTbl().add(this);

      this.setStrNome(strNome);
      this.inicializarColunas(-1);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(122), ex);
    }
    finally {
    }
  }

  public boolean getBooPermitirCadastroNovo() {

    return _booPermitirCadastroNovo;
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

  public DbColuna getClnOrdemCadastro() {

    try {

      if (_clnOrdemCadastro != null) {

        return _clnOrdemCadastro;
      }

      _clnOrdemCadastro = this.getClnChavePrimaria();
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _clnOrdemCadastro;
  }

  public int getIntQtdLinha() {

    String sql;

    try {

      sql = "select count(1) from _tbl_nome;";
      sql = sql.replace("_tbl_nome", this.getStrNomeSimplificado());

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

  public List<DbColuna> getLstClnCadastro() {

    try {

      if (_lstClnCadastro != null) {

        return _lstClnCadastro;
      }

      _lstClnCadastro = new ArrayList<DbColuna>();

      for (DbColuna cln : this.getLstCln()) {

        if (!cln.getBooVisivelCadastro() && !cln.getBooChavePrimaria()) {
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

  public ArrayList<DbFiltro> getLstDbFiltroTelaCadastro() {

    try {

      if (_lstDbFiltroTelaCadastro != null) {

        return _lstDbFiltroTelaCadastro;
      }

      _lstDbFiltroTelaCadastro = new ArrayList<DbFiltro>();
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _lstDbFiltroTelaCadastro;
  }

  public DataBase getObjDb() {

    try {

      if (_objDb != null) {

        return _objDb;
      }

      _objDb = App.getI().getObjDbPrincipal();
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _objDb;
  }

  public String getStrClnNome(String strNomeSimplificado) {

    String strResultado = null;

    try {

      strResultado = Utils.STR_VAZIA;

      for (DbColuna cln : this.getLstCln()) {

        if (cln.getStrNomeSimplificado().equals(strNomeSimplificado)) {
          return cln.getStrNomeExibicao();
        }
      }
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(128), ex);
    }
    finally {
    }

    return strResultado;
  }

  public String getStrPesquisaActConsulta() {

    return _strPesquisaActConsulta;
  }

  protected int inicializarColunas(int intOrdem) {

    try {
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(128), ex);
    }
    finally {
    }
    return intOrdem;
  }

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

  protected void setBooPermitirCadastroNovo(boolean booPermitirCadastroNovo) {

    _booPermitirCadastroNovo = booPermitirCadastroNovo;
  }

  public void setClnChavePrimaria(DbColuna clnChavePrimaria) {

    _clnChavePrimaria = clnChavePrimaria;
  }

  public void setClnNome(DbColuna clnNome) {

    _clnNome = clnNome;
  }

  public void setClnOrdemCadastro(DbColuna clnOrdemCadastro) {

    _clnOrdemCadastro = clnOrdemCadastro;
  }

  public void setObjDb(DataBase objDb) {

    _objDb = objDb;
  }

  public void setStrPesquisaActConsulta(String strPesquisaActConsulta) {

    _strPesquisaActConsulta = strPesquisaActConsulta;
  }
}
