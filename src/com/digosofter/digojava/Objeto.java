package com.digosofter.digojava;

import com.digosofter.digojava.erro.Erro;

public abstract class Objeto {

  private static int _intObjetoIdStatic;

  private static int getIntObjetoIdStatic() {

    return _intObjetoIdStatic;
  }

  private static void setIntObjetoIdStatic(int intObjetoIdStatic) {

    _intObjetoIdStatic = intObjetoIdStatic;
  }

  private int _intObjetoId;
  private String _strDescricao;
  private String _strNome;
  private String _strNomeExibicao;
  private String _strNomeSimplificado;

  public int getIntObjetoId() {

    try {

      if (_intObjetoId > 0) {

        return _intObjetoId;
      }

      Objeto.setIntObjetoIdStatic(Objeto.getIntObjetoIdStatic() + 1);

      _intObjetoId = Objeto.getIntObjetoIdStatic();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _intObjetoId;
  }

  public String getStrDescricao() {

    return _strDescricao;
  }

  public String getStrNome() {

    return _strNome;
  }

  public String getStrNomeExibicao() {

    try {

      if (!Utils.getBooStrVazia(_strNomeExibicao)) {

        return _strNomeExibicao;
      }

      _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(this.getStrNome());
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strNomeExibicao;
  }

  private void setStrNomeSimplificado(String strNomeSimplificado) {

    _strNomeSimplificado = strNomeSimplificado;
  }

  public String getStrNomeSimplificado() {

    try {

      if (!Utils.getBooStrVazia(_strNomeSimplificado)) {

        return _strNomeSimplificado;
      }

      _strNomeSimplificado = Utils.simplificar(this.getStrNome());
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strNomeSimplificado;
  }

  public void setStrDescricao(String strDescricao) {

    _strDescricao = strDescricao;
  }

  public void setStrNome(String strNome) {

    try {

      _strNome = strNome;

      this.setStrNomeSimplificado(null);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  public void setStrNomeExibicao(String strNomeExibicao) {

    _strNomeExibicao = strNomeExibicao;
  }
}