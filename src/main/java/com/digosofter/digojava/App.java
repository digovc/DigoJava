/**/
package com.digosofter.digojava;

import com.digosofter.digojava.MsgUsuario.EnmLingua;
import com.digosofter.digojava.database.Tabela;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Principal classe da aplicação. Esta classe deve ser implementada/estendida pela classe especializada da sua aplicação. Ela controla todo o ciclo de
 * vida da aplicação como um todo deste que o usuário a inicia, até a sua conclusão. Esta classe não pode ser instanciada, pois precisa
 * necessariamente ser implementada/estendida por outra classe que receberá as especificações da aplicação que está sendo construída.
 *
 * @author r-vieira
 */
public abstract class App extends Objeto
{

  private static App i;

  /**
   * @return Retorna a única instância desta classe durante o ciclo de vida da aplicação. Esta instância é carregada automaticamente quando a classe
   * que estende esta é construída.
   */
  public static App getI()
  {
    return i;
  }

  private boolean _booDebug;
  private long _intMilisegLigado;
  private long _intSegLigado;
  private long _intStartTime;
  private int _intVersao = 1;
  private List<MsgUsuario> _lstMsgUsr;
  private List<MsgUsuario> _lstMsgUsrPadrao;
  private List<Tabela<?>> _lstTbl;
  private Gson _objGson;
  private String _strVersao;

  /**
   * O construtor não é público, pois esta classe não pode ser construída diretamente. Ela necessariamente precisa ser implementada/estendida por
   * outra classe que conterá as especificações da aplicação que está sendo desenvolvida.
   */
  protected App()
  {
    this.setI(this);
    this.iniciar();
  }

  /**
   * Serve para adicionar novas instâncias das tabelas que a aplicação precisa para funcionar.<br/> As tabelas adicionadas por este método podem ser
   * acessadas posteriormente através do método {@link #getLstTbl()}.
   *
   * @param tbl Tabela que faz parte da aplicação e será adicionada.
   */
  public void addTbl(Tabela<?> tbl)
  {
    if (tbl == null)
    {
      return;
    }

    if (this.getLstTbl().contains(tbl))
    {
      return;
    }
    this.getLstTbl().add(tbl);
  }

  /**
   * @return Retorna atributo que indica se a aplicação está em modo de "debug" ou não.
   */
  public boolean getBooDebug()
  {
    return _booDebug;
  }

  /**
   * @return Retorna a quantidade de milissegundos que a aplicação está rodando.
   */
  public long getIntMilisegLigado()
  {
    _intMilisegLigado = (System.currentTimeMillis() - this.getIntStartTime());

    return _intMilisegLigado;
  }

  /**
   * @return Retorna a quantidade de segundos que a aplicação está rodando.
   */
  public long getIntSegLigado()
  {
    _intSegLigado = (this.getIntMilisegLigado() / 1000);

    return _intSegLigado;
  }

  private long getIntStartTime()
  {
    return _intStartTime;
  }

  /**
   * @return Retorna um inteiro que representa a versão desta aplicação.
   */
  public int getIntVersao()
  {
    return _intVersao;
  }

  /**
   * Esta lista tem por objetivo manter centralizada todos os textos que serão mostrados para o usuário no decorrer do uso da aplicação. A classe
   * {@link MsgUsuario} dá a oportunidade de se trabalhar com aplicações em diversas idiomas. Este método precisa ser sobrescrito para ser inicializar
   * e retornar as mensagens da aplicação.
   *
   * @return Retorna lista de objetos do tipo {@link MsgUsuario}, que mantém todos os textos que serão apresentados para o usuário num só local.
   */
  public List<MsgUsuario> getLstMsgUsr()
  {
    if (_lstMsgUsr != null)
    {
      return _lstMsgUsr;
    }
    _lstMsgUsr = new ArrayList<>();

    return _lstMsgUsr;
  }

  /**
   * Esta lista tem o mesmo propósito da outra acessada pelo {@link #getLstMsgUsr()}, com exceção de que esta guarda as mensagens internas do
   * framework DigoJava.
   *
   * @return Lista de mensagens que podem ser lançadas ao usuário.
   */
  protected List<MsgUsuario> getLstMsgUsrPadrao()
  {
    if (_lstMsgUsrPadrao != null)
    {
      return _lstMsgUsrPadrao;
    }
    _lstMsgUsrPadrao = this.inicializarLstMsgUsrPadrao();

    return _lstMsgUsrPadrao;
  }

