package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.OnValorAlteradoArg;
import com.digosofter.digojava.OnValorAlteradoListener;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.dominio.DominioMain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Coluna extends Objeto
{
  public enum EnmOrdem
  {
    CRESCENTE,
    DECRESCENTE,
    NONE,
  }

  public enum EnmTipo
  {
    BIGINT,
    BIGSERIAL,
    BOOLEAN,
    CEP,
    CHAR,
    CNPJ,
    CPF,
    DATE,
    DATE_TIME,
    DECIMAL,
    DOUBLE,
    EMAIL,
    FLOAT,
    INTEGER,
    INTERVAL,
    MONEY,
    NUMERIC,
    PERCENTUAL,
    REAL,
    SERIAL,
    SMALLINT,
    TEXT,
    TIME_WITH_TIME_ZONE,
    TIME_WITHOUT_TIME_ZONE,
    TIMESTAMP_WITH_TIME_ZONE,
    TIMESTAMP_WITHOUT_TIME_ZONE,
    VARCHAR,
    XML,
  }

  public enum EnmTipoGrupo
  {
    ALPHANUMERICO,
    NONE,
    NUMERICO,
    TEMPORAL,
  }

  public static final String STR_VALOR_NULO = "<<<<<null>>>>>";
  private boolean _booClnDominioValorCarregado;
  private boolean _booNome;
  private boolean _booNotNull;
  private boolean _booObrigatorio;
  private boolean _booOnDeleteCascade;
  private boolean _booOnUpdateCascade;
  private boolean _booSenha;
  private boolean _booValorDefault;
  private boolean _booVisivelCadastro = true;
  private boolean _booVisivelConsulta;
  private boolean _booVisivelDetalhe = true;
  private Coluna _clnRef;
  private double _dblValorDefault;
  private EnmOrdem _enmOrdem = EnmOrdem.NONE;
  private EnmTipo _enmTipo;
  private EnmTipoGrupo _enmTipoGrupo;
  private int _intFrmLinha = 1;
  private int _intFrmLinhaPeso = 1;
  private int _intTamanhoCampo = 100;
  private int _intValorDefault;
  private List<OnValorAlteradoListener> _lstEvtOnValorAlteradoListener;
  private LinkedHashMap<Integer, String> _mapOpcao;
  private String _sqlNome;
  private String _sqlNomeInsert;
  private String _sqlSelect;
  private String _sqlSubSelect;
  private String _sqlUpdate;
  private String _sqlValor;
  private String _sqlValorDetault;
  private String _sqlValorInsert;
  private String _strDominioNome;
  private String _strNomeExibicao;
  private String _strNomeValor;
  private String _strValor;
  private String _strValorAnterior;
  private String _strValorDefault;
  private String _strValorExibicao;
  private String _strValorMonetario;
  private TabelaMain<?> _tbl;

  public Coluna(String strNome, TabelaMain<?> tbl, EnmTipo enmTipo)
  {
    this(strNome, tbl, enmTipo, null);
  }

  public Coluna(String strNome, TabelaMain<?> tbl, EnmTipo enmTipo, Coluna clnRef)
  {
    this.setClnRef(clnRef);
    this.setEnmTipo(enmTipo);
    this.setStrNome(strNome);
    this.setTbl(tbl);
  }

  public void addEvtOnValorAlteradoListener(OnValorAlteradoListener evt)
  {
    if (evt == null)
    {
      return;
    }

    if (this.getLstEvtOnValorAlteradoListener().contains(evt))
    {
      return;
    }

    this.getLstEvtOnValorAlteradoListener().add(evt);
  }

  /**
   * Adiciona uma opção para lista, tornando o campo selecionável por "ComboBox".
   */
  public void addOpcao(int intValor, String strNome)
  {
    if (Utils.getBooStrVazia(strNome))
    {
      return;
    }

    if (this.getMapOpcao().containsKey(intValor))
    {
      return;
    }

    this.getMapOpcao().put(intValor, strNome);
  }

  protected void atualizarBooValorDefault(final boolean booValorDefault)
  {
    this.setStrValorDefault(booValorDefault ? "true" : "false");
  }

  private void atualizarIntValorDefault(final int intValorDefault)
  {
    this.setStrValorDefault(String.valueOf(intValorDefault));
  }

  private void atualizarSqlValor(final String sqlValor)
  {
    this.setSqlNomeInsert(null);
    this.setSqlUpdate(null);
    this.setSqlValorInsert(null);
  }

  private void atualizarStrValor(final String strValor)
  {
    this.setStrNomeValor(null);
    this.setStrValorExibicao(null);
    this.setStrValorMonetario(null);
    this.setSqlValor(null);

    this.dispararEvtOnValorAlteradoListener();
  }

  public void carregarValorDefault()
  {
    if (!this.getBooVazia())
    {
      return;
    }

    if (Utils.getBooStrVazia(this.getStrValorDefault()))
    {
      return;
    }

    this.setStrValor(this.getStrValorDefault());
  }

  public void criar()
  {
  }

  private void dispararEvtOnValorAlteradoListener()
  {
    if (this.getLstEvtOnValorAlteradoListener().isEmpty())
    {
      return;
    }

    if ((this.getStrValor() != null) ? (this.getStrValor().equals(this.getStrValorAnterior())) : (this.getStrValorAnterior() == null))
    {
      return;
    }

    OnValorAlteradoArg arg = new OnValorAlteradoArg();

    arg.setStrValor(this.getStrValor());
    arg.setStrValorAnterior(this.getStrValorAnterior());

    for (OnValorAlteradoListener evt : this.getLstEvtOnValorAlteradoListener())
    {
      if (evt == null)
      {
        continue;
      }

      evt.onValorAlterado(this, arg);
    }
  }

  public boolean getBooChavePrimaria()
  {
    if (this.getTbl() == null)
    {
      return false;
    }

    return this.equals(this.getTbl().getClnIntId());
  }

  private boolean getBooClnDominioValorCarregado()
  {
    return _booClnDominioValorCarregado;
  }

  public boolean getBooNome()
  {
    return _booNome;
  }

  public boolean getBooNotNull()
  {
    return _booNotNull;
  }

  protected boolean getBooObrigatorio()
  {
    return _booObrigatorio;
  }

  protected boolean getBooOnDeleteCascade()
  {
    return _booOnDeleteCascade;
  }

  protected boolean getBooOnUpdateCascade()
  {
    return _booOnUpdateCascade;
  }

  public boolean getBooSenha()
  {
    return _booSenha;
  }

  public boolean getBooValor()
  {
    return Utils.getBoo(this.getStrValor());
  }

  public boolean getBooValorDefault()
  {
    _booValorDefault = Utils.getBoo(this.getStrValorDefault());

    return _booValorDefault;
  }

  /**
   * Retorna booleano indicando se o valor desta coluna está vazia.
   *
   * @return Booleano indicando se o valor desta coluna está vazia.
   */
  public boolean getBooVazia()
  {
    return Utils.getBooStrVazia(this.getStrValor());
  }

  public boolean getBooVisivelCadastro()
  {
    return _booVisivelCadastro;
  }

  public boolean getBooVisivelConsulta()
  {
    return _booVisivelConsulta;
  }

  public boolean getBooVisivelDetalhe()
  {
    return _booVisivelDetalhe;
  }

  public char getChrValor()
  {
    if (Utils.getBooStrVazia(this.getStrValor()))
    {
      return 0;
    }

    if (this.getStrValor().length() < 1)
    {
      return 0;
    }

    return this.getStrValor().charAt(0);
  }

  public Coluna getClnRef()
  {
    return _clnRef;
  }

  public double getDblValor()
  {
    if (Utils.getBooStrVazia(this.getStrValor()))
    {
      return 0;
    }

    return Double.parseDouble(this.getStrValor());
  }

  public double getDblValorDefault()
  {
    if (Utils.getBooStrVazia(this.getStrValorDefault()))
    {
      return 0;
    }

    _dblValorDefault = Double.valueOf(this.getStrValorDefault());

    return _dblValorDefault;
  }

  public GregorianCalendar getDttValor()
  {
    if (Utils.getBooStrVazia(this.getStrValor()))
    {
      return null;
    }

    return Utils.strToDtt(this.getStrValor());
  }

  public EnmOrdem getEnmOrdem()
  {
    return _enmOrdem;
  }

  public EnmTipo getEnmTipo()
  {
    if (_enmTipo != null)
    {
      return _enmTipo;
    }

    _enmTipo = EnmTipo.TEXT;

    return _enmTipo;
  }

  protected EnmTipoGrupo getEnmTipoGrupo()
  {
    if (_enmTipoGrupo != null)
    {
      return _enmTipoGrupo;
    }

    switch (this.getEnmTipo())
    {
      case BIGINT:
      case BIGSERIAL:
      case DECIMAL:
      case DOUBLE:
      case INTEGER:
      case INTERVAL:
      case MONEY:
      case NUMERIC:
      case REAL:
      case SERIAL:
      case SMALLINT:
        return _enmTipoGrupo = EnmTipoGrupo.NUMERICO;

      case DATE:
      case DATE_TIME:
      case TIME_WITH_TIME_ZONE:
      case TIME_WITHOUT_TIME_ZONE:
      case TIMESTAMP_WITH_TIME_ZONE:
      case TIMESTAMP_WITHOUT_TIME_ZONE:
        return _enmTipoGrupo = EnmTipoGrupo.TEMPORAL;

      default:
        return _enmTipoGrupo = EnmTipoGrupo.ALPHANUMERICO;
    }
  }

  public int getIntFrmLinha()
  {
    return _intFrmLinha;
  }

  public int getIntFrmLinhaPeso()
  {
    return _intFrmLinhaPeso;
  }

  public int getIntTamanhoCampo()
  {
    return _intTamanhoCampo;
  }

  public int getIntValor()
  {
    return (int) this.getDblValor();
  }

  public int getIntValorDefault()
  {
    _intValorDefault = (int) this.getDblValorDefault();

    return _intValorDefault;
  }

  private List<OnValorAlteradoListener> getLstEvtOnValorAlteradoListener()
  {
    if (_lstEvtOnValorAlteradoListener != null)
    {
      return _lstEvtOnValorAlteradoListener;
    }

    _lstEvtOnValorAlteradoListener = new ArrayList<>();

    return _lstEvtOnValorAlteradoListener;
  }

  public LinkedHashMap<Integer, String> getMapOpcao()
  {
    if (_mapOpcao != null)
    {
      return _mapOpcao;
    }

    _mapOpcao = new LinkedHashMap<>();

    return _mapOpcao;
  }

  public String getSqlNome()
  {
    if (_sqlNome != null)
    {
      return _sqlNome;
    }

    _sqlNome = this.getStrNomeSimplificado();

    return _sqlNome;
  }

  public String getSqlNomeInsert()
  {
    if (_sqlNomeInsert != null)
    {
      return _sqlNomeInsert;
    }

    if (this.getBooVazia())
    {
      return null;
    }

    if ((this.getBooChavePrimaria()) && (this.getIntValor() < 1))
    {
      return null;
    }

    if ((this.getClnRef() != null) && (this.getIntValor() < 1))
    {
      return null;
    }

    return _sqlNomeInsert = String.format("%s, ", this.getSqlNome());
  }

  public String getSqlSelect()
  {
    if (_sqlSelect != null)
    {
      return _sqlSelect;
    }

    return _sqlSelect = String.format("%s.%s, ", this.getTbl().getSqlNome(), this.getSqlNome());
  }

  public String getSqlSubSelect()
  {
    if (_sqlSubSelect != null)
    {
      return _sqlSubSelect;
    }

    if (this.getClnRef() == null)
    {
      return null;
    }

    _sqlSubSelect = "(select _tbl_ref_nome._cln_ref_nome from _tbl_ref_nome where _tbl_ref_nome._cln_ref_pk = _tbl_nome._cln_nome) _cln_nome, ";

    _sqlSubSelect = _sqlSubSelect.replace("_tbl_ref_nome", this.getClnRef().getTbl().getSqlNome());
    _sqlSubSelect = _sqlSubSelect.replace("_cln_ref_nome", this.getClnRef().getTbl().getClnNome().getSqlNome());
    _sqlSubSelect = _sqlSubSelect.replace("_cln_ref_pk", this.getClnRef().getTbl().getClnIntId().getSqlNome());
    _sqlSubSelect = _sqlSubSelect.replace("_tbl_nome", this.getTbl().getSqlNome());
    _sqlSubSelect = _sqlSubSelect.replace("_cln_nome", this.getSqlNome());

    return _sqlSubSelect;
  }

  public String getSqlUpdate()
  {
    if (_sqlUpdate != null)
    {
      return _sqlUpdate;
    }

    if (this.getBooVazia())
    {
      return null;
    }

    if (this.getBooChavePrimaria())
    {
      return null;
    }

    if ((this.getClnRef() != null) && (this.getIntValor() < 1))
    {
      return null;
    }

    return _sqlUpdate = String.format("%s = %s, ", this.getSqlNome(), this.getSqlValor());
  }

  public String getSqlValor()
  {
    if (_sqlValor != null)
    {
      return _sqlValor;
    }

    if (STR_VALOR_NULO.equals(this.getStrValor()))
    {
      return (_sqlValor = "null");
    }

    switch (this.getEnmTipo())
    {
      case BOOLEAN:
        return (_sqlValor = this.getBooValor() ? "1" : "0");
    }

    switch (this.getEnmTipoGrupo())
    {
      case NUMERICO:
        return (_sqlValor = this.getStrValor());

      default:
        return (_sqlValor = this.getSqlValorAlfanumerico());
    }
  }

  private String getSqlValorAlfanumerico()
  {
    if (Utils.getBooStrVazia(this.getStrValor()))
    {
      return null;
    }

    String strResultado = this.getStrValor();

    strResultado = strResultado.replace("'", "''");

    return String.format("'%s'", strResultado);
  }

  protected String getSqlValorDetault()
  {
    if (_sqlValorDetault != null)
    {
      return _sqlValorDetault;
    }

    if (Utils.getBooStrVazia(this.getStrValorDefault()))
    {
      return null;
    }

    _sqlValorDetault = "default _cln_valor_default".replace("_cln_valor_default", this.getStrValorDefault());

    return _sqlValorDetault;
  }

  public String getSqlValorInsert()
  {
    if (_sqlValorInsert != null)
    {
      return _sqlValorInsert;
    }

    if (this.getBooVazia())
    {
      return null;
    }

    if ((this.getBooChavePrimaria()) && (this.getIntValor() < 1))
    {
      return null;
    }

    if ((this.getClnRef() != null) && (this.getIntValor() < 1))
    {
      return null;
    }

    return _sqlValorInsert = String.format("%s, ", this.getSqlValor());
  }

  protected String getStrDominioNome()
  {
    if (_strDominioNome != null)
    {
      return _strDominioNome;
    }

    if (Utils.getBooStrVazia(this.getStrNomeSimplificado()))
    {
      return null;
    }

    _strDominioNome = this.getStrNomeSimplificado().replace("_", Utils.STR_VAZIA);

    return _strDominioNome;
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

    _strNomeExibicao = _strNomeExibicao.replace("_id", Utils.STR_VAZIA);
    _strNomeExibicao = _strNomeExibicao.replace("_", " ");

    _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(_strNomeExibicao);

    return _strNomeExibicao;
  }

  private String getStrNomeValor()
  {
    if (_strNomeValor != null)
    {
      return _strNomeValor;
    }

    _strNomeValor = "_cln_nome = '_cln_valor'";

    _strNomeValor = _strNomeValor.replace("_cln_nome", this.getSqlNome());
    _strNomeValor = _strNomeValor.replace("_cln_valor", this.getSqlValor());

    return _strNomeValor;
  }

  public String getStrValor()
  {
    return _strValor;
  }

  private String getStrValorAnterior()
  {
    return _strValorAnterior;
  }

  public String getStrValorDefault()
  {
    return _strValorDefault;
  }

  public String getStrValorExibicao()
  {
    if (_strValorExibicao != null)
    {
      return _strValorExibicao;
    }

    String strValorExibicaoOpcao = this.getStrValorExibicaoOpcao();

    if (!Utils.getBooStrVazia(strValorExibicaoOpcao))
    {
      return _strValorExibicao = strValorExibicaoOpcao;
    }

    switch (this.getEnmTipo())
    {
      case BOOLEAN:
        return _strValorExibicao = this.getBooValor() ? "Sim" : "Não";

      case CEP:
        return _strValorExibicao = Utils.addMascaraCep(this.getStrValor());

      case CNPJ:
        return _strValorExibicao = Utils.addMascaraCnpj(this.getStrValor());

      case CPF:
        return _strValorExibicao = Utils.addMascaraCpf(this.getStrValor());

      case DATE:
        return _strValorExibicao = Utils.getStrDataFormatada(this.getDttValor(), Utils.EnmDataFormato.DD_MM_YYYY);

      case DATE_TIME:
      case TIMESTAMP_WITH_TIME_ZONE:
      case TIMESTAMP_WITHOUT_TIME_ZONE:
        return _strValorExibicao = Utils.getStrDataFormatada(this.getDttValor(), Utils.EnmDataFormato.HH_MM_DD_MM_YYYY);

      case DOUBLE:
      case NUMERIC:
      case REAL:
        return _strValorExibicao = Utils.getStrValorNumerico(this.getDblValor());

      case EMAIL:
        return _strValorExibicao = this.getStrValor() != null ? this.getStrValor().toLowerCase() : null;

      case MONEY:
        return _strValorExibicao = Utils.getStrValorMonetario(this.getDblValor());

      case PERCENTUAL:
        return _strValorExibicao = Utils.getStrValorPercentual(this.getDblValor());

      default:
        return _strValorExibicao = this.getStrValor();
    }
  }

  private String getStrValorExibicaoOpcao()
  {
    if (this.getMapOpcao().isEmpty())
    {
      return null;
    }

    for (Map.Entry<Integer, String> itmOpcao : this.getMapOpcao().entrySet())
    {
      if (this.getIntValor() != itmOpcao.getKey())
      {
        continue;
      }

      return itmOpcao.getValue();
    }

    return null;
  }

  private String getStrValorMonetario()
  {
    if (_strValorMonetario != null)
    {
      return _strValorMonetario;
    }

    _strValorMonetario = Utils.getStrValorMonetario(Double.parseDouble(this.getStrValor()));

    return _strValorMonetario;
  }

  private String getStrValorSqlText()
  {
    if (Utils.getBooStrVazia(this.getStrValor()))
    {
      return "null";
    }

    String strValorResultado = this.getStrValor();

    strValorResultado = strValorResultado.replace("'", "''");
    strValorResultado = strValorResultado.trim();

    return strValorResultado;
  }

  public TabelaMain<?> getTbl()
  {
    return _tbl;
  }

  public <T extends DominioMain> void lerDominio(T objDominio)
  {
    if (objDominio == null)
    {
      return;
    }

    this.setBooClnDominioValorCarregado(false);

    this.lerDominio(objDominio, objDominio.getClass());
  }

  private <T extends DominioMain> void lerDominio(T objDominio, Class<?> cls)
  {
    if (cls == null)
    {
      return;
    }

    this.lerDominio(objDominio, cls.getSuperclass());

    if (this.getBooClnDominioValorCarregado())
    {
      return;
    }

    for (Field objField : cls.getDeclaredFields())
    {
      if (!this.lerDominio(objDominio, objField))
      {
        continue;
      }

      this.setBooClnDominioValorCarregado(true);
      return;
    }
  }

  private <T extends DominioMain> boolean lerDominio(T objDominio, Field objField)
  {
    if (!objField.getName().replace("_", Utils.STR_VAZIA).toLowerCase().equals(this.getStrDominioNome()))
    {
      return false;
    }

    objField.setAccessible(true);

    try
    {
      switch (this.getEnmTipo())
      {
        case BOOLEAN:
          this.setBooValor((boolean) objField.get(objDominio));
          return true;

        case DATE:
        case DATE_TIME:
        case INTERVAL:
        case TIME_WITH_TIME_ZONE:
        case TIME_WITHOUT_TIME_ZONE:
        case TIMESTAMP_WITH_TIME_ZONE:
        case TIMESTAMP_WITHOUT_TIME_ZONE:
          this.setDttValor((Calendar) objField.get(objDominio));
          return true;

        case DECIMAL:
        case DOUBLE:
        case FLOAT:
        case MONEY:
        case NUMERIC:
        case PERCENTUAL:
        case REAL:
          this.setDblValor((double) objField.get(objDominio));
          return true;

        case BIGINT:
        case BIGSERIAL:
        case INTEGER:
        case SMALLINT:
          this.setIntValor((int) objField.get(objDominio));
          return true;

        default:
          this.setStrValor((String) objField.get(objDominio));
          return true;
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      objField.setAccessible(false);
    }

    return false;
  }

  public void limpar()
  {
    this.setStrValor(this.getStrValorDefault());
  }

  protected void limparOrdem()
  {
    this.setEnmOrdem(Coluna.EnmOrdem.NONE);
  }

  public void removerEvtOnValorAlteradoListener(OnValorAlteradoListener evt)
  {
    if (evt == null)
    {
      return;
    }

    this.getLstEvtOnValorAlteradoListener().remove(evt);
  }

  private void setBooClnDominioValorCarregado(boolean booClnDominioValorCarregado)
  {
    _booClnDominioValorCarregado = booClnDominioValorCarregado;
  }

  public void setBooNome(boolean booNome)
  {
    _booNome = booNome;

    if (!_booNome)
    {
      this.getTbl().setClnNome(null);
      return;
    }

    if (!this.equals(this.getTbl().getClnNome()))
    {
      this.getTbl().getClnNome()._booNome = false;
    }

    this.getTbl().setClnNome(this);
  }

  public void setBooNotNull(boolean booNotNull)
  {
    _booNotNull = booNotNull;
  }

  /**
   * Indica se o valor desta coluna não pode estar vazio quando for salvar um registro.
   *
   * @param booObrigatorio Indica se o valor desta coluna não pode estar vazio quando for salvar um registro.
   */
  public void setBooObrigatorio(boolean booObrigatorio)
  {
    _booObrigatorio = booObrigatorio;
  }

  /**
   * Se esta coluna for referência para outra coluna, indica se o registro será apagado caso o registro pai também o seja.
   *
   * @param booOnDeleteCascade Indica se o registro será apagado caso o registro pai também o seja.
   */
  public void setBooOnDeleteCascade(boolean booOnDeleteCascade)
  {
    _booOnDeleteCascade = booOnDeleteCascade;
  }

  /**
   * Se esta coluna for referência para outra coluna, indica se o valor será atualizado caso o registro pai também o seja.
   *
   * @param booOnUpdateCascade Indica se o valor será atualizado caso o registro pai também o seja.
   */
  public void setBooOnUpdateCascade(boolean booOnUpdateCascade)
  {
    _booOnUpdateCascade = booOnUpdateCascade;
  }

  public void setBooSenha(boolean booSenha)
  {
    _booSenha = booSenha;
  }

  public void setBooValor(boolean booValor)
  {
    this.setStrValor(String.valueOf(booValor));
  }

  public void setBooValorDefault(boolean booValorDefault)
  {
    _booValorDefault = booValorDefault;

    this.atualizarBooValorDefault(_booValorDefault);
  }

  public void setBooVisivelCadastro(boolean booVisivelCadastro)
  {
    _booVisivelCadastro = booVisivelCadastro;

    if (!_booVisivelCadastro)
    {
      return;
    }

    this.getTbl().setLstClnCadastro(null);
  }

  public void setBooVisivelConsulta(boolean booVisivelConsulta)
  {
    _booVisivelConsulta = booVisivelConsulta;

    if (!_booVisivelConsulta)
    {
      return;
    }

    this.getTbl().setLstClnConsulta(null);
  }

  /**
   * Indica se esta coluna será vista nas telas de detalhes.
   *
   * @param booVisivelDetalhe Indica se esta coluna será vista nas telas de detalhes.
   */
  public void setBooVisivelDetalhe(boolean booVisivelDetalhe)
  {
    _booVisivelDetalhe = booVisivelDetalhe;
  }

  public void setChrValor(char chrValor)
  {
    this.setStrValor(String.valueOf(chrValor));
  }

  private void setClnRef(Coluna clnRef)
  {
    _clnRef = clnRef;
  }

  public void setDblValor(double dblValor)
  {
    this.setStrValor(String.valueOf(dblValor));
  }

  public void setDblValorDefault(double dblValorDefault)
  {
    _dblValorDefault = dblValorDefault;

    this.setStrValorDefault(String.valueOf(_dblValorDefault));
  }

  public void setDttValor(Calendar dttValor)
  {
    if (dttValor == null)
    {
      this.setStrValor(null);
      return;
    }

    this.setStrValor(Utils.getStrDataFormatada(dttValor, Utils.EnmDataFormato.YYYY_MM_DD_HH_MM_SS));
  }

  public void setEnmOrdem(EnmOrdem enmOrdem)
  {
    if (_enmOrdem == enmOrdem)
    {
      return;
    }

    _enmOrdem = enmOrdem;

    if (this.getTbl() == null)
    {
      return;
    }

    this.getTbl().addClnOrdem(this);
  }

  private void setEnmTipo(EnmTipo enmTipo)
  {
    _enmTipo = enmTipo;
  }

  public void setIntFrmLinha(int intFrmLinha)
  {
    _intFrmLinha = intFrmLinha;
  }

  public void setIntFrmLinhaPeso(int intFrmLinhaPeso)
  {
    _intFrmLinhaPeso = intFrmLinhaPeso;
  }

  public void setIntTamanhoCampo(int intTamanhoCampo)
  {
    _intTamanhoCampo = intTamanhoCampo;
  }

  public void setIntValor(int intValor)
  {
    if ((intValor < 1) && (this.getClnRef() != null))
    {
      return;
    }

    this.setStrValor(String.valueOf(intValor));
  }

  public void setIntValorDefault(Integer intValorDefault)
  {
    _intValorDefault = intValorDefault;

    this.atualizarIntValorDefault(_intValorDefault);
  }

  private void setSqlNomeInsert(final String sqlNomeInsert)
  {
    _sqlNomeInsert = sqlNomeInsert;
  }

  private void setSqlUpdate(final String sqlUpdate)
  {
    _sqlUpdate = sqlUpdate;
  }

  private void setSqlValor(String sqlValor)
  {
    if (_sqlValor == sqlValor)
    {
      return;
    }

    _sqlValor = sqlValor;

    this.atualizarSqlValor(sqlValor);
  }

  private void setSqlValorInsert(String sqlValorInsert)
  {
    _sqlValorInsert = sqlValorInsert;
  }

  public void setStrDominioNome(String strDominioNome)
  {
    _strDominioNome = strDominioNome;
  }

  private void setStrNomeValor(String strNomeValor)
  {
    _strNomeValor = strNomeValor;
  }

  public void setStrValor(String strValor)
  {
    if (_strValor == strValor)
    {
      return;
    }

    this.setStrValorAnterior(_strValor);

    _strValor = strValor;

    this.atualizarStrValor(strValor);
  }

  private void setStrValorAnterior(String strValorAnterior)
  {
    _strValorAnterior = strValorAnterior;
  }

  public void setStrValorDefault(String strValorDefault)
  {
    _strValorDefault = strValorDefault;
  }

  private void setStrValorExibicao(String strValorExibicao)
  {
    _strValorExibicao = strValorExibicao;
  }

  private void setStrValorMonetario(String strValorMonetario)
  {
    _strValorMonetario = strValorMonetario;
  }

  public void setTbl(TabelaMain<?> tbl)
  {
    _tbl = tbl;
  }

  @Override
  public String toString()
  {
    return String.format("%s: %s", this.getSqlNome(), this.getStrValor());
  }
}