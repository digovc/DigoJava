package com.digosofter.digojava;

import java.util.ArrayList;
import java.util.List;

import com.digosofter.digojava.MsgUsuario.EnmLingua;
import com.digosofter.digojava.database.DbTabela;
import com.digosofter.digojava.erro.Erro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Principal classe da aplica��o. Esta classe deve ser implementada/estendida
 * pela classe especializada da sua aplica��o. Ela controla todo o ciclo de vida
 * da aplica��o como um todo deste que o usu�rio a inicia, at� a sua conclus�o.
 * Esta classe n�o pode ser instanciada, pois precisa necessariamente ser
 * implementada/estendida por outra classe que receber� as especifica��es da
 * aplica��o que est� sendo constru�da.
 *
 * @author r-vieira
 */
public abstract class App extends Objeto {

  private static App i;

  /**
   * @return Retorna a �nica inst�ncia desta classe durante o ciclo de vida da
   *         aplica��o. Esta inst�ncia � carregada automaticamente quando a
   *         classe que estende esta � constru�da.
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
  private List<DbTabela> _lstTbl;
  private Gson _objGson;
  private String _strVersao;

  private DbTabela _tblSelec;

  /**
   * O construtor n�o � p�blico, pois esta classe n�o pode ser constru�da
   * diretamente. Ela necessariamente precisa ser implementada/estendida por
   * outra classe que conter� as especifica��es da aplica��o que est� sendo
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
   * Serve para adicionar novas inst�ncias das tabelas que a aplica��o precisa
   * para funcionar.<br/>
   * As tabelas adicionadas por este m�todo podem ser acessadas posteriormente
   * atrav�s do m�todo {@link #getLstTbl()}.
   *
   * @param tbl
   *          Tabela que faz parte da aplica��o e ser� adicionada.
   */
  public void addTbl(DbTabela tbl) {

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
   * @return Retorna atributo que indica se a aplica��o est� em modo de "debug"
   *         ou n�o.
   */
  public boolean getBooDebug() {

    return _booDebug;
  }

  /**
   * @return Retorna a quantidade de milissegundos que a aplica��o est� rodando.
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
   * @return Retorna a quantidade de segundos que a aplica��o est� rodando.
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
   * @return Retorna um inteiro que representa a vers�o desta aplica��o.
   */
  public int getIntVersao() {

    return _intVersao;
  }

  /**
   * Esta lista tem por objetivo manter centralizada todos os textos que ser�o
   * mostrados para o usu�rio no decorrer do uso da aplica��o. A classe
   * {@link MsgUsuario} d� a oportunidade de se trabalhar com aplica��es em
   * diversas idiomas. Este m�todo precisa ser sobrescrito para ser inicializar
   * e retornar as mensagens da aplica��o.
   *
   * @return Retorna lista de objetos do tipo {@link MsgUsuario}, que mant�m
   *         todos os textos que ser�o apresentados para o usu�rio num s� local.
   */
  public List<MsgUsuario> getLstMsgUsr() {

    try {

      if (_lstMsgUsr != null) {

        return _lstMsgUsr;
      }

      _lstMsgUsr = new ArrayList<MsgUsuario>();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstMsgUsr;
  }

  /**
   * Esta lista tem o mesmo prop�sito da outra acessada pelo
   * {@link #getLstMsgUsr()}, com exce��o de que esta guarda as mensagens
   * internas do framework DigoJava.
   *
   * @return
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
   * @return Retorna a lista de inst�ncias das tabelas que a aplica��o necessita
   *         para funcionas. Este objetos foram adicionados atrav�s do m�todos
   *         {@link #addTbl(DbTabela)}. Este processo tem por objetivo manter
   *         concentradas num mesmo local a inst�ncia de todos as tabelas.
   */
  public List<DbTabela> getLstTbl() {

    try {

      if (_lstTbl != null) {

        return _lstTbl;
      }

      _lstTbl = new ArrayList<DbTabela>();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _lstTbl;
  }

  /**
   * @return Retorna uma inst�ncia �nica de um objeto do tipo "Gson", contido na
   *         biblioteca do Google para tratamento de JSON. � atrav�s deste
   *         objeto � poss�vel transformar os mais variados objetos em JSON e
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
   *          C�digo que indica a mensagem que se espera como retorno.
   * @return Retorna a primeira mensagem que recebeu o c�digo representado no
   *         par�metro "intId". Caso n�o haja um objeto do tipo
   *         {@link MsgUsuario} na lista {@link #getLstMsgUsr()} que contenha
   *         este c�digo, retorna "null". O texto que ser� estar� no idioma
   *         <i>default</i>, ou seja <b>portugu�s do Brasil</b>.
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

  private String getStrTexto(int intId) {

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

  public DbTabela getTblSelec() {

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

  public void setTblSelec(DbTabela tblSelec) {

    _tblSelec = tblSelec;
  }
}