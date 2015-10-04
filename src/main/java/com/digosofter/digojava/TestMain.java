package com.digosofter.digojava;

public abstract class TestMain extends Objeto {

  public TestMain() {

    this.inicializarLocal();
  }

  protected abstract void inicializar();

  private void inicializarLocal() {

    this.inicializar();
  }
}