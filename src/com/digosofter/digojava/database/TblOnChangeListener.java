package com.digosofter.digojava.database;

public interface TblOnChangeListener {

  void onAdicionarReg(TblOnChangeArg arg);

  void onApagarReg(TblOnChangeArg arg);

  void onAtualizarReg(TblOnChangeArg arg);
}