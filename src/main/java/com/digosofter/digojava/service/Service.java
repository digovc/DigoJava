package com.digosofter.digojava.service;

public abstract class Service extends Thread
{
  private boolean _booParar;

  protected void dormirHora(int intHora)
  {
    this.dormirMinuto(intHora * 60);
  }

  protected void dormirMilissegundo(int intMilissegundo)
  {
    try
    {
      long lng = 0;

      while (!this.getBooParar() && lng < intMilissegundo)
      {
        Thread.sleep(100);

        lng += 100;
      }
    }
    catch (InterruptedException ex)
    {
      ex.printStackTrace();
    }
  }

  protected void dormirMinuto(int intMinuto)
  {
    this.dormirSegundo(intMinuto * 60);
  }

  protected void dormirSegundo(int intSegundo)
  {
    this.dormirMilissegundo(intSegundo * 1000);
  }

  private boolean getBooParar()
  {
    return _booParar;
  }

  public void setBooParar(boolean booParar)
  {
    _booParar = booParar;
  }
}