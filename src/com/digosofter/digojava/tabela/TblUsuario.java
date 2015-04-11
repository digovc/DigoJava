package com.digosofter.digojava.tabela;

import com.digosofter.digojava.App;
import com.digosofter.digojava.database.DbColuna;
import com.digosofter.digojava.database.DbColuna.EnmTipo;
import com.digosofter.digojava.erro.Erro;

public class TblUsuario extends TblMain {

  private DbColuna _clnIntPessoaId;
  private DbColuna _clnStrLogin;
  private DbColuna _clnStrPassword;

  public TblUsuario() {

    super("tbl_usuario");
  }

  public DbColuna getClnIntPessoaId() {

    try {
      if (_clnIntPessoaId != null) {
        return _clnIntPessoaId;
      }
      _clnIntPessoaId = new DbColuna("int_pessoa_id", this, EnmTipo.INTEGER);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnIntPessoaId;
  }

  public DbColuna getClnStrLogin() {

    try {
      if (_clnStrLogin != null) {
        return _clnStrLogin;
      }
      _clnStrLogin = new DbColuna("str_login", this, EnmTipo.TEXT);
      _clnStrLogin.setStrNomeExibicao("Login");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnStrLogin;
  }

  public DbColuna getClnStrPassword() {

    try {
      if (_clnStrPassword != null) {
        return _clnStrPassword;
      }
      _clnStrPassword = new DbColuna("str_password", this, EnmTipo.TEXT);
      _clnStrPassword.setStrNomeExibicao("Password");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnStrPassword;
  }

  @Override
  protected int inicializarColuna(int intOrdem) {

    try {
      intOrdem = super.inicializarColuna(intOrdem);
      this.getClnIntPessoaId().setIntOrdem(++intOrdem);
      this.getClnStrLogin().setIntOrdem(++intOrdem);
      this.getClnStrPassword().setIntOrdem(++intOrdem);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return intOrdem;
  }
}
