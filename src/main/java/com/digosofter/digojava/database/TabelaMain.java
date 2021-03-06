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

  private boolean _booPermitirAdicionar = true;
  private boolean _booPermitirAlterar;
  private boolean _booPermitirApagar;
  private boolean _booRecemCriada;
  private ColunaMain _clnNome;
  private Class<T> _clsDominio;
  private DbeMain _dbe;
  private List<ColunaMain> _lstCln;
  private List<ColunaMain> _lstClnConsulta;
  private List<ColunaMain> _lstClnOrdem;
  private List<ColunaMain> _lstClnOrdenado;
  private List<OnTblChangeListener> _lstEvtOnTblChangeListener;
  private List<Filtro> _lstFilConsulta;
  private String _sqlNome;
  private String _sqlOrderBy;
  private String _strNomeExibicao;
  private String _strPesquisa;
  private TabelaMain _tblPrincipal;

  protected TabelaMain()
  {
    this.setStrNome(Utils.simplificar(this.getClass().getSimpleName()));
  }

  void addClnOrdem(final ColunaMain cln)
  {
    if (cln == null)
    {
      return;
    }

    if (!this.equals(cln.getTbl()))
    {
      return;
    }

    if (!ColunaMain.EnmOrdem.NONE.equals(cln.getEnmOrdem()))
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

  // TODO: Criar uma classe que gerencia a criação e atualização das tabelas.
  protected abstract void criar();

  protected void dispararEvtOnAdicionarReg(OnChangeArg arg)
  {
    for (OnTblChangeListener evt : this.getLstEvtOnChangeListener())
    {
      if (evt == null)
      {
        continue;
      }

      evt.onTabelaAdicionar(arg);
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

      evt.onTabelaApagar(e);
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

      evt.onTabelaAtualizar(e);
    }
  }

  protected void finalizar()
  {

  }

  public boolean getBooPermitirAdicionar()
  {
    return _booPermitirAdicionar;
  }

  public boolean getBooPermitirAlterar()
  {
    return _booPermitirAlterar;
  }

  public boolean getBooPermitirApagar()
  {
    return _booPermitirApagar;
  }

  public boolean getBooRecemCriada()
  {
    return _booRecemCriada;
  }

  /**
   * Pesquisa na lista de colunas desta tabela e retorna a coluna que tem o nome passado como parâmetro.
   *
   * @param strClnNomeSql Nome da coluna que se deseja encontrar.
   * @return Retorna a coluna que possui o mesmo nome que foi passado como parâmetro ou null caso não encontre.
   */
  public ColunaMain getCln(final String strClnNomeSql)
  {
    if (Utils.getBooStrVazia(strClnNomeSql))
    {
      return null;
    }

    for (ColunaMain cln : this.getLstCln())
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

  public abstract ColunaMain getClnBooAtivo();

  public abstract ColunaMain getClnDttAlteracao();

  public abstract ColunaMain getClnDttCadastro();

  public abstract ColunaMain getClnIntId();

  protected abstract ColunaMain getClnIntUsuarioAlteracaoId();

  protected abstract ColunaMain getClnIntUsuarioCadastroId();

  public ColunaMain getClnNome()
  {
    if (_clnNome != null)
    {
      return _clnNome;
    }

    _clnNome = this.getClnIntId();

    return _clnNome;
  }

  public abstract ColunaMain getClnStrObservacao();

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
    return this.getDbe().execSqlInt(String.format("select count(1) from %s;", this.getSqlNome()));
  }

  public List<ColunaMain> getLstCln()
  {
    if (_lstCln != null)
    {
      return _lstCln;
    }

    _lstClnOrdenado = null;

    _lstCln = new ArrayList<>();

    this.inicializarLstCln(_lstCln);

    return _lstCln;
  }

  public List<ColunaMain> getLstClnConsulta()
  {
    if (_lstClnConsulta != null)
    {
      return _lstClnConsulta;
    }

    List<ColunaMain> lstClnConsultaResultado = new ArrayList<>();

    lstClnConsultaResultado.add(this.getClnIntId());
    lstClnConsultaResultado.add(this.getClnNome());

    for (ColunaMain cln : this.getLstCln())
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

  protected List<ColunaMain> getLstClnOrdem()
  {
    if (_lstClnOrdem != null)
    {
      return _lstClnOrdem;
    }

    _lstClnOrdem = new ArrayList<>();

    return _lstClnOrdem;
  }

  public List<ColunaMain> getLstClnOrdenado()
  {
    if (_lstClnOrdenado != null)
    {
      return _lstClnOrdenado;
    }

    _lstClnOrdenado = new ArrayList<>(this.getLstCln());

    Collections.sort(_lstClnOrdenado, new Comparator<ColunaMain>()
    {
      @Override
      public int compare(ColunaMain cln1, ColunaMain cln2)
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
    if (_sqlNome != null)
    {
      return _sqlNome;
    }

    _sqlNome = this.getStrNome();

    return _sqlNome;
  }

  protected String getSqlOrderBy()
  {
    if (_sqlOrderBy != null)
    {
      return _sqlOrderBy;
    }

    _sqlOrderBy = Utils.STR_VAZIA;

    for (ColunaMain cln : this.getLstClnOrdem())
    {
      _sqlOrderBy = _sqlOrderBy.concat(cln.getSqlOrderBy());
    }

    return _sqlOrderBy;
  }

  public String getStrClnNome(String strNomeSql)
  {
    for (ColunaMain cln : this.getLstCln())
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

  public TabelaMain getTbl(final int intTabelaObjetoId)
  {
    if (this.getIntObjetoId() == intTabelaObjetoId)
    {
      return this;
    }

    return null;
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
    this.criar();

    for (ColunaMain cln : this.getLstCln())
    {
      cln.setTbl(this);
    }

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

  private void inicializarLstCln()
  {
    for (ColunaMain cln : this.getLstCln())
    {
      cln.iniciar();
    }
  }

  protected void inicializarLstCln(List<ColunaMain> lstCln)
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

  public void iniciar(DbeMain dbe)
  {
    this.setDbe(dbe);

    this.inicializar();
    this.setEventos();
    this.finalizar();
  }

  void iniciarCln()
  {
    this.inicializarLstCln();
  }

  /**
   * Limpa os valores de todas as colunas da tabela.
   */
  public void limparDados()
  {
    this.bloquearThread();

    for (ColunaMain cln : this.getLstCln())
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

    for (ColunaMain cln : this.getLstCln())
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

  protected void setBooPermitirAdicionar(boolean booPermitirAdicionar)
  {
    _booPermitirAdicionar = booPermitirAdicionar;
  }

  protected void setBooPermitirAlterar(boolean booPermitirAlterar)
  {
    _booPermitirAlterar = booPermitirAlterar;
  }

  protected void setBooPermitirApagar(boolean booPermitirApagar)
  {
    _booPermitirApagar = booPermitirApagar;
  }

  protected void setBooRecemCriada(boolean booRecemCriada)
  {
    _booRecemCriada = booRecemCriada;
  }

  void setClnNome(ColunaMain clnNome)
  {
    _clnNome = clnNome;
  }

  private void setDbe(DbeMain dbe)
  {
    _dbe = dbe;
  }

  protected void setEventos()
  {

  }

  void setLstClnConsulta(List<ColunaMain> lstClnConsulta)
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