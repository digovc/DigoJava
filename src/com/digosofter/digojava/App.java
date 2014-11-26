package com.digosofter.digojava;

import java.util.ArrayList;
import java.util.List;

import com.digosofter.digojava.MsgUsuario.EnmLingua;
import com.digosofter.digojava.database.DataBase;
import com.digosofter.digojava.database.DbTabela;
import com.digosofter.digojava.erro.Erro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class App extends Objeto {

  private static App i;

  public static App getI() {

    return i;
  }

  private boolean _booDebug;
  private long _intMilisegLigado;
  private long _intSegundosLigado;
  private long _intStartTime;
  private int _intVersao;
  private List<MsgUsuario> _lstMsgUsuarioPadrao;
  private List<DbTabela> _lstTbl;
  private DataBase _objDbPrincipal;

  private Gson _objGson;

  private String _strVersao;

  private DbTabela _tblSelec;

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

  public boolean getBooDebug() {

    return _booDebug;
  }

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

  public long getIntSegundosLigado() {

    try {

      _intSegundosLigado = this.getIntMilisegLigado() / 1000;
    }
    catch (Exception ex) {
      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _intSegundosLigado;
  }

  private long getIntStartTime() {

    return _intStartTime;
  }

  public int getIntVersao() {

    return _intVersao;
  }

  public abstract List<MsgUsuario> getLstMsgUsuario();

  protected List<MsgUsuario> getLstMsgUsuarioPadrao() {

    try {

      if (_lstMsgUsuarioPadrao != null) {

        return _lstMsgUsuarioPadrao;
      }

      _lstMsgUsuarioPadrao = new ArrayList<MsgUsuario>();
      _lstMsgUsuarioPadrao.add(new MsgUsuario("Erro inesperado..", 0));
    }
    catch (Exception ex) {
      new Erro(this.getStrMsgUsuarioPadrao(0), ex);
    }
    finally {
    }

    return _lstMsgUsuarioPadrao;
  }

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

  public DataBase getObjDbPrincipal() {

    try {

      if (_objDbPrincipal != null) {

        return _objDbPrincipal;
      }

      _objDbPrincipal = new DataBase();
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(102), ex);
    }
    finally {
    }

    return _objDbPrincipal;
  }

  public Gson getObjGson() {

    try {

      if (_objGson != null) {

        return _objGson;
      }

      _objGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }
    catch (Exception ex) {
      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _objGson;
  }

  public String getStrMsgUsuario(int intId) {

    return this.getStrMsgUsuario(intId, EnmLingua.PORTUGUES, false);
  }

  public String getStrMsgUsuario(int intId, EnmLingua enmLingua, boolean booMsgPadrao) {

    List<MsgUsuario> lstMsgUsuarioTemp;
    String strResultado = Utils.STR_VAZIA;

    try {

      if (booMsgPadrao) {
        lstMsgUsuarioTemp = this.getLstMsgUsuarioPadrao();
      }
      else {
        lstMsgUsuarioTemp = this.getLstMsgUsuario();
      }

      for (MsgUsuario msgUsuario : lstMsgUsuarioTemp) {

        if (msgUsuario.getIntId() == intId && msgUsuario.getEnmLingua() == enmLingua) {
          strResultado = msgUsuario.getStrTexto();
          break;
        }
      }
    }
    catch (Exception ex) {
      new Erro(this.getStrTextoPadrao(103), ex);
    }
    finally {
    }

    return strResultado;
  }

  public String getStrMsgUsuarioPadrao(int intId) {

    return this.getStrMsgUsuario(intId, EnmLingua.PORTUGUES, true);
  }

  public String getStrTexto(int intId) {

    return this.getStrMsgUsuario(intId);
  }

  public String getStrTextoPadrao(int intId) {

    return this.getStrMsgUsuarioPadrao(intId);
  }

  public String getStrVersao() {

    return _strVersao;
  }

  public DbTabela getTblSelec() {

    return _tblSelec;
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
