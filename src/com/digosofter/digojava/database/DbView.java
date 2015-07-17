package com.digosofter.digojava.database;

public abstract class DbView extends DbTabela<Dominio> {

  public DbView(String strNome) {

    super(strNome, null);
  }
}