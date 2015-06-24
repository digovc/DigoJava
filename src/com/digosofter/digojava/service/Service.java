package com.digosofter.digojava.service;

import com.digosofter.digojava.erro.Erro;

public abstract class Service extends Thread {

  private boolean _booParar;

  protected void dormirHora(int intHora) {

    this.dormirMinuto(intHora * 60);
  }

  protected void dormirMilissegundo(int intMilissegundo) {

    long lng;

    try {

      lng = 0;

      while (!this.getBooParar() && lng < intMilissegundo) {

        Thread.sleep(100);
        lng += 100;
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void dormirMinuto(int intMinuto) {

    this.dormirSegundo(intMinuto * 60);
  }

  protected void dormirSegundo(int intSegundo) {

    this.dormirMilissegundo(intSegundo * 1000);
  }

  private boolean getBooParar() {

    return _booParar;
  }

  public void setBooParar(boolean booParar) {

    _booParar = booParar;
  }
}