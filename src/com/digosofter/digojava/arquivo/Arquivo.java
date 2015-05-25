package com.digosofter.digojava.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;

import com.digosofter.digojava.App;
import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

public abstract class Arquivo extends Objeto {

  private boolean _booExiste;
  private String _dir;
  private String _dirCompleto;
  private String _strConteudo;

  /**
   * Faz uma cópia deste arquivo para outra diretório.
   */
  public void copiar(String dirDestino) {

    byte[] arrBte;
    FileInputStream filOriginal;
    FileOutputStream filCopia;
    int i;

    try {

      this.criarDiretorio(dirDestino);

      arrBte = new byte[1024];
      filCopia = new FileOutputStream(dirDestino + "/" + this.getStrNome());
      filOriginal = new FileInputStream(this.getDirCompleto());

      while ((i = filOriginal.read(arrBte)) > 0) {

        filCopia.write(arrBte, 0, i);
      }

      filCopia.close();
      filOriginal.close();
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  public void criarArquivo() {

    PrintWriter objPrintWriter;

    try {

      if (this.getBooExiste()) {

        return;
      }

      objPrintWriter = new PrintWriter(this.getDirCompleto(), "UTF-8");

      objPrintWriter.print(this.getStrConteudo());
      objPrintWriter.close();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  private void criarDiretorio() {

    this.criarDiretorio(this.getDir());
  }

  private void criarDiretorio(String dir) {

    File fil;

    try {

      if (Utils.getBooStrVazia(dir)) {

        return;
      }

      fil = new File(dir);
      fil.mkdirs();
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }
  }

  protected boolean getBooExiste() {

    try {

      if (Utils.getBooStrVazia(this.getDirCompleto())) {

        return false;
      }

      _booExiste = new File(this.getDirCompleto()).exists();
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

    return _booExiste;
  }

  public String getDir() {

    return _dir;
  }

  public String getDirCompleto() {

    try {

      if (!Utils.getBooStrVazia(_dirCompleto)) {

        return _dirCompleto;
      }

      if (!Utils.getBooStrVazia(this.getDir())) {

        _dirCompleto = "_dir/_str_nome";
      }
      else {

        _dirCompleto = "../_str_nome";
      }

      _dirCompleto = _dirCompleto.replace("_dir", this.getDir() != null ? this.getDir() : Utils.STR_VAZIA);
      _dirCompleto = _dirCompleto.replace("_str_nome", this.getStrNome());
      _dirCompleto = _dirCompleto.replace("//", "/");
    }
    catch (Exception ex) {

      new Erro(App.getI().getStrTextoPadrao(0), ex);
    }
    finally {
    }

    return _dirCompleto;
  }

  public String getStrConteudo() {

    FileInputStream fis;

    try {

      if (!Utils.getBooStrVazia(_strConteudo)) {

        return _strConteudo;
      }

      if (Utils.getBooStrVazia(this.getDirCompleto())) {

        return Utils.STR_VAZIA;
      }

      if (!this.getBooExiste()) {

        return Utils.STR_VAZIA;
      }

      fis = new FileInputStream(this.getDirCompleto());
      _strConteudo = IOUtils.toString(fis, "UTF-8");
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }

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

      if (Utils.getBooStrVazia(_dirCompleto)) {

        return;
      }

      fil = new File(_dirCompleto);

      this.setStrNome(fil.getName());
      this.setDir(fil.getPath().replace(this.getStrNome(), Utils.STR_VAZIA));
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