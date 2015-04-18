package com.digosofter.digojava.database;

public interface TblOnChangeListener {

  void OnAdicionarReg(TblOnChangeArg arg);

  void OnApagarReg(TblOnChangeArg arg);

  void OnAtualizarReg(TblOnChangeArg arg);
}