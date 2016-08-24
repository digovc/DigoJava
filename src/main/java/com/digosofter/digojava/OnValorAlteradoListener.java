package com.digosofter.digojava;

import java.util.EventListener;

public interface OnValorAlteradoListener extends EventListener
{
  void onValorAlterado(Object objSender, OnValorAlteradoArg arg);
}