package com.digosofter.digojava.service;

import com.digosofter.digojava.erro.Erro;

public abstract class ServiceMain extends Thread {

  protected void dormirHora(int intHora) {

    try {

      this.dormirMinuto(intHora * 60);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void dormirMilissegundo(int intMilissegundo) {

    try {

      Thread.sleep(intMilissegundo);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void dormirMinuto(int intMinuto) {

    try {

      this.dormirSegundo(intMinuto * 60);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  protected void dormirSegundo(int intSegundo) {

    try {

      this.dormirMilissegundo(intSegundo * 1000);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

}
