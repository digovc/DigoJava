package com.digosofter.digojava.http;

public class HttpServer extends NanoHTTPD
{
  public HttpServer()
  {
    super(8080);
  }

  @Override
  public Response serve(IHTTPSession session)
  {
    return super.serve(session);
  }
}