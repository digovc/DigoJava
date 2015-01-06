package com.digosofter.digojava.arquivo;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.digosofter.digojava.erro.Erro;

public class ArquivoXml extends Arquivo {

  private File _objFile;

  private File getObjFile() {

    try {

      if (_objFile == null) {
        
        _objFile = new File(this.getDirCompleto());
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _objFile;
  }

  /**
   * Retorna o valor de um elemento do "XML".
   *
   * @param string
   */
  public String getStrElemento(String strElemento, String strValorDefault) {

    Document doc;
    DocumentBuilder db;
    DocumentBuilderFactory dbf;
    String strResultado = null;

    try {

      dbf = DocumentBuilderFactory.newInstance();

      db = dbf.newDocumentBuilder();

      doc = db.parse(this.getObjFile());

      strResultado = doc.getDocumentElement().getAttribute(strElemento);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return strResultado;
  }

}
