package com.digosofter.digojava.tabela;

import com.digosofter.digojava.App;
import com.digosofter.digojava.database.DbColuna;
import com.digosofter.digojava.database.DbColuna.EnmTipo;
import com.digosofter.digojava.erro.Erro;

public class TblPessoa extends TblMain {

  private DbColuna _clnStrNome;
  private DbColuna _clnStrSobrenome;

  public TblPessoa() {

    super("tbl_pessoa");
  }

  public DbColuna getClnStrNome() {

    try {
      if (_clnStrNome != null) {
        return _clnStrNome;
      }
      _clnStrNome = new DbColuna("str_nome", this, EnmTipo.TEXT);
      _clnStrNome.setBooClnNome(true);
      _clnStrNome.setBooOrdemCadastro(true);
      _clnStrNome.setBooVisivelCadastro(true);
      _clnStrNome.setStrNomeExibicao("Nome");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnStrNome;
  }

  public DbColuna getClnStrSobrenome() {

    try {
      if (_clnStrSobrenome != null) {
        return _clnStrSobrenome;
      }
      _clnStrSobrenome = new DbColuna("str_sobrenome", this, EnmTipo.TEXT);
      _clnStrSobrenome.setBooVisivelCadastro(true);
      _clnStrSobrenome.setStrNomeExibicao("Sobrenome");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnStrSobrenome;
  }

  @Override
  protected int inicializarColunas(int intOrdem) {

    try {
      intOrdem = super.inicializarColunas(intOrdem);
      this.getClnStrNome().setIntOrdem(++intOrdem);
      this.getClnStrSobrenome().setIntOrdem(++intOrdem);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return intOrdem;
  }
}
