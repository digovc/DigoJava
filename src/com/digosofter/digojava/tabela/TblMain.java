package com.digosofter.digojava.tabela;

import com.digosofter.digojava.App;
import com.digosofter.digojava.database.DbColuna;
import com.digosofter.digojava.database.DbColuna.EnmTipo;
import com.digosofter.digojava.database.DbTabela;
import com.digosofter.digojava.erro.Erro;

public abstract class TblMain extends DbTabela {

  private DbColuna _clnDttAlteracao;
  private DbColuna _clnDttCadastro;
  private DbColuna _clnDttExclusao;
  private DbColuna _clnIntId;
  private DbColuna _clnIntUsuarioAlteracaoId;
  private DbColuna _clnIntUsuarioCadastroId;
  private DbColuna _clnIntUsuarioExclusaoId;

  public TblMain(String strNome) {

    super(strNome);
  }

  public DbColuna getClnDttAlteracao() {

    try {
      if (_clnDttAlteracao != null) {
        return _clnDttAlteracao;
      }
      _clnDttAlteracao = new DbColuna("dtt_alteracao", this, EnmTipo.TEXT);
      _clnDttAlteracao.setStrNomeExibicao("Alteração");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnDttAlteracao;
  }

  public DbColuna getClnDttCadastro() {

    try {
      if (_clnDttCadastro != null) {
        return _clnDttCadastro;
      }
      _clnDttCadastro = new DbColuna("dtt_cadastro", this, EnmTipo.TEXT);
      _clnDttCadastro.setStrNomeExibicao("Cadastro");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnDttCadastro;
  }

  public DbColuna getClnDttExclusao() {

    try {
      if (_clnDttExclusao != null) {
        return _clnDttExclusao;
      }
      _clnDttExclusao = new DbColuna("dtt_exclusao", this, EnmTipo.TEXT);
      _clnDttExclusao.setStrNomeExibicao("Exclusão");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnDttExclusao;
  }

  public DbColuna getClnIntId() {

    try {
      if (_clnIntId != null) {
        return _clnIntId;
      }
      _clnIntId = new DbColuna("int_id", this, EnmTipo.INTEGER);
      _clnIntId.setBooChavePrimaria(true);
      _clnIntId.setStrNomeExibicao("Código");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnIntId;
  }

  public DbColuna getClnIntUsuarioAlteracaoId() {

    try {
      if (_clnIntUsuarioAlteracaoId != null) {
        return _clnIntUsuarioAlteracaoId;
      }
      _clnIntUsuarioAlteracaoId = new DbColuna("int_usuario_alteracao_id", this, EnmTipo.INTEGER);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnIntUsuarioAlteracaoId;
  }

  public DbColuna getClnIntUsuarioCadastroId() {

    try {
      if (_clnIntUsuarioCadastroId != null) {
        return _clnIntUsuarioCadastroId;
      }
      _clnIntUsuarioCadastroId = new DbColuna("int_usuario_cadastro_id", this, EnmTipo.INTEGER);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnIntUsuarioCadastroId;
  }

  public DbColuna getClnIntUsuarioExclusaoId() {

    try {
      if (_clnIntUsuarioExclusaoId != null) {
        return _clnIntUsuarioExclusaoId;
      }
      _clnIntUsuarioExclusaoId = new DbColuna("int_usuario_exclusao_id", this, EnmTipo.INTEGER);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return _clnIntUsuarioExclusaoId;
  }

  @Override
  protected int inicializarColuna(int intOrdem) {

    try {

      intOrdem = super.inicializarColuna(intOrdem);

      this.getClnDttAlteracao().setIntOrdem(++intOrdem);
      this.getClnDttCadastro().setIntOrdem(++intOrdem);
      this.getClnDttExclusao().setIntOrdem(++intOrdem);
      this.getClnIntId().setIntOrdem(++intOrdem);
      this.getClnIntUsuarioAlteracaoId().setIntOrdem(++intOrdem);
      this.getClnIntUsuarioCadastroId().setIntOrdem(++intOrdem);
      this.getClnIntUsuarioExclusaoId().setIntOrdem(++intOrdem);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return intOrdem;
  }
}