  /**
   * @return Retorna a lista de instâncias das tabelas que a aplicação necessita para funcionas. Este objetos foram adicionados através do métodos
   * {@link #addTbl(Tabela)}. Este processo tem por objetivo manter concentradas num mesmo local a instância de todos as tabelas.
   */
  public List<Tabela<?>> getLstTbl()
  {
    if (_lstTbl != null)
    {
      return _lstTbl;
    }
    _lstTbl = new ArrayList<>();
    this.inicializarLstTbl(_lstTbl);

    return _lstTbl;
  }

  /**
   * @return Retorna uma instância única de um objeto do tipo "Gson", contido na biblioteca do Google para tratamento de JSON. É através deste objeto
   * é possível transformar os mais variados objetos em JSON e vice-versa.
   */
  public Gson getObjGson()
  {
    GsonBuilder objGsonBuilder;

    if (_objGson != null)
    {
      return _objGson;
    }
    objGsonBuilder = new GsonBuilder();
    objGsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    _objGson = objGsonBuilder.create();

    return _objGson;
  }

  /**
   * @param intId Código que indica a mensagem que se espera como retorno.
   * @return Retorna a primeira mensagem que recebeu o código representado no parâmetro "intId". Caso não haja um objeto do tipo {@link MsgUsuario} na
   * lista {@link #getLstMsgUsr()} que contenha este código, retorna "null". O texto que será estará no idioma <i>default</i>, ou seja <b>português do
   * Brasil</b>.
   */
  public String getStrMsgUsr(int intId)
  {
    return this.getStrMsgUsr(intId, EnmLingua.PORTUGUES_BRASIL, false);
  }

  public String getStrMsgUsr(int intMensagemId, EnmLingua enmLingua, boolean booMensagemPadrao)
  {
    List<MsgUsuario> lstMsgUsrTemp;

    if (booMensagemPadrao)
    {
      lstMsgUsrTemp = this.getLstMsgUsrPadrao();
    }
    else
    {
      lstMsgUsrTemp = this.getLstMsgUsr();
    }
    for (MsgUsuario msgUsuario : lstMsgUsrTemp)
    {
      if (msgUsuario.getIntId() != intMensagemId || msgUsuario.getEnmLingua() != enmLingua)
      {
        continue;
      }
      return msgUsuario.getStrTexto();
    }

    return null;
  }

  public String getStrMsgUsrPadrao(int intId)
  {
    return this.getStrMsgUsr(intId, EnmLingua.PORTUGUES_BRASIL, true);
  }

  public String getStrTexto(int intId)
  {
    return this.getStrMsgUsr(intId);
  }

  public String getStrTextoPadrao(int intId)
  {
    return this.getStrMsgUsrPadrao(intId);
  }

  public String getStrVersao()
  {
    if (!Utils.getBooStrVazia(_strVersao))
    {
      return _strVersao;
    }
    _strVersao = "0.0.1 beta";

    return _strVersao;
  }

  protected void inicializar()
  {
    this.setIntStartTime(System.currentTimeMillis());
  }

  private List<MsgUsuario> inicializarLstMsgUsrPadrao()
  {
    List<MsgUsuario> lstMsgUsrResultado;

    lstMsgUsrResultado = new ArrayList<>();
    lstMsgUsrResultado.add(new MsgUsuario("Erro inesperado.", 0));
    return lstMsgUsrResultado;
  }

  /**
   * Este método tem a responsabilidade de inicializar a lista de tabelas da aplicação.
   *
   * @param lstTbl Lista de tabela da aplicação.
   */
  protected void inicializarLstTbl(final List<Tabela<?>> lstTbl)
  {
  }

  private void iniciar()
  {
    this.inicializar();
    this.setEventos();
  }

  public void setBooDebug(boolean booDebug)
  {
    _booDebug = booDebug;
  }

  protected void setEventos()
  {
  }

  private void setI(App app)
  {
    if (i != null)
    {
      return;
    }

    i = app;
  }

  private void setIntStartTime(long intStartTime)
  {
    _intStartTime = intStartTime;
  }

  public void setIntVersao(int intVersao)
  {
    _intVersao = intVersao;
  }

  public void setStrVersao(String strVersao)
  {
    _strVersao = strVersao;
  }
}