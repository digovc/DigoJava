package com.digosofter.digojava.database;

import com.digosofter.digojava.dominio.DominioMain;

public abstract class View extends TabelaMain<DominioMain>
{
  public View(String strNome, DbeMain dbe)
  {
    super(strNome, dbe);
  }
}