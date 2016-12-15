package com.digosofter.digojava.arquivo;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

public abstract class Arquivo extends Objeto
{
  private boolean _booExiste;
  private String _dir;
  private String _dirCompleto;
  private String _strConteudo;

  /**
   * Faz uma cópia deste arquivo para outra diretório.
   */
  public void copiar(String dirDestino)
  {
    if (Utils.getBooStrVazia(dirDestino))
    {
      return;
    }

    if (!this.getBooExiste())
    {
      return;
    }

    this.criarDiretorio(dirDestino);

    File fil = new File(dirDestino + "/" + this.getStrNome());

    try
    {
      if (!fil.exists())
      {
        fil.createNewFile();
      }

      FileOutputStream filCopia = new FileOutputStream(fil);
      FileInputStream filOriginal = new FileInputStream(this.getDirCompleto());

      int i;

      byte[] arrBte = new byte[1024];

      while ((i = filOriginal.read(arrBte)) > 0)
      {
        filCopia.write(arrBte, 0, i);
      }

      filCopia.close();
      filOriginal.close();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public void criarArquivo()
  {
    if (this.getBooExiste())
    {
      return;
    }

    try
    {
      PrintWriter objPrintWriter = new PrintWriter(this.getDirCompleto(), "UTF-8");

      objPrintWriter.print(this.getStrConteudo());
      objPrintWriter.close();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void criarDiretorio()
  {
    this.criarDiretorio(this.getDir());
  }

  private void criarDiretorio(String dir)
  {
    if (Utils.getBooStrVazia(dir))
    {
      return;
    }

    File fil = new File(dir);

    if (fil.exists())
    {
      return;
    }

    fil.mkdirs();
  }

  protected boolean getBooExiste()
  {
    if (Utils.getBooStrVazia(this.getDirCompleto()))
    {
      return false;
    }

    _booExiste = new File(this.getDirCompleto()).exists();

    return _booExiste;
  }

  public String getDir()
  {
    return _dir;
  }

  public String getDirCompleto()
  {
    if (_dirCompleto != null)
    {
      return _dirCompleto;
    }

    if (!Utils.getBooStrVazia(this.getDir()))
    {
      _dirCompleto = "_dir/_str_nome";
    }
    else
    {
      _dirCompleto = "../_str_nome";
    }

    _dirCompleto = _dirCompleto.replace("_dir", (this.getDir() != null) ? this.getDir() : Utils.STR_VAZIA);
    _dirCompleto = _dirCompleto.replace("_str_nome", this.getStrNome());
    _dirCompleto = _dirCompleto.replace("//", "/");

    return _dirCompleto;
  }

  public String getStrConteudo()
  {
    if (_strConteudo != null)
    {
      return _strConteudo;
    }

    if (Utils.getBooStrVazia(this.getDirCompleto()))
    {
      return null;
    }

    if (!this.getBooExiste())
    {
      return null;
    }

    try
    {
      FileInputStream fis = new FileInputStream(this.getDirCompleto());

      _strConteudo = IOUtils.toString(fis, "UTF-8");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    return _strConteudo;
  }

  /**
   * Salva o arquivo no diretório indicado no atributo "dirCompleto".
   */
  public void salvar()
  {
    if (Utils.getBooStrVazia(this.getStrConteudo()))
    {
      return;
    }

    this.criarDiretorio();

    File fil = new File(this.getDirCompleto());

    try
    {
      if (!fil.exists())
      {
        fil.createNewFile();
      }

      FileWriter filWriter = new FileWriter(fil);

      filWriter.write(this.getStrConteudo());
      filWriter.close();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public void setDir(String dir)
  {
    _dir = dir;
  }

  public void setDirCompleto(String dirCompleto)
  {
    if (_dirCompleto == dirCompleto)
    {
      return;
    }

    _dirCompleto = dirCompleto;

    if (Utils.getBooStrVazia(_dirCompleto))
    {
      return;
    }

    File fil = new File(_dirCompleto);

    this.setStrNome(fil.getName());

    this.setDir(fil.getPath().replace(this.getStrNome(), Utils.STR_VAZIA));
  }

  public void setStrConteudo(String strConteudo)
  {
    _strConteudo = strConteudo;
  }
}