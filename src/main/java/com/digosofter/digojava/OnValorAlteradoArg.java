package com.digosofter.digojava;

public class OnValorAlteradoArg extends EventArg
{
  private String _strValor;
  private String _strValorAnterior;

  public String getStrValor()
  {
    return _strValor;
  }

  public String getStrValorAnterior()
  {
    return _strValorAnterior;
  }

  public void setStrValor(String strValor)
  {
    _strValor = strValor;
  }

  public void setStrValorAnterior(String strValorAnterior)
  {
    _strValorAnterior = strValorAnterior;
  }
}
