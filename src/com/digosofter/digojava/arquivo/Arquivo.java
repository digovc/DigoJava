package com.digosofter.digojava.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class Arquivo extends Objeto {

  private String _dir;
  private String _dirCompleto;
  private String _strConteudo;

  /**
   * Faz uma cópia deste arquivo para outra diretório.
   */
  public void copiar(String dirDestino) {

    byte[] arrBytBuffer;
    FileInputStream filOriginal;
    FileOutputStream filCopia;
    int intLength;

    try {

      filOriginal = new FileInputStream(this.getDirCompleto());
      filCopia = new FileOutputStream(dirDestino + "/" + this.getStrNome());
      arrBytBuffer = new byte[1024];

      while ((intLength = filOriginal.read(arrBytBuffer)) > 0) {
        filCopia.write(arrBytBuffer, 0, intLength);
      }

      filOriginal.close();
      filCopia.close();
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  private void criarDiretorio() {

    File fil;

    try {

      fil = new File(this.getDir());
      fil.mkdirs();
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public String getDir() {

    return _dir;
  }

  public String getDirCompleto() {

    try {

      if (!Utils.getBooStrVazia(_dirCompleto)) {

        return _dirCompleto;
      }

      _dirCompleto = "_dir\\_str_nome";
      _dirCompleto = _dirCompleto.replace("_dir", this.getDir());
      _dirCompleto = _dirCompleto.replace("_str_nome", this.getStrNome());
      _dirCompleto = _dirCompleto.replace("\\\\", "\\");
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _dirCompleto;
  }

  public String getStrConteudo() {

    return _strConteudo;
  }

  /**
   * Salva o arquivo no diretório indicado no atributo "dirCompleto".
   */
  public void salvar() {

    FileWriter filWriter;

    try {

      this.criarDiretorio();

      filWriter = new FileWriter(new File(this.getDirCompleto()));
      filWriter.write(this.getStrConteudo());
      filWriter.close();
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setDir(String dir) {

    _dir = dir;
  }

  public void setDirCompleto(String dirCompleto) {

    File fil;

    try {

      _dirCompleto = dirCompleto;
      fil = new File(_dirCompleto);
      this.setStrNome(fil.getName());
      _dir = fil.getPath().replace(this.getStrNome(), Utils.STR_VAZIA);
    }
    catch (Exception ex) {
      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void setStrConteudo(String strConteudo) {

    _strConteudo = strConteudo;
  }
}
