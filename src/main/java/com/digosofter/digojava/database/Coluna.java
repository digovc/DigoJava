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
    NONE,
    NUMERICO,
    TEMPORAL,
  }

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
  private String _sqlValorDetault;
  private String _strDominioNome;
  private String _strNomeValor;
  private String _strTblNomeClnNome;
  private String _strValor;
  private String _strValorAnterior;
  private String _strValorDefault;
  private String _strValorExibicao;
  private String _strValorMonetario;
  private String _strValorSql;
  private Tabela<?> _tbl;

  public Coluna(String strNome, Tabela<?> tbl, EnmTipo enmTipo)
  {
    this(strNome, tbl, enmTipo, null);
  }

  public Coluna(String strNome, Tabela<?> tbl, EnmTipo enmTipo, Coluna clnRef)
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
    return Utils.getBoo(this.getStrValor());
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

  public GregorianCalendar getDttValor()
  {
    if (Utils.getBooStrVazia(this.getStrValor()))
    {
      return null;
    }

    return Utils.strToDtt(this.getStrValor());
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
    if (_enmTipoGrupo != EnmTipoGrupo.NONE)
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
    return (int) this.getDblValor();
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

    _mapOpcao = new LinkedHashMap<Integer, String>();

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

  public String getSqlSubSelectClnRef()
  {
    if (_sqlSubSelectClnRef != null)
    {
      return _sqlSubSelectClnRef;
    }

    if (this.getClnRef() == null)
    {
      return null;
    }

    _sqlSubSelectClnRef = "(select _tbl_ref_nome._cln_ref_nome from _tbl_ref_nome where _tbl_ref_nome._cln_ref_pk = _tbl_nome._cln_nome) _cln_nome, ";

    _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_tbl_ref_nome", this.getClnRef().getTbl().getSqlNome());
    _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_cln_ref_nome", this.getClnRef().getTbl().getClnNome().getSqlNome());
    _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_cln_ref_pk", this.getClnRef().getTbl().getClnIntId().getSqlNome());
    _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_tbl_nome", this.getTbl().getSqlNome());
    _sqlSubSelectClnRef = _sqlSubSelectClnRef.replace("_cln_nome", this.getSqlNome());

    return _sqlSubSelectClnRef;
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

    _sqlValorDetault = "DEFAULT '_cln_valor_default'".replace("_cln_valor_default", this.getStrValorDefault());

    return _sqlValorDetault;
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

  private String getStrNomeValor()
  {
    if (_strNomeValor != null)
    {
      return _strNomeValor;
    }

    _strNomeValor = "_cln_nome = '_cln_valor'";

    _strNomeValor = _strNomeValor.replace("_cln_nome", this.getSqlNome());
    _strNomeValor = _strNomeValor.replace("_cln_valor", this.getStrValorSql());

    return _strNomeValor;
  }

  public String getStrTblNomeClnNome()
  {
    if (_strTblNomeClnNome != null)
    {
      return _strTblNomeClnNome;
    }

    _strTblNomeClnNome = "_tbl_nome._cln_nome, ";

    _strTblNomeClnNome = _strTblNomeClnNome.replace("_tbl_nome", this.getTbl().getSqlNome());
    _strTblNomeClnNome = _strTblNomeClnNome.replace("_cln_nome", this.getSqlNome());

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
    if (_strValorExibicao != null)
    {
      return _strValorExibicao;
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

  private String getStrValorMonetario()
  {
    if (_strValorMonetario != null)
    {
      return _strValorMonetario;
    }

    _strValorMonetario = Utils.getStrValorMonetario(Double.parseDouble(this.getStrValor()));

    return _strValorMonetario;
  }

  public String getStrValorSql()
  {
    if (_strValorSql != null)
    {
      return _strValorSql;
    }

    switch (this.getEnmTipo())
    {
      case BOOLEAN:
        return _strValorSql = this.getBooValor() ? "1" : "0";

      case DATE_TIME:
        return _strValorSql = this.getStrValor();

      case INTEGER:
        return _strValorSql = this.getStrValor();

      case NUMERIC:
        return _strValorSql = this.getStrValor();

      case REAL:
        return _strValorSql = this.getStrValor();

      default:
        return _strValorSql = getStrValorSqlText();
    }
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

  public Tabela<?> getTbl()
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
    if (objDominio == null)
    {
      return;
    }

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
      if (this.lerDominio(objDominio, objField))
      {
        this.setBooClnDominioValorCarregado(true);
        return;
      }
    }
  }

  private <T extends DominioMain> boolean lerDominio(T objDominio, Field objField)
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

  public void setBooOrdem(boolean booOrdem)
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
    this.setStrValor(String.valueOf(booValor));
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

  public void setDttValor(Calendar dttValor)
  {
    if (dttValor == null)
    {
      this.setStrValor(null);
      return;
    }

    this.setStrValor(Utils.getStrDataFormatada(dttValor, Utils.EnmDataFormato.YYYY_MM_DD_HH_MM_SS));
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
    this.setStrValor(String.valueOf(intValor));
  }

  public void setStrDominioNome(String strDominioNome)
  {
    _strDominioNome = strDominioNome;
  }

  @Override
  public void setStrNome(String strNome)
  {
    super.setStrNome(strNome);

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

    this.setStrNomeValor(null);
    this.setStrValorExibicao(null);
    this.setStrValorMonetario(null);
    this.setStrValorSql(null);

    this.dispararEvtOnValorAlteradoListener();
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

  private void setStrValorSql(String strValorSql)
  {
    _strValorSql = strValorSql;
  }

  public void setTbl(Tabela<?> tbl)
  {
    if (_tbl == tbl)
    {
      return;
    }

    _tbl = tbl;

    if (_tbl == null)
    {
      return;
    }

    _tbl.addCln(this);
  }
}