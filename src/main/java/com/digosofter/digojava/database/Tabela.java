package com.digosofter.digojava.database;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Tabela<T extends Dominio> extends Objeto {

  private boolean _booMenuAdicionar;
  private boolean _booMenuAlterar;
  private boolean _booMenuApagar;
  private Coluna _clnChavePrimaria;
  private Coluna _clnNome;
  private Coluna _clnOrdem;
  private Class<T> _clsDominio;
  private int _intQtdLinha;
  private List<Coluna> _lstCln;
  private List<Coluna> _lstClnCadastro;
  private List<Coluna> _lstClnConsulta;
  private List<Coluna> _lstClnConsultaOrdenado;
  private List<Coluna> _lstClnOrdenado;
  private List<OnChangeListener> _lstEvtOnChangeListener;
  private List<Filtro> _lstFilCadastro;
  private List<Filtro> _lstFilConsulta;
  private DataBase _objDb;
  private String _strNomeSql;
  private String _strPesquisa;

  protected Tabela(String strNome, Class<T> clsDominio) {

    try {

      this.setClsDominio(clsDominio);
      this.setStrNome(strNome);
      this.addAppLstTbl();

      this.inicializarLstCln(-1);
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

  public void addCln(Coluna cln) {

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

  /**
   * Pesquisa na lista de colunas desta tabela e retorna a coluna que tem o nome passado como parâmetro.
   *
   * @param strClnNomeSql Nome da coluna que se deseja encontrar.
   * @return Retorna a coluna que possui o mesmo nome que foi passado como parâmetro ou null caso não encontre.
   */
  public Coluna getCln(final String strClnNomeSql) {

    Coluna clnResultado;

    try {

      clnResultado = null;

      if (Utils.getBooStrVazia(strClnNomeSql)) {

        return null;
      }

      for (Coluna cln : this.getLstCln()) {

        if (cln == null) {

          continue;
        }

        if (Utils.getBooStrVazia(cln.getStrNomeSql())) {

          continue;
        }

        if (!cln.getStrNomeSql().equals(strClnNomeSql)) {

          continue;
        }

        return cln;
      }

      return clnResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return null;
  }

  public Coluna getClnChavePrimaria() {

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

  public Coluna getClnNome() {

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

  public Coluna getClnOrdem() {

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

  public Class<T> getClsDominio() {

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

  public List<Coluna> getLstCln() {

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

  public List<Coluna> getLstClnCadastro() {

    try {

      if (_lstClnCadastro != null) {

        return _lstClnCadastro;
      }

      _lstClnCadastro = new ArrayList<>();

      _lstClnCadastro.add(this.getClnNome());

      for (Coluna cln : this.getLstCln()) {

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

  public List<Coluna> getLstClnConsulta() {

    try {

      if (_lstClnConsulta != null) {

        return _lstClnConsulta;
      }

      _lstClnConsultaOrdenado = null;

      _lstClnConsulta = new ArrayList<>();

      _lstClnConsulta.add(this.getClnChavePrimaria());
      _lstClnConsulta.add(this.getClnNome());

      for (Coluna cln : this.getLstCln()) {

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

  public List<Coluna> getLstClnConsultaOrdenado() {

    try {

      if (_lstClnConsultaOrdenado != null) {

        return _lstClnConsultaOrdenado;
      }

      _lstClnConsultaOrdenado = this.getLstClnConsulta();

      Collections.sort(_lstClnConsultaOrdenado, new Comparator<Coluna>() {

        @Override
        public int compare(Coluna cln1, Coluna cln2) {

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

  public List<Coluna> getLstClnOrdenado() {

    try {

      if (_lstClnOrdenado != null) {

        return _lstClnOrdenado;
      }

      _lstClnOrdenado = this.getLstCln();

      Collections.sort(_lstClnOrdenado, new Comparator<Coluna>() {

        @Override
        public int compare(Coluna cln1, Coluna cln2) {

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

  public List<Filtro> getLstFilCadastro() {

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

  public List<Filtro> getLstFilConsulta() {

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

      for (Coluna cln : this.getLstCln()) {

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

  protected int inicializarLstCln(int intOrdem) {

    return intOrdem;
  }

  /**
   * Limpa os valores de todas as colunas da tabela.
   */
  public void limparColunas() {

    try {

      for (Coluna cln : this.getLstCln()) {

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

  public void setClnChavePrimaria(Coluna clnChavePrimaria) {

    _clnChavePrimaria = clnChavePrimaria;
  }

  public void setClnNome(Coluna clnNome) {

    _clnNome = clnNome;
  }

  public void setClnOrdem(Coluna clnOrdem) {

    _clnOrdem = clnOrdem;
  }

  protected void setClsDominio(Class<T> clsDominio) {

    _clsDominio = clsDominio;
  }

  void setLstClnCadastro(List<Coluna> lstClnCadastro) {

    _lstClnCadastro = lstClnCadastro;
  }

  void setLstClnConsulta(List<Coluna> lstClnConsulta) {

    _lstClnConsulta = lstClnConsulta;
  }

  public void setObjDb(DataBase objDb) {

    _objDb = objDb;
  }

  @Override
  public void setStrNome(final String strNome) {

    super.setStrNome(strNome);

    try {

      if (Utils.getBooStrVazia(strNome)) {

        return;
      }

      this.setStrNomeExibicao(strNome.replace("tbl_", ""));
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public void setStrPesquisa(String strPesquisa) {

    _strPesquisa = strPesquisa;
  }
}