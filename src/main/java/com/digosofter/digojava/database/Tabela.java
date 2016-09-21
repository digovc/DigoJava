package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.dominio.DominioMain;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Tabela<T extends DominioMain> extends Objeto
{
  public static final String STR_CLN_DTT_ALTERACAO_NOME = "altecação";
  public static final String STR_CLN_DTT_CADASTRO_NOME = "cadastro";
  public static final String STR_CLN_STR_USUARIO_ALTERACAO_NOME = "cadastro";
  public static final String STR_CLN_STR_USUARIO_CADASTRO_NOME = "cadastro";

  private boolean _booPermitirAlterar;
  private boolean _booPermitirApagar;
  private Coluna _clnNome;
  private Coluna _clnOrdem;
  private Class<T> _clsDominio;
  private DataBase _dbe;
  private List<Coluna> _lstCln;
  private List<Coluna> _lstClnCadastro;
  private List<Coluna> _lstClnConsulta;
  private List<Coluna> _lstClnConsultaOrdenado;
  private List<Coluna> _lstClnOrdenado;
  private List<OnTblChangeListener> _lstEvtOnTblChangeListener;
  private List<Filtro> _lstFilCadastro;
  private List<Filtro> _lstFilConsulta;
  private String _sqlNome;
  private String _strNomeExibicao;
  private String _strPesquisa;
  private Tabela _tblPrincipal;

  protected Tabela(String strNome, DataBase dbe)
  {
    this.setDbe(dbe);
    this.setStrNome(strNome);

    this.iniciar();
  }

  public void addCln(Coluna cln)
  {
    if (cln == null)
    {
      return;
    }

    if (!this.equals(cln.getTbl()))
    {
      return;
    }

    if (this.getLstCln().contains(cln))
    {
      return;
    }

    this.getLstCln().add(cln);
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

  private void criarColuna()
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

  public Coluna getClnOrdem()
  {
    if (_clnOrdem != null)
    {
      return _clnOrdem;
    }

    _clnOrdem = this.getClnNome();

    _clnOrdem.setBooOrdem(true);

    return _clnOrdem;
  }

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

  public DataBase getDbe()
  {
    return _dbe;
  }

  public int getIntQtdLinha()
  {
    String sql = "select count(1) from _tbl_nome;";

    sql = sql.replace("_tbl_nome", this.getSqlNome());

    return this.getDbe().execSqlGetInt(sql);
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

  public List<Coluna> getLstClnCadastro()
  {
    if (_lstClnCadastro != null)
    {
      return _lstClnCadastro;
    }

    _lstClnCadastro = new ArrayList<>();

    _lstClnCadastro.add(this.getClnNome());

    for (Coluna cln : this.getLstCln())
    {
      if (cln == null)
      {
        continue;
      }

      if (cln == this.getClnIntId())
      {
        continue;
      }

      if (cln.getBooNome())
      {
        continue;
      }

      if (!cln.getBooVisivelCadastro())
      {
        continue;
      }

      if (_lstClnCadastro.contains(cln))
      {
        continue;
      }

      _lstClnCadastro.add(cln);
    }

    return _lstClnCadastro;
  }

  public List<Coluna> getLstClnConsulta()
  {
    if (_lstClnConsulta != null)
    {
      return _lstClnConsulta;
    }

    _lstClnConsultaOrdenado = null;

    _lstClnConsulta = new ArrayList<>();

    _lstClnConsulta.add(this.getClnIntId());
    _lstClnConsulta.add(this.getClnNome());

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

      if (cln == this.getClnIntId())
      {
        continue;
      }

      if (cln.getBooNome())
      {
        continue;
      }

      if (_lstClnConsulta.contains(cln))
      {
        continue;
      }

      _lstClnConsulta.add(cln);
    }

    return _lstClnConsulta;
  }

  public List<Coluna> getLstClnConsultaOrdenado()
  {
    if (_lstClnConsultaOrdenado != null)
    {
      return _lstClnConsultaOrdenado;
    }

    _lstClnConsultaOrdenado = this.getLstClnConsulta();

    Collections.sort(_lstClnConsultaOrdenado, new Comparator<Coluna>()
    {
      @Override
      public int compare(Coluna cln1, Coluna cln2)
      {
        return (cln1.getIntOrdem() - cln2.getIntOrdem());
      }
    });

    return _lstClnConsultaOrdenado;
  }

  public List<Coluna> getLstClnOrdenado()
  {
    if (_lstClnOrdenado != null)
    {
      return _lstClnOrdenado;
    }

    _lstClnOrdenado = this.getLstCln();

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

  public List<Filtro> getLstFilCadastro()
  {
    if (_lstFilCadastro != null)
    {
      return _lstFilCadastro;
    }

    _lstFilCadastro = new ArrayList<>();

    return _lstFilCadastro;
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

  public Tabela getTblPrincipal()
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
    this.inicializarLstCln(-1);
  }

  protected int inicializarLstCln(int intOrdem)
  {
    this.getClnBooAtivo().setIntOrdem(++intOrdem);
    this.getClnDttAlteracao().setIntOrdem(++intOrdem);
    this.getClnDttCadastro().setIntOrdem(++intOrdem);
    this.getClnIntId().setIntOrdem(++intOrdem);
    this.getClnIntUsuarioAlteracaoId().setIntOrdem(++intOrdem);
    this.getClnIntUsuarioCadastroId().setIntOrdem(++intOrdem);

    return intOrdem;
  }

  protected void inicializarLstFilConsulta(final List<Filtro> lstFilConsulta)
  {
  }

  private void iniciar()
  {
    this.inicializar();
    this.criar();
    this.criarColuna();
  }

  /**
   * Limpa os valores de todas as colunas da tabela.
   */
  public void limparColunas()
  {
    for (Coluna cln : this.getLstCln())
    {
      if (cln == null)
      {
        continue;
      }

      cln.limpar();
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

  public void setClnNome(Coluna clnNome)
  {
    _clnNome = clnNome;
  }

  public void setClnOrdem(Coluna clnOrdem)
  {
    _clnOrdem = clnOrdem;
  }

  private void setDbe(DataBase dbe)
  {
    if (_dbe == dbe)
    {
      return;
    }

    _dbe = dbe;

    _dbe.addTbl(this);
  }

  void setLstClnCadastro(List<Coluna> lstClnCadastro)
  {
    _lstClnCadastro = lstClnCadastro;
  }

  void setLstClnConsulta(List<Coluna> lstClnConsulta)
  {
    _lstClnConsulta = lstClnConsulta;
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