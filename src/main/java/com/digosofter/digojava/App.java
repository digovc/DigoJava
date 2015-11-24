package com.digosofter.digojava;

import java.util.ArrayList;
import java.util.List;

import com.digosofter.digojava.MsgUsuario.EnmLingua;
import com.digosofter.digojava.database.DbTabela;
import com.digosofter.digojava.erro.Erro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Principal classe da aplicação. Esta classe deve ser implementada/estendida
 * pela classe especializada da sua aplicação. Ela controla todo o ciclo de vida
 * da aplicação como um todo deste que o usuário a inicia, até a sua conclusão.
 * Esta classe não pode ser instanciada, pois precisa necessariamente ser
 * implementada/estendida por outra classe que receberá as especificações da
 * aplicação que está sendo construída.
 *
 * @author r-vieira
 */
public abstract class App extends Objeto {

  private static App i;

  /**
   * @return Retorna a única instância desta classe durante o ciclo de vida da
   *         aplicação. Esta instância é carregada automaticamente quando a
   *         classe que estende esta é construída.
   */
  public static App getI() {

    return i;
  }

  private boolean _booDebug;
  private long _intMilisegLigado;
  private long _intSegLigado;
  private long _intStartTime;
  private int _intVersao = 1;
  private List<MsgUsuario> _lstMsgUsr;
  private List<MsgUsuario> _lstMsgUsrPadrao;
  private List<DbTabela<?>> _lstTbl;
  private Gson _objGson;
  private String _strVersao;

  private DbTabela<?> _tblSelec;

  /**
   * O construtor não é público, pois esta classe não pode ser construída
   * diretamente. Ela necessariamente precisa ser implementada/estendida por
   * outra classe que conterá as especificações da aplicação que está sendo
   * desenvolvida.
   */
  protected App() {

    try {

      this.setI(this);
      this.setIntStartTime(System.currentTimeMillis());
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  /**
   * Serve para adicionar novas instâncias das tabelas que a aplicação precisa
   * para funcionar.<br/>
   * As tabelas adicionadas por este método podem ser acessadas posteriormente
   * através do método {@link #getLstTbl()}.
   *
   * @param tbl
   *          Tabela que faz parte da aplicação e será adicionada.
   */
  public void addTbl(DbTabela<?> tbl) {

    try {

      if (tbl == null) {

        return;
      }

      if (this.getLstTbl().contains(tbl)) {

        return;
      }

      this.getLstTbl().add(tbl);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

  }

  /**
   * @return Retorna atributo que indica se a aplicação está em modo de "debug"
   *         ou não.
   */
  public boolean getBooDebug() {

    return _booDebug;
  }

  /**
   * @return Retorna a quantidade de milissegundos que a aplicação está rodando.
   */
  public long getIntMilisegLigado() {

    try {

      _intMilisegLigado = System.currentTimeMillis() - this.getIntStartTime();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _intMilisegLigado;
  }

  /**
   * @return Retorna a quantidade de segundos que a aplicação está rodando.
   */
  public long getIntSegLigado() {

    try {

      _intSegLigado = this.getIntMilisegLigado() / 1000;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _intSegLigado;
  }

  private long getIntStartTime() {

    return _intStartTime;
  }

  /**
   * @return Retorna um inteiro que representa a versão desta aplicação.
   */
  public int getIntVersao() {

    return _intVersao;
  }

  /**
   * Esta lista tem por objetivo manter centralizada todos os textos que serão
   * mostrados para o usuário no decorrer do uso da aplicação. A classe
   * {@link MsgUsuario} dá a oportunidade de se trabalhar com aplicações em
   * diversas idiomas. Este método precisa ser sobrescrito para ser inicializar
   * e retornar as mensagens da aplicação.
   *
   * @return Retorna lista de objetos do tipo {@link MsgUsuario}, que mantém
   *         todos os textos que serão apresentados para o usuário num só local.
   */
  public List<MsgUsuario> getLstMsgUsr() {

    try {

      if (_lstMsgUsr != null) {

        return _lstMsgUsr;
      }

      _lstMsgUsr = new ArrayList<>();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstMsgUsr;
  }

  /**
   * Esta lista tem o mesmo propósito da outra acessada pelo
   * {@link #getLstMsgUsr()}, com exceção de que esta guarda as mensagens
   * internas do framework DigoJava.
   *
   * @return Lista de mensagens que podem ser lançadas ao usuário.
   */
  protected List<MsgUsuario> getLstMsgUsrPadrao() {

    try {

      if (_lstMsgUsrPadrao != null) {

        return _lstMsgUsrPadrao;
      }

      _lstMsgUsrPadrao = this.inicializarLstMsgUsrPadrao();
    }
    catch (Exception ex) {

      new Erro(this.getStrMsgUsrPadrao(0), ex);
    }
    finally {
    }

    return _lstMsgUsrPadrao;
  }

  /**
   * @return Retorna a lista de instâncias das tabelas que a aplicação necessita
   *         para funcionas. Este objetos foram adicionados através do métodos
   *         {@link #addTbl(DbTabela)}. Este processo tem por objetivo manter
   *         concentradas num mesmo local a instância de todos as tabelas.
   */
  public List<DbTabela<?>> getLstTbl() {

    try {

      if (_lstTbl != null) {

        return _lstTbl;
      }

      _lstTbl = new ArrayList<DbTabela<?>>();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstTbl;
  }

  /**
   * @return Retorna uma instância única de um objeto do tipo "Gson", contido na
   *         biblioteca do Google para tratamento de JSON. É através deste
   *         objeto é possível transformar os mais variados objetos em JSON e
   *         vice-versa.
   *
   */
  public Gson getObjGson() {

    GsonBuilder objGsonBuilder;

    try {

      if (_objGson != null) {

        return _objGson;
      }

      objGsonBuilder = new GsonBuilder();

      objGsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

      _objGson = objGsonBuilder.create();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _objGson;
  }

  /**
   * @param intId
   *          Código que indica a mensagem que se espera como retorno.
   * @return Retorna a primeira mensagem que recebeu o código representado no
   *         parâmetro "intId". Caso não haja um objeto do tipo
   *         {@link MsgUsuario} na lista {@link #getLstMsgUsr()} que contenha
   *         este código, retorna "null". O texto que será estará no idioma
   *         <i>default</i>, ou seja <b>português do Brasil</b>.
   */
  public String getStrMsgUsr(int intId) {

    return this.getStrMsgUsr(intId, EnmLingua.PORTUGUES_BRASIL, false);
  }

  public String getStrMsgUsr(int intId, EnmLingua enmLingua, boolean booMsgPadrao) {

    List<MsgUsuario> lstMsgUsrTemp;

    try {

      if (booMsgPadrao) {

        lstMsgUsrTemp = this.getLstMsgUsrPadrao();
      }
      else {

        lstMsgUsrTemp = this.getLstMsgUsr();
      }

      for (MsgUsuario msgUsuario : lstMsgUsrTemp) {

        if (msgUsuario.getIntId() != intId || msgUsuario.getEnmLingua() != enmLingua) {

          continue;
        }

        return msgUsuario.getStrTexto();
      }
    }
    catch (Exception ex) {

      new Erro(this.getStrTextoPadrao(103), ex);
    }
    finally {
    }

    return null;
  }

  public String getStrMsgUsrPadrao(int intId) {

    return this.getStrMsgUsr(intId, EnmLingua.PORTUGUES_BRASIL, true);
  }

  public String getStrTexto(int intId) {

    return this.getStrMsgUsr(intId);
  }

  public String getStrTextoPadrao(int intId) {

    return this.getStrMsgUsrPadrao(intId);
  }

  public String getStrVersao() {

    try {

      if (!Utils.getBooStrVazia(_strVersao)) {

        return _strVersao;
      }

      _strVersao = "0.0.1 beta";
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strVersao;
  }

  public DbTabela<?> getTblSelec() {

    return _tblSelec;
  }

  private List<MsgUsuario> inicializarLstMsgUsrPadrao() {

    List<MsgUsuario> lstMsgUsrResultado;

    try {

      lstMsgUsrResultado = new ArrayList<MsgUsuario>();

      lstMsgUsrResultado.add(new MsgUsuario("Erro inesperado.", 0));

      return lstMsgUsrResultado;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
    return null;
  }

  public void setBooDebug(boolean booDebug) {

    _booDebug = booDebug;
  }

  private void setI(App app) {

    try {

      if (i != null) {

        return;
      }

      i = app;
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  private void setIntStartTime(long intStartTime) {

    _intStartTime = intStartTime;
  }

  public void setIntVersao(int intVersao) {

    _intVersao = intVersao;
  }

  public void setStrVersao(String strVersao) {

    _strVersao = strVersao;
  }

  public void setTblSelec(DbTabela<?> tblSelec) {

    _tblSelec = tblSelec;
  }
}