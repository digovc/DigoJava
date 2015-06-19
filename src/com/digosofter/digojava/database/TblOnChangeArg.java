package com.digosofter.digojava.database;

import com.digosofter.digojava.EventArg;

public class TblOnChangeArg extends EventArg {

  private int _intRegistroId;

  public int getIntRegistroId() {

    return _intRegistroId;
  }

  public void setIntRegistroId(int intRegistroId) {

    _intRegistroId = intRegistroId;
  }
}