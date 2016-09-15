package com.digosofter.digojava;

public abstract class Objeto
{
  private static int _intObjetoIdStatic;

  private transient int _intObjetoId;
  private transient String _strDescricao;
  private String _strNome;
  private transient String _strNomeExibicao;
  private transient String _strNomeSimplificado;

  /**
   * Retorna um número único para cada uma das intâncias dos objetos que decendem desta classe. O primeiro valor é 1.
   *
   * @return Número único que representa a instância deste objeto.
   */
  public int getIntObjetoId()
  {
    if (_intObjetoId > 0)
    {
      return _intObjetoId;
    }

    Objeto.setIntObjetoIdStatic(Objeto.getIntObjetoIdStatic() + 1);

    _intObjetoId = Objeto.getIntObjetoIdStatic();

    return _intObjetoId;
  }

  private static int getIntObjetoIdStatic()
  {
    return _intObjetoIdStatic;
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
    if (_strNomeExibicao != null)
    {
      return _strNomeExibicao;
    }

    if (Utils.getBooStrVazia(this.getStrNome()))
    {
      return null;
    }

    _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(this.getStrNome());
    
    return _strNomeExibicao;
  }

  public String getStrNomeSimplificado()
  {
    if (_strNomeSimplificado != null)
    {
      return _strNomeSimplificado;
    }

    _strNomeSimplificado = Utils.simplificar(this.getStrNome());

    return _strNomeSimplificado;
  }

  private static void setIntObjetoIdStatic(int intObjetoIdStatic)
  {
    _intObjetoIdStatic = intObjetoIdStatic;
  }

  public void setStrDescricao(String strDescricao)
  {
    _strDescricao = strDescricao;
  }

  public void setStrNome(String strNome)
  {
    if (_strNome == strNome)
    {
      return;
    }

    _strNome = strNome;

    this.setStrNomeSimplificado(null);
  }

  public void setStrNomeExibicao(String strNomeExibicao)
  {
    if (_strNomeExibicao == strNomeExibicao)
    {
      return;
    }

    _strNomeExibicao = strNomeExibicao;

    _strNomeExibicao = Utils.getStrPrimeiraMaiuscula(_strNomeExibicao);
  }

  private void setStrNomeSimplificado(String strNomeSimplificado)
  {
    _strNomeSimplificado = strNomeSimplificado;
  }
}