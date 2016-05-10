package com.digosofter.digojava.arquivo;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.digosofter.digojava.erro.Erro;

public class ArquivoXml extends Arquivo
{

  private File _fil;

  private File getFil()
  {
    try
    {
      if (_fil != null)
      {
        return _fil;
      }
      _fil = new File(this.getDirCompleto());
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _fil;
  }

  /**
   * Retorna o valor de um elemento do "XML".
   */
  public String getStrElemento(String strElemento, String strValorDefault)
  {
    Document doc;
    DocumentBuilder objDocumentBuilder;
    DocumentBuilderFactory objDocumentBuilderFactory;
    String strResultado = null;
    try
    {
      objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
      objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
      doc = objDocumentBuilder.parse(this.getFil());
      strResultado = doc.getDocumentElement().getAttribute(strElemento);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return strResultado;
  }
}