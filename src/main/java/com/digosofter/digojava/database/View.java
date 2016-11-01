package com.digosofter.digojava.database;

import com.digosofter.digojava.dominio.DominioMain;

public abstract class View extends Tabela<DominioMain>
{
  public View(String strNome, DbeMain dbe)
  {
    super(strNome, dbe);
  }
}