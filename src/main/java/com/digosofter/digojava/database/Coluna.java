package com.digosofter.digojava.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.OnValorAlteradoArg;
import com.digosofter.digojava.OnValorAlteradoListener;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public class Coluna extends Objeto
{

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
    NUMERICO,
    TEMPORAL,
  }

  private boolean _booChavePrimaria;
  private boolean _booClnDominioValorCarregado;
  private boolean _booNome;
  private boolean _booNotNull;
  private boolean _booObrigatorio;
  private boolean _booOnDeleteCascade;
  private boolean _booOnUpdateCascade;
  private boolean _booOrdem;
  private boolean _booOrdemDecrescente;
  private boolean _booSenha;
  private boolean _booVisivelCadastro = true;
  private boolean _booVisivelConsulta;
  private boolean _booVisivelDetalhe = true;
  private Coluna _clnRef;
  private EnmTipo _enmTipo;
  private EnmTipoGrupo _enmTipoGrupo;
  private int _intFrmLinha = 1;
  private int _intFrmLinhaPeso = 1;
  private int _intOrdem;
  private int _intTamanhoCampo = 100;
  private List<OnValorAlteradoListener> _lstEvtOnValorAlteradoListener;
  private LinkedHashMap<Integer, String> _mapOpcao;
  private String _sqlNome;
  private String _sqlSubSelectClnRef;
  private String _strDominioNome;
  private String _strTblNomeClnNome;
  private String _strValor;
  private String _strValorAnterior;
  private String _strValorDefault;
  private String _strValorExibicao;
  private String _strValorSql;
  private Tabela<?> _tbl;

  public Coluna(String strNome, Tabela<?> tbl, EnmTipo enmTipo)
  {
    try
    {
      this.setStrNome(strNome);
      this.setTbl(tbl);
      this.setEnmTipo(enmTipo);
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(120), ex);
    }
    finally
    {
    }
  }

  public void addEvtOnValorAlteradoListener(OnValorAlteradoListener evt)
  {
    try
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
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  /**
   * Adiciona uma opção para lista, tornando o campo selecionável por
   * "Combobox".
   */
  public void addOpcao(int intValor, String strNome)
  {
    try
    {
      if (this.getMapOpcao().containsKey(intValor))
      {
        return;
      }
      this.getMapOpcao().put(intValor, strNome);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  private void atualizarStrValor()
  {
    try
    {
      this.setStrValorExibicao(null);
      this.dispararEvtOnValorAlteradoListener();
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  private void dispararEvtOnValorAlteradoListener()
  {
    OnValorAlteradoArg arg;
    try
    {
      if (this.getLstEvtOnValorAlteradoListener().isEmpty())
      {
        return;
      }
      if ((this.getStrValor() != null) ? (this.getStrValor().equals(this.getStrValorAnterior())) : (this.getStrValorAnterior() == null))
      {
        return;
      }
      arg = new OnValorAlteradoArg();
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
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  public boolean getBooChavePrimaria()
  {
    return _booChavePrimaria;
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

  public boolean getBooOrdem()
  {
    return _booOrdem;
  }

  public boolean getBooOrdemDecrescente()
  {
    return _booOrdemDecrescente;
  }

  public boolean getBooSenha()
  {
    return _booSenha;
  }

  public boolean getBooValor()
  {
    try
    {
      switch (this.getStrValor().toLowerCase())
      {
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
    catch (Exception ex)
    {
      return false;
    }
    finally
    {
    }
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
    try
    {
      return this.getStrValor().charAt(0);
    }
    catch (Exception ex)
    {
      return 0;
    }
    finally
    {
    }
  }

  public Coluna getClnRef()
  {
    return _clnRef;
  }

  public double getDblValor()
  {
    try
    {
      return Double.parseDouble(this.getStrValor());
    }
    catch (Exception ex)
    {
      return 0;
    }
    finally
    {
    }
  }

  public GregorianCalendar getDttValor()
  {
    try
    {
      if (Utils.getBooStrVazia(this.getStrValor()))
      {
        return null;
      }
      return Utils.strToDtt(this.getStrValor());
    }
    catch (Exception ex)
    {
      return null;
    }
    finally
    {
    }
  }

  public EnmTipo getEnmTipo()
  {
    try
    {
      if (_enmTipo != null)
      {
        return _enmTipo;
      }
      _enmTipo = EnmTipo.TEXT;
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
    return _enmTipo;
  }

  protected EnmTipoGrupo getEnmTipoGrupo()
  {
    try
    {
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
          _enmTipoGrupo = EnmTipoGrupo.NUMERICO;
          return _enmTipoGrupo;
        case DATE:
        case DATE_TIME:
        case TIME_WITH_TIME_ZONE:
        case TIME_WITHOUT_TIME_ZONE:
        case TIMESTAMP_WITH_TIME_ZONE:
        case TIMESTAMP_WITHOUT_TIME_ZONE:
          _enmTipoGrupo = EnmTipoGrupo.TEMPORAL;
          return _enmTipoGrupo;
        default:
          _enmTipoGrupo = EnmTipoGrupo.ALPHANUMERICO;
          return _enmTipoGrupo;
      }
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _enmTipoGrupo;
  }

  public int getIntFrmLinha()
  {
    return _intFrmLinha;
  }

  public int getIntFrmLinhaPeso()
  {
    return _intFrmLinhaPeso;
  }

  public int getIntOrdem()
  {
    return _intOrdem;
  }

  public int getIntTamanhoCampo()
  {
    return _intTamanhoCampo;
  }

  public int getIntValor()
  {
    try
    {
      return (int) this.getDblValor();
    }
    catch (Exception ex)
    {
      return 0;
    }
    finally
    {
    }
  }

  private List<OnValorAlteradoListener> getLstEvtOnValorAlteradoListener()
  {
    try
    {
      if (_lstEvtOnValorAlteradoListener != null)
      {
        return _lstEvtOnValorAlteradoListener;
      }
      _lstEvtOnValorAlteradoListener = new ArrayList<>();
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _lstEvtOnValorAlteradoListener;
  }

  public LinkedHashMap<Integer, String> getMapOpcao()
  {
    try
    {
      if (_mapOpcao != null)
      {
        return _mapOpcao;
      }
      _mapOpcao = new LinkedHashMap<Integer, String>();
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _mapOpcao;
  }

  public String getSqlNome()
  {
    try
    {
      if (!Utils.getBooStrVazia(_sqlNome))
      {
        return _sqlNome;
      }
      _sqlNome = this.getStrNomeSimplificado();
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _sqlNome;
  }

  public String getSqlSubSelectClnRef()
  {
    try
    {
      if (this.getClnRef() == null)
      {
        return null;
      }
      if (!Utils.getBooStrVazia(_sqlSubSelectClnRef))
      {
        return _sqlSubSelectClnRef;
      }
      _sqlSubSelectClnRef = "(select _tbl_ref_nome._cln_ref_nome from _tbl_ref_nome where _tbl_ref_nome._cln_ref_pk = _tbl_nome._cln_nome) _cln_nome, ";
      _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_tbl_ref_nome", this.getClnRef().getTbl().getSqlNome());
      _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_cln_ref_nome", this.getClnRef().getTbl().getClnNome().getSqlNome());
      _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_cln_ref_pk", this.getClnRef().getTbl().getClnChavePrimaria().getSqlNome());
      _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_tbl_nome", this.getTbl().getSqlNome());
      _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_cln_nome", this.getSqlNome());
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _sqlSubSelectClnRef;
  }

  protected String getSqlValorDetault()
  {
    String sqlResultado;
    try
    {
      if (Utils.getBooStrVazia(this.getStrValorDefault()))
      {
        return Utils.STR_VAZIA;
      }
      sqlResultado = "DEFAULT '_cln_valor_default'";
      sqlResultado = sqlResultado.replace("_cln_valor_default", this.getStrValorDefault());
      return sqlResultado;
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return null;
  }

  protected String getStrDominioNome()
  {
    try
    {
      if (!Utils.getBooStrVazia(_strDominioNome))
      {
        return _strDominioNome;
      }
      _strDominioNome = this.getStrNomeSimplificado();
      _strDominioNome = _strDominioNome.replace("_", Utils.STR_VAZIA);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _strDominioNome;
  }

  public String getStrNomeValor()
  {
    String strResultado;
    try
    {
      strResultado = "_cln_nome = '_cln_valor'";
      strResultado = strResultado.replace("_cln_nome", this.getSqlNome());
      strResultado = strResultado.replace("_cln_valor", this.getStrValorSql());
      return strResultado;
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return null;
  }

  public String getStrTblNomeClnNome()
  {
    try
    {
      if (_strTblNomeClnNome != null)
      {
        return _strTblNomeClnNome;
      }
      _strTblNomeClnNome = "_tbl_nome._cln_nome, ";
      _strTblNomeClnNome = _strTblNomeClnNome.replace("_tbl_nome", this.getTbl().getSqlNome());
      _strTblNomeClnNome = _strTblNomeClnNome.replace("_cln_nome", this.getSqlNome());
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _strTblNomeClnNome;
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
    try
    {
      if (_strValorExibicao != null)
      {
        return _strValorExibicao;
      }
      switch (this.getEnmTipo())
      {
        case BOOLEAN:
          _strValorExibicao = this.getBooValor() ? "Sim" : "Não";
          break;
        case CEP:
          _strValorExibicao = Utils.addMascaraCep(this.getStrValor());
          break;
        case CNPJ:
          _strValorExibicao = Utils.addMascaraCnpj(this.getStrValor());
          break;
        case CPF:
          _strValorExibicao = Utils.addMascaraCpf(this.getStrValor());
          break;
        case DATE:
          _strValorExibicao = Utils.getStrDataFormatada(this.getDttValor(), Utils.EnmDataFormato.DD_MM_YYYY);
          break;
        case DATE_TIME:
        case TIMESTAMP_WITH_TIME_ZONE:
        case TIMESTAMP_WITHOUT_TIME_ZONE:
          _strValorExibicao = Utils.getStrDataFormatada(this.getDttValor(), Utils.EnmDataFormato.HH_MM_DD_MM_YYYY);
          break;
        case DOUBLE:
        case NUMERIC:
        case REAL:
          _strValorExibicao = Utils.getStrValorNumerico(this.getDblValor());
          break;
        case EMAIL:
          _strValorExibicao = this.getStrValor() != null ? this.getStrValor().toLowerCase() : null;
          break;
        case MONEY:
          _strValorExibicao = Utils.getStrValorMonetario(this.getDblValor());
          break;
        case PERCENTUAL:
          _strValorExibicao = Utils.getStrValorPercentual(this.getDblValor());
          break;
        default:
          _strValorExibicao = this.getStrValor();
          break;
      }
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
    return _strValorExibicao;
  }

  public String getStrValorMonetario()
  {
    try
    {
      if (Utils.getBooStrVazia(this.getStrValor()))
      {
        return "R$ 0,00";
      }
      return Utils.getStrValorMonetario(Double.parseDouble(this.getStrValor()));
    }
    catch (Exception ex)
    {
      return "R$ 0,00";
    }
    finally
    {
    }
  }

  public String getStrValorSql()
  {
    try
    {
      if (Utils.getBooStrVazia(this.getStrValor()))
      {
        return null;
      }

      switch (this.getEnmTipo())
      {
        case BOOLEAN:
          _strValorSql = this.getBooValor() ? "1" : "0";
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
        default:
          _strValorSql = getStrValorSqlText();
          break;
      }
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }

    return _strValorSql;
  }

  private String getStrValorSqlText()
  {
    String strValorResultado;
    try
    {
      if (Utils.getBooStrVazia(this.getStrValor()))
      {
        return "null";
      }
      strValorResultado = this.getStrValor();
      strValorResultado = strValorResultado.replace("'", "''");
      strValorResultado = strValorResultado.trim();
      return strValorResultado;
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return null;
  }

  public Tabela<?> getTbl()
  {
    return _tbl;
  }

  public <T extends Dominio> void lerDominio(T objDominio)
  {
    try
    {
      if (objDominio == null)
      {
        return;
      }
      this.setBooClnDominioValorCarregado(false);
      this.lerDominio(objDominio, objDominio.getClass());
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  private <T extends Dominio> void lerDominio(T objDominio, Class<?> cls)
  {
    try
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
        if (objField == null)
        {
          continue;
        }
        if (this.lerDominio(objDominio, objField))
        {
          this.setBooClnDominioValorCarregado(true);
          return;
        }
      }
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  private <T extends Dominio> boolean lerDominio(T objDominio, Field objField)
  {
    try
    {
      if (objDominio == null)
      {
        return false;
      }
      if (objField == null)
      {
        return false;
      }
      if (!Utils.simplificar(objField.getName().replace("_", Utils.STR_VAZIA)).equals(this.getStrDominioNome()))
      {
        return false;
      }
      objField.setAccessible(true);
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
          this.setDttValor((GregorianCalendar) objField.get(objDominio));
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
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
      objField.setAccessible(false);
    }
    return false;
  }

  public void limpar()
  {
    try
    {
      this.setStrValor(this.getStrValorDefault());
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  public void removerEvtOnValorAlteradoListener(OnValorAlteradoListener evt)
  {
    try
    {
      if (evt == null)
      {
        return;
      }
      this.getLstEvtOnValorAlteradoListener().remove(evt);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  public void setBooChavePrimaria(boolean booChavePrimaria)
  {
    try
    {
      _booChavePrimaria = booChavePrimaria;
      if (!_booChavePrimaria)
      {
        this.getTbl().setClnChavePrimaria(null);
        return;
      }
      if (!this.equals(this.getTbl().getClnChavePrimaria()))
      {
        this.getTbl().getClnChavePrimaria()._booChavePrimaria = false;
      }
      this.getTbl().setClnChavePrimaria(this);
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }

  private void setBooClnDominioValorCarregado(boolean booClnDominioValorCarregado)
  {
    _booClnDominioValorCarregado = booClnDominioValorCarregado;
  }

  public void setBooNome(boolean booNome)
  {
    try
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
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }

  public void setBooNotNull(boolean booNotNull)
  {
    _booNotNull = booNotNull;
  }

  /**
   * Indica se o valor desta coluna não pode estar vazio quando for salvar um
   * registro.
   *
   * @param booObrigatorio
   *          Indica se o valor desta coluna não pode estar vazio quando for
   *          salvar um registro.
   */
  public void setBooObrigatorio(boolean booObrigatorio)
  {
    _booObrigatorio = booObrigatorio;
  }

  /**
   * Se esta coluna for referência para outra coluna, indica se o registro será
   * apagado caso o registro pai também o seja.
   *
   * @param booOnDeleteCascade
   *          Indica se o registro será apagado caso o registro pai também o
   *          seja.
   */
  public void setBooOnDeleteCascade(boolean booOnDeleteCascade)
  {
    _booOnDeleteCascade = booOnDeleteCascade;
  }

  /**
   * Se esta coluna for referência para outra coluna, indica se o valor será
   * atualizado caso o registro pai também o seja.
   *
   * @param booOnUpdateCascade
   *          Indica se o valor será atualizado caso o registro pai também o
   *          seja.
   */
  public void setBooOnUpdateCascade(boolean booOnUpdateCascade)
  {
    _booOnUpdateCascade = booOnUpdateCascade;
  }

  public void setBooOrdem(boolean booOrdem)
  {
    try
    {
      _booOrdem = booOrdem;
      if (!_booOrdem)
      {
        this.getTbl().setClnOrdem(null);
        return;
      }
      if (!this.equals(this.getTbl().getClnOrdem()))
      {
        this.getTbl().getClnOrdem()._booOrdem = false;
      }
      this.getTbl().setClnOrdem(this);
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }

  public void setBooOrdemDecrescente(boolean booOrdemDecrescente)
  {
    _booOrdemDecrescente = booOrdemDecrescente;
  }

  public void setBooSenha(boolean booSenha)
  {
    _booSenha = booSenha;
  }

  public void setBooValor(boolean booValor)
  {
    try
    {
      this.setStrValor(String.valueOf(booValor));
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }

  public void setBooVisivelCadastro(boolean booVisivelCadastro)
  {
    try
    {
      _booVisivelCadastro = booVisivelCadastro;
      if (!_booVisivelCadastro)
      {
        return;
      }
      this.getTbl().setLstClnCadastro(null);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  public void setBooVisivelConsulta(boolean booVisivelConsulta)
  {
    try
    {
      _booVisivelConsulta = booVisivelConsulta;

      if (!_booVisivelConsulta)
      {
        return;
      }

      this.getTbl().setLstClnConsulta(null);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  /**
   * Indica se esta coluna será vista nas telas de detalhes.
   *
   * @param booVisivelDetalhe
   *          Indica se esta coluna será vista nas telas de detalhes.
   */
  public void setBooVisivelDetalhe(boolean booVisivelDetalhe)
  {
    _booVisivelDetalhe = booVisivelDetalhe;
  }

  public void setChrValor(char chrValor)
  {
    try
    {
      this.setStrValor(String.valueOf(chrValor));
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }

  public void setClnRef(Coluna clnRef)
  {
    _clnRef = clnRef;
  }

  public void setDblValor(double dblValor)
  {
    try
    {
      this.setStrValor(String.valueOf(dblValor));
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }

  public void setDttValor(Calendar dttValor)
  {
    try
    {
      if (dttValor == null)
      {
        this.setStrValor(Utils.STR_VAZIA);
        return;
      }
      this.setStrValor(Utils.getStrDataFormatada(dttValor, Utils.EnmDataFormato.YYYY_MM_DD_HH_MM_SS));
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
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

  public void setIntOrdem(int intOrdem)
  {
    _intOrdem = intOrdem;
  }

  public void setIntTamanhoCampo(int intTamanhoCampo)
  {
    _intTamanhoCampo = intTamanhoCampo;
  }

  public void setIntValor(int intValor)
  {
    try
    {
      this.setStrValor(String.valueOf(intValor));
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }

  public void setStrDominioNome(String strDominioNome)
  {
    _strDominioNome = strDominioNome;
  }

  @Override
  public void setStrNome(String strNome)
  {
    super.setStrNome(strNome);
    try
    {
      if (Utils.getBooStrVazia(strNome))
      {
        return;
      }
      if (strNome.length() < 5)
      {
        return;
      }
      strNome = strNome.substring(4);
      strNome = strNome.replace("_id", "");
      this.setStrNomeExibicao(strNome);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  public void setStrValor(String strValor)
  {
    try
    {
      this.setStrValorAnterior(_strValor);
      _strValor = strValor;
      this.atualizarStrValor();
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
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

  public void setTbl(Tabela<?> tbl)
  {
    try
    {
      _tbl = tbl;
      if (_tbl == null)
      {
        return;
      }
      _tbl.addCln(this);
    }
    catch (Exception ex)
    {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally
    {
    }
  }
}