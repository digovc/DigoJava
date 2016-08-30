package com.digosofter.digojava.database;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.dominio.DominioMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Tabela<T extends DominioMain> extends Objeto
{
  private boolean _booPermitirAdicionar;
  private boolean _booPermitirAlterar;
  private boolean _booPermitirApagar;
  private Coluna _clnIntId;
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
  private String _strPesquisa;

  protected Tabela(String strNome, Class<T> clsDominio)
  {
    this.setClsDominio(clsDominio);
    this.setStrNome(strNome);
    this.addAppLstTbl();
    this.inicializarLstCln(-1);
  }

  protected void addAppLstTbl()
  {
    if (App.getI() == null)
    {
      return;
    }
    
    App.getI().addTbl(this);
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

  public Coluna getClnIntId()
  {
    if (_clnIntId != null)
    {
      return _clnIntId;
    }

    _clnIntId = new Coluna("int_id", this, Coluna.EnmTipo.BIGINT);

    return _clnIntId;
  }

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

  public String getStrPesquisa()
  {
    return _strPesquisa;
  }

  protected int inicializarLstCln(int intOrdem)
  {
    return intOrdem;
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

  public void setClnChavePrimaria(Coluna clnChavePrimaria)
  {
    _clnIntId = clnChavePrimaria;
  }

  public void setClnNome(Coluna clnNome)
  {
    _clnNome = clnNome;
  }

  public void setClnOrdem(Coluna clnOrdem)
  {
    _clnOrdem = clnOrdem;
  }

  protected void setClsDominio(Class<T> clsDominio)
  {
    _clsDominio = clsDominio;
  }

  public void setDbe(DataBase dbe)
  {
    _dbe = dbe;
  }

  void setLstClnCadastro(List<Coluna> lstClnCadastro)
  {
    _lstClnCadastro = lstClnCadastro;
  }

  void setLstClnConsulta(List<Coluna> lstClnConsulta)
  {
    _lstClnConsulta = lstClnConsulta;
  }

  @Override
  public void setStrNome(final String strNome)
  {
    super.setStrNome(strNome);

    if (Utils.getBooStrVazia(strNome))
    {
      return;
    }

    this.setStrNomeExibicao(strNome.replace("tbl_", ""));
  }

  public void setStrPesquisa(String strPesquisa)
  {
    _strPesquisa = strPesquisa;
  }
}