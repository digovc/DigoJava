package com.digosofter.digojava;

import com.digosofter.digojava.arquivo.ArquivoTxt;
import com.digosofter.digojava.erro.Erro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public abstract class Config extends Objeto {

  @Expose(deserialize = false, serialize = false)
  private ArquivoTxt _arqJson;

  @Expose(deserialize = false, serialize = false)
  private transient Gson _objGson;

  protected ArquivoTxt getArqJson() {

    try {

      if (_arqJson != null) {

        return _arqJson;
      }

      _arqJson = new ArquivoTxt();

      _arqJson.setDir(this.getDirArquivo());
      _arqJson.setStrNome("AppConfig.json");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _arqJson;
  }

  protected abstract String getDirArquivo();

  protected Gson getObjGson() {

    GsonBuilder objGsonBuilder;

    try {

      if (_objGson != null) {

        return _objGson;
      }

      objGsonBuilder = new GsonBuilder();
      
      _objGson = objGsonBuilder.create();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _objGson;
  }

  protected abstract void recuperar();

  public void salvar() {

    try {

      this.getArqJson().setStrConteudo(this.getObjGson().toJson(this));
      this.getArqJson().salvar();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }
}
