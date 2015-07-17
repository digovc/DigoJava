package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;

public abstract class Dominio extends Objeto {

  private int _intId;

  public int getIntId() {

    return _intId;
  }

  public void setIntId(int intId) {

    _intId = intId;
  }
}