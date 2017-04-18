/**/
package com.digosofter.digojava;

/**
 * Principal classe da aplicação. Esta classe deve ser implementada/estendida pela classe especializada da sua aplicação. Ela controla todo o ciclo de
 * vida da aplicação como um todo deste que o usuário a inicia, até a sua conclusão. Esta classe não pode ser instanciada, pois precisa
 * necessariamente ser implementada/estendida por outra classe que receberá as especificações da aplicação que está sendo construída.
 *
 * @author r-vieira
 */
public abstract class App extends Objeto
{
  private static App _i;

  /**
   * @return Retorna a única instância desta classe durante o ciclo de vida da aplicação. Esta instância é carregada automaticamente quando a classe
   * que estende esta é construída.
   */
  public static App getI()
  {
    return _i;
  }

  private long _intMilisegLigado;
  private long _intSegLigado;
  private long _intStartTime;
  private int _intVersao = 1;
  private String _strVersao;

  /**
   * O construtor não é público, pois esta classe não pode ser construída diretamente. Ela necessariamente precisa ser implementada/estendida por
   * outra classe que conterá as especificações da aplicação que está sendo desenvolvida.
   */
  protected App()
  {
    this.setI(this);

    this.iniciar();
  }

  /**
   * @return Retorna a quantidade de milissegundos que a aplicação está rodando.
   */
  public long getIntMilisegLigado()
  {
    _intMilisegLigado = (System.currentTimeMillis() - this.getIntStartTime());

    return _intMilisegLigado;
  }

  /**
   * @return Retorna a quantidade de segundos que a aplicação está rodando.
   */
  public long getIntSegLigado()
  {
    _intSegLigado = (this.getIntMilisegLigado() / 1000);

    return _intSegLigado;
  }

  private long getIntStartTime()
  {
    return _intStartTime;
  }

  /**
   * @return Retorna um inteiro que representa a versão desta aplicação.
   */
  public int getIntVersao()
  {
    return _intVersao;
  }

  public String getStrVersao()
  {
    if (_strVersao != null)
    {
      return _strVersao;
    }

    _strVersao = "1.0.0 beta";

    return _strVersao;
  }

  protected void inicializar()
  {
    this.setIntStartTime(System.currentTimeMillis());
  }

  private void iniciar()
  {
    this.inicializar();
    this.setEventos();
  }

  protected void setEventos()
  {
  }

  private void setI(App i)
  {
    if (_i != null)
    {
      return;
    }

    _i = i;
  }

  private void setIntStartTime(long intStartTime)
  {
    _intStartTime = intStartTime;
  }

  public void setIntVersao(int intVersao)
  {
    _intVersao = intVersao;
  }

  public void setStrVersao(String strVersao)
  {
    _strVersao = strVersao;
  }
}