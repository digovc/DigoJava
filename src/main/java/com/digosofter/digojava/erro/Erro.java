package com.digosofter.digojava.erro;

import com.digosofter.digojava.Utils;

import java.io.Serializable;

public class Erro extends Exception implements Serializable {

  private static final long serialVersionUID = 1L;
  private String _strMsg;
  private String _strMsgDetalhe;
  private String _strNome;

  public Erro(String strMsg, Exception ex) {

    try {

      this.setStrMsg(strMsg);

      if (ex != null && !Utils.getBooStrVazia(ex.getMessage())) {

        this.setStrMsgDetalhe(ex.getMessage());
      }

      this.imprimirConsole();

      if (ex != null) {

        ex.printStackTrace();
      }
    }
    catch (Exception e) {
    }
    finally {
    }
  }

  public String getStrMsg() {

    try {

      if (!Utils.getBooStrVazia(_strMsg)) {

        return _strMsg;
      }

      _strMsg = "Erro inesperado.";
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strMsg;
  }

  public String getStrMsgDetalhe() {

    try {

      if (!Utils.getBooStrVazia(_strMsgDetalhe)) {

        return _strMsgDetalhe;
      }

      _strMsgDetalhe = "Não há detalhes do erro.";
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _strMsgDetalhe;
  }

  public String getStrNome() {

    return _strNome;
  }

  private void imprimirConsole() {

    try {

      System.out.println(this.getStrMsg());
      System.out.println(this.getStrMsgDetalhe());
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  private void setStrMsg(String strMsg) {

    _strMsg = strMsg;
  }

  private void setStrMsgDetalhe(String strMsgDetalhe) {

    _strMsgDetalhe = strMsgDetalhe;
  }
}