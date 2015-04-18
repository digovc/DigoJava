package com.digosofter.digojava.database;

import com.digosofter.digojava.EventArgMain;

public class TblOnChangeArg extends EventArgMain {

  private int _intRegistroId;

  public int getIntRegistroId() {

    return _intRegistroId;
  }

  public void setIntRegistroId(int intRegistroId) {

    _intRegistroId = intRegistroId;
  }
}