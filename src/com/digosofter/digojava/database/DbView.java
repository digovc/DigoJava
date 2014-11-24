package com.digosofter.digojava.database;

import com.digosofter.digojava.App;
import com.digosofter.digojava.erro.Erro;

public abstract class DbView extends DbTabela {

  public DbView(String strNome) {

    super(strNome);
    try {
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }
}
