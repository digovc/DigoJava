package com.digosofter.digojava;

public class MsgUsuario extends Objeto
{

  public enum EnmLingua
  {
    INGLES,
    PORTUGUES_BRASIL
  }

  private EnmLingua _enmLingua = EnmLingua.PORTUGUES_BRASIL;
  private int _intId;
  private String _strTexto;

  public MsgUsuario(String strTexto, int intId)
  {
    this.setIntId(intId);
    this.setStrTexto(strTexto);
  }

  public MsgUsuario(String strTexto, int intId, EnmLingua enmLingua)
  {
    this.setIntId(intId);
    this.setStrTexto(strTexto);
    this.setEnmLingua(enmLingua);
  }

  public EnmLingua getEnmLingua()
  {
    return _enmLingua;
  }

  public int getIntId()
  {
    return _intId;
  }

  public String getStrTexto()
  {
    return _strTexto;
  }

  private void setEnmLingua(EnmLingua enmLingua)
  {
    _enmLingua = enmLingua;
  }

  private void setIntId(int intId)
  {
    _intId = intId;
  }

  private void setStrTexto(String strTexto)
  {
    _strTexto = strTexto;
  }
}
