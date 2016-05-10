package com.digosofter.digojava;

import com.digosofter.digojava.erro.Erro;

public abstract class Objeto
{

  private static int _intObjetoIdStatic;

  private static int getIntObjetoIdStatic()
  {
    return _intObjetoIdStatic;
  }

  private static void setIntObjetoIdStatic(int intObjetoIdStatic)
  {
    _intObjetoIdStatic = intObjetoIdStatic;
  }

  private transient int _intObjetoId;
  private transient String _strDescricao;
  private transient String _strNome;
  private transient String _strNomeExibicao;
  private transient String _strNomeSimplificado;

  /**
   * Retorna um número único para cada uma das intâncias dos objetos que
   * decendem desta classe. O primeiro valor é 1.
   *
   * @return Número único que representa a instância deste objeto.
   */
  public int getIntObjetoId()
  {
    try
    {
      if (_intObjetoId > 0)
      {
        return _intObjetoId;
      }
      Objeto.setIntObjetoIdStatic(Objeto.getIntObjetoIdStatic() + 1);
      _intObjetoId = Objeto.getIntObjetoIdStatic();
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _intObjetoId;
  }

  public String getStrDescricao()
  {
    return _strDescricao;
  }

  public String getStrNome()
  {
    return _strNome;
  }

  public String getStrNomeExibicao()
  {
    try
    {
      if (!Utils.getBooStrVazia(_strNomeExibicao))
      {
        return _strNomeExibicao;
      }
      _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(this.getStrNome());
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _strNomeExibicao;
  }

  public String getStrNomeSimplificado()
  {
    try
    {
      if (!Utils.getBooStrVazia(_strNomeSimplificado))
      {
        return _strNomeSimplificado;
      }
      _strNomeSimplificado = Utils.simplificar(this.getStrNome());
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
    return _strNomeSimplificado;
  }

  public void setStrDescricao(String strDescricao)
  {
    _strDescricao = strDescricao;
  }

  public void setStrNome(String strNome)
  {
    try
    {
      _strNome = strNome;
      this.setStrNomeSimplificado(null);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  public void setStrNomeExibicao(String strNomeExibicao)
  {
    try
    {
      _strNomeExibicao = strNomeExibicao;
      if (Utils.getBooStrVazia(_strNomeExibicao))
      {
        return;
      }
      _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(_strNomeExibicao);
    }
    catch (Exception ex)
    {
      new Erro("Erro inesperado.\n", ex);
    }
    finally
    {
    }
  }

  private void setStrNomeSimplificado(String strNomeSimplificado)
  {
    _strNomeSimplificado = strNomeSimplificado;
  }
}