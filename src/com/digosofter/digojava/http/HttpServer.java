package com.digosofter.digojava.http;

import com.digosofter.digojava.App;
import com.digosofter.digojava.erro.Erro;

public class HttpServer extends NanoHTTPD {

  public HttpServer() {

    super(8080);
    try {
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  @Override
  public Response serve(IHTTPSession session) {

    try {
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
    return super.serve(session);
  }
}
