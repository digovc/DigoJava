package com.digosofter.digojava;

import com.digosofter.digojava.erro.Erro;

public class MsgUsuario extends Objeto {

  public enum EnmLingua {
    INGLES,
    PORTUGUES
  }

  private EnmLingua _enmLingua = EnmLingua.PORTUGUES;
  private int _intId;
  private String _strTexto;

  public MsgUsuario(String strTexto, int intId) {

    try {

      this.setIntId(intId);
      this.setStrTexto(strTexto);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public MsgUsuario(String strTexto, int intId, EnmLingua enmLingua) {

    try {

      this.setIntId(intId);
      this.setStrTexto(strTexto);
      this.setEnmLingua(enmLingua);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public EnmLingua getEnmLingua() {

    return _enmLingua;
  }

  public int getIntId() {

    return _intId;
  }

  public String getStrTexto() {

    return _strTexto;
  }

  private void setEnmLingua(EnmLingua enmLingua) {

    _enmLingua = enmLingua;
  }

  private void setIntId(int intId) {

    _intId = intId;
  }

  private void setStrTexto(String strTexto) {

    _strTexto = strTexto;
  }
}
