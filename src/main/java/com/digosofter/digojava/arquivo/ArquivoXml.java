package com.digosofter.digojava.arquivo;

import java.io.File;

public class ArquivoXml extends Arquivo
{
  private File _fil;

  private File getFil()
  {
    if (_fil != null)
    {
      return _fil;
    }
    _fil = new File(this.getDirCompleto());

    return _fil;
  }
}