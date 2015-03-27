package com.digosofter.digojava;

import com.digosofter.digojava.erro.Erro;

public abstract class Objeto {

  private static int intObjetoIdStatic;

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

      _intObjetoId = Objeto.intObjetoIdStatic++;
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

      _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(_strNome);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strNomeExibicao;
  }

  public String getStrNomeSimplificado() {

    try {

      if (!Utils.getBooStrVazia(_strNomeSimplificado)) {

        return _strNomeSimplificado;
      }

      _strNomeSimplificado = Utils.getStrSimplificada(_strNome);
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

    _strNome = strNome;
  }

  public void setStrNomeExibicao(String strNomeExibicao) {

    _strNomeExibicao = strNomeExibicao;
  }
}