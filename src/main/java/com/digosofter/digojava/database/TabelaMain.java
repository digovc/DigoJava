package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.dominio.DominioMain;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class TabelaMain<T extends DominioMain> extends Objeto
{
  public static final String STR_CLN_DTT_ALTERACAO_NOME = "Alteração";
  public static final String STR_CLN_DTT_CADASTRO_NOME = "Cadastro";
  public static final String STR_CLN_STR_USUARIO_ALTERACAO_NOME = "Usuário (alteração)";
  public static final String STR_CLN_STR_USUARIO_CADASTRO_NOME = "Usuário (cadastro)";

  private boolean _booPermitirAlterar;
  private boolean _booPermitirApagar;
  private Coluna _clnNome;
  private Class<T> _clsDominio;
  private DbeMain _dbe;
  private List<Coluna> _lstCln;
  private List<Coluna> _lstClnConsulta;
  private List<Coluna> _lstClnOrdem;
  private List<Coluna> _lstClnOrdenado;
  private List<OnTblChangeListener> _lstEvtOnTblChangeListener;
  private List<Filtro> _lstFilConsulta;
  private String _sqlNome;
  private String _sqlOrderBy;
  private String _strNomeExibicao;
  private String _strPesquisa;
  private TabelaMain _tblPrincipal;

  protected TabelaMain(String strNome, DbeMain dbe)
  {
    this.setDbe(dbe);
    this.setStrNome(strNome);

    this.iniciar();
  }

  void addClnOrdem(final Coluna cln)
  {
    if (cln == null)
    {
      return;
    }

    if (!this.equals(cln.getTbl()))
    {
      return;
    }

    if (!Coluna.EnmOrdem.NONE.equals(cln.getEnmOrdem()))
    {
      this.getLstClnOrdem().add(cln);
    }
    else
    {
      this.getLstClnOrdem().remove(cln);
    }

    this.setSqlOrderBy(null);
  }

  public void addEvtOnTblChangeListener(OnTblChangeListener evt)
  {
    if (evt == null)
    {
      return;
    }

    if (this.getLstEvtOnChangeListener().contains(evt))
    {
      return;
    }

    this.getLstEvtOnChangeListener().add(evt);
  }

  public void apagar(int intId)
  {
    OnChangeArg arg;

    arg = new OnChangeArg();

    arg.setIntRegistroId(intId);

    this.dispararEvtOnApagarReg(arg);
  }

  private void atualizarDbe(final DbeMain dbe)
  {
    if (dbe == null)
    {
      return;
    }

    dbe.addTbl(this);
  }

  // TODO: Criar uma classe que gerencia a criação e atualização das tabelas.
  protected abstract void criar();

  protected void criarColuna()
  {
    for (Coluna cln : this.getLstCln())
    {
      if (cln == null)
      {
        continue;
      }

      cln.criar();
    }
  }

  protected void dispararEvtOnAdicionarReg(OnChangeArg arg)
  {
    for (OnTblChangeListener evt : this.getLstEvtOnChangeListener())
    {
      if (evt == null)
      {
        continue;
      }

      evt.onTblAdicionar(arg);
    }
  }

  protected void dispararEvtOnApagarReg(OnChangeArg e)
  {
    for (OnTblChangeListener evt : this.getLstEvtOnChangeListener())
    {
      if (evt == null)
      {
        continue;
      }

      evt.onTblApagar(e);
    }
  }

  protected void dispararEvtOnAtualizarReg(OnChangeArg e)
  {
    for (OnTblChangeListener evt : this.getLstEvtOnChangeListener())
    {
      if (evt == null)
      {
        continue;
      }

      evt.onTblAtualizar(e);
    }
  }

  protected void finalizar()
  {

  }

  public boolean getBooPermitirAlterar()
  {
    return _booPermitirAlterar;
  }

  public boolean getBooPermitirApagar()
  {
    return _booPermitirApagar;
  }

  /**
   * Pesquisa na lista de colunas desta tabela e retorna a coluna que tem o nome passado como parâmetro.
   *
   * @param strClnNomeSql Nome da coluna que se deseja encontrar.
   * @return Retorna a coluna que possui o mesmo nome que foi passado como parâmetro ou null caso não encontre.
   */
  public Coluna getCln(final String strClnNomeSql)
  {
    if (Utils.getBooStrVazia(strClnNomeSql))
    {
      return null;
    }

    for (Coluna cln : this.getLstCln())
    {
      if (cln == null)
      {
        continue;
      }

      if (!strClnNomeSql.toLowerCase().equals(cln.getSqlNome()))
      {
        continue;
      }

      return cln;
    }

    return null;
  }

  public abstract Coluna getClnBooAtivo();

  public abstract Coluna getClnDttAlteracao();

  public abstract Coluna getClnDttCadastro();

  public abstract Coluna getClnIntId();

  protected abstract Coluna getClnIntUsuarioAlteracaoId();

  protected abstract Coluna getClnIntUsuarioCadastroId();

  public Coluna getClnNome()
  {
    if (_clnNome != null)
    {
      return _clnNome;
    }

    _clnNome = this.getClnIntId();

    _clnNome.setBooNome(true);

    return _clnNome;
  }

  public abstract Coluna getClnStrObservacao();

  public Class<T> getClsDominio()
  {
    if (_clsDominio != null)
    {
      return _clsDominio;
    }

    if (!(this.getClass().getGenericSuperclass() instanceof ParameterizedType))
    {
      return null;
    }

    if (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments() == null)
    {
      return null;
    }

    if (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments().length < 1)
    {
      return null;
    }

    _clsDominio = ((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);

    return _clsDominio;
  }

  public DbeMain getDbe()
  {
    return _dbe;
  }

  /**
   * @return Retorna a quantidade de registros presentes nesta tabela.
   */
  public int getIntRegistroQuantidade()
  {
    String sql = "select count(1) from _tbl_nome;";

    sql = sql.replace("_tbl_nome", this.getSqlNome());

    return this.getDbe().execSqlInt(sql);
  }

  public List<Coluna> getLstCln()
  {
    if (_lstCln != null)
    {
      return _lstCln;
    }

    _lstClnOrdenado = null;

    _lstCln = new ArrayList<>();

    return _lstCln;
  }

  public List<Coluna> getLstClnConsulta()
  {
    if (_lstClnConsulta != null)
    {
      return _lstClnConsulta;
    }

    List<Coluna> lstClnConsultaResultado = new ArrayList<>();

    lstClnConsultaResultado.add(this.getClnIntId());
    lstClnConsultaResultado.add(this.getClnNome());

    for (Coluna cln : this.getLstCln())
    {
      if (cln == null)
      {
        continue;
      }

      if (!cln.getBooVisivelConsulta())
      {
        continue;
      }

      if (lstClnConsultaResultado.contains(cln))
      {
        continue;
      }

      lstClnConsultaResultado.add(cln);
    }

    _lstClnConsulta = lstClnConsultaResultado;

    return _lstClnConsulta;
  }

  protected List<Coluna> getLstClnOrdem()
  {
    if (_lstClnOrdem != null)
    {
      return _lstClnOrdem;
    }

    _lstClnOrdem = new ArrayList<>();

    return _lstClnOrdem;
  }

  public List<Coluna> getLstClnOrdenado()
  {
    if (_lstClnOrdenado != null)
    {
      return _lstClnOrdenado;
    }

    _lstClnOrdenado = new ArrayList<>(this.getLstCln());

    Collections.sort(_lstClnOrdenado, new Comparator<Coluna>()
    {
      @Override
      public int compare(Coluna cln1, Coluna cln2)
      {
        return cln1.getStrNomeExibicao().compareTo(cln2.getStrNomeExibicao());
      }
    });

    return _lstClnOrdenado;
  }

  private List<OnTblChangeListener> getLstEvtOnChangeListener()
  {
    if (_lstEvtOnTblChangeListener != null)
    {
      return _lstEvtOnTblChangeListener;
    }

    _lstEvtOnTblChangeListener = new ArrayList<>();

    return _lstEvtOnTblChangeListener;
  }

  protected final List<Filtro> getLstFilConsulta()
  {
    if (_lstFilConsulta != null)
    {
      return _lstFilConsulta;
    }

    _lstFilConsulta = new ArrayList<>();

    this.inicializarLstFilConsulta(_lstFilConsulta);

    return _lstFilConsulta;
  }

  public String getSqlNome()
  {
    if (!Utils.getBooStrVazia(_sqlNome))
    {
      return _sqlNome;
    }

    _sqlNome = this.getStrNomeSimplificado();

    return _sqlNome;
  }

  protected String getSqlOrderBy()
  {
    if (_sqlOrderBy != null)
    {
      return _sqlOrderBy;
    }

    _sqlOrderBy = Utils.STR_VAZIA;

    for (Coluna cln : this.getLstClnOrdem())
    {
      _sqlOrderBy = _sqlOrderBy.concat(cln.getSqlOrderBy());
    }

    return _sqlOrderBy;
  }

  public String getStrClnNome(String strNomeSql)
  {
    for (Coluna cln : this.getLstCln())
    {
      if (!cln.getSqlNome().equals(strNomeSql))
      {
        continue;
      }

      return cln.getStrNomeExibicao();
    }

    return null;
  }

  public String getStrNomeExibicao()
  {
    if (_strNomeExibicao != null)
    {
      return _strNomeExibicao;
    }

    _strNomeExibicao = super.getStrNomeExibicao();

    if (Utils.getBooStrVazia(_strNomeExibicao))
    {
      return null;
    }

    if (!_strNomeExibicao.toLowerCase().equals(this.getSqlNome()))
    {
      return _strNomeExibicao;
    }

    _strNomeExibicao = _strNomeExibicao.substring(4);

    _strNomeExibicao = _strNomeExibicao.replace("_", " ");

    _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(_strNomeExibicao);

    return _strNomeExibicao;
  }

  public String getStrPesquisa()
  {
    return _strPesquisa;
  }

  public TabelaMain getTblPrincipal()
  {
    if (_tblPrincipal != null)
    {
      return _tblPrincipal;
    }

    _tblPrincipal = this;

    return _tblPrincipal;
  }

  protected void inicializar()
  {
    this.inicializarLstCln(this.getLstCln());

    this.criar();
    this.criarColuna();

    this.inicializarClnDttAlteracao();
    this.inicializarClnIntUsuarioAlteracaoId();
    this.inicializarClnIntUsuarioCadastroId();
  }

  private void inicializarClnDttAlteracao()
  {
    if (this.getClnDttAlteracao() == null)
    {
      return;
    }

    this.getClnDttAlteracao().setStrNomeExibicao(STR_CLN_DTT_ALTERACAO_NOME);
  }

  private void inicializarClnIntUsuarioAlteracaoId()
  {
    if (this.getClnIntUsuarioAlteracaoId() == null)
    {
      return;
    }

    this.getClnIntUsuarioAlteracaoId().setStrNomeExibicao(STR_CLN_STR_USUARIO_ALTERACAO_NOME);
  }

  private void inicializarClnIntUsuarioCadastroId()
  {
    if (this.getClnIntUsuarioCadastroId() == null)
    {
      return;
    }

    this.getClnIntUsuarioCadastroId().setStrNomeExibicao(STR_CLN_STR_USUARIO_CADASTRO_NOME);
  }

  protected void inicializarLstCln(List<Coluna> lstCln)
  {
    lstCln.add(this.getClnBooAtivo());
    lstCln.add(this.getClnDttAlteracao());
    lstCln.add(this.getClnDttCadastro());
    lstCln.add(this.getClnIntId());
    lstCln.add(this.getClnIntUsuarioAlteracaoId());
    lstCln.add(this.getClnIntUsuarioCadastroId());
    lstCln.add(this.getClnStrObservacao());
  }

  protected void inicializarLstFilConsulta(final List<Filtro> lstFilConsulta)
  {
  }

  private void iniciar()
  {
    this.inicializar();
    this.setEventos();
    this.finalizar();
  }

  /**
   * Limpa os valores de todas as colunas da tabela.
   */
  public void limparDados()
  {
    this.bloquearThread();

    for (Coluna cln : this.getLstCln())
    {
      if (cln == null)
      {
        continue;
      }

      cln.limpar();
    }
  }

  public void limparOrdem()
  {
    this.getLstClnOrdem().clear();
    this.setSqlOrderBy(null);

    for (Coluna cln : this.getLstCln())
    {
      cln.limparOrdem();
    }
  }

  public void removerEvtOnTblChangeListener(OnTblChangeListener evtOnTblChangeListener)
  {
    if (evtOnTblChangeListener == null)
    {
      return;
    }

    if (!this.getLstEvtOnChangeListener().contains(evtOnTblChangeListener))
    {
      return;
    }

    this.getLstEvtOnChangeListener().remove(evtOnTblChangeListener);
  }

  protected void setBooPermitirAlterar(boolean booPermitirAlterar)
  {
    _booPermitirAlterar = booPermitirAlterar;
  }

  protected void setBooPermitirApagar(boolean booPermitirApagar)
  {
    _booPermitirApagar = booPermitirApagar;
  }

  void setClnNome(Coluna clnNome)
  {
    _clnNome = clnNome;
  }

  private void setDbe(DbeMain dbe)
  {
    if (_dbe == dbe)
    {
      return;
    }

    _dbe = dbe;

    this.atualizarDbe(dbe);
  }

  protected void setEventos()
  {

  }

  void setLstClnConsulta(List<Coluna> lstClnConsulta)
  {
    _lstClnConsulta = lstClnConsulta;
  }

  private void setSqlOrderBy(final String sqlOrderBy)
  {
    _sqlOrderBy = sqlOrderBy;
  }

  public void setStrPesquisa(String strPesquisa)
  {
    _strPesquisa = strPesquisa;
  }

  @Override
  public String toString()
  {
    return this.getSqlNome();
  }
}