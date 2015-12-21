package com.digosofter.digojava.database;

public abstract class View extends Tabela<Dominio> {

  public View(String strNome) {

    super(strNome, null);
  }
}