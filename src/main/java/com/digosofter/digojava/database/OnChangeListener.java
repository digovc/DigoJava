package com.digosofter.digojava.database;

public interface OnChangeListener {

  void onAdicionarReg(OnChangeArg arg);

  void onApagarReg(OnChangeArg arg);

  void onAtualizarReg(OnChangeArg arg);
}