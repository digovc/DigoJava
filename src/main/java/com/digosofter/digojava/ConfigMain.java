package com.digosofter.digojava;

import com.digosofter.digojava.arquivo.ArquivoTxt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ConfigMain extends Objeto
{
  private transient ArquivoTxt _arqJson;
  private transient Gson _objGson;

  protected ArquivoTxt getArqJson()
  {
    if (_arqJson != null)
    {
      return _arqJson;
    }

    _arqJson = new ArquivoTxt();

    _arqJson.setDir(this.getDirArquivo());
    _arqJson.setStrNome("AppConfig.json");

    return _arqJson;
  }

  protected String getDirArquivo()
  {
    return "/";
  }

  protected Gson getObjGson()
  {
    if (_objGson != null)
    {
      return _objGson;
    }

    GsonBuilder objGsonBuilder = new GsonBuilder();

    objGsonBuilder.setPrettyPrinting();

    _objGson = objGsonBuilder.create();

    return _objGson;
  }

  protected void recuperar()
  {
  }

  public void salvar()
  {
    this.getArqJson().setStrConteudo(this.getObjGson().toJson(this));
    this.getArqJson().salvar();
  }
}