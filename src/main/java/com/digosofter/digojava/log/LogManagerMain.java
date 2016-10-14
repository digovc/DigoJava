package com.digosofter.digojava.log;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class LogManagerMain extends Objeto
{
  private static final int INT_LIMITE_LOG_RESUMIDO = 100;

  private List<Log> _lstLog;
  private String _strLog;
  private String _strLogResumido;

  public LogManagerMain(String strNome)
  {
    this.setStrNome(strNome);
  }

  public void addLog(Log log)
  {
    if (log == null)
    {
      return;
    }

    if (this.getLstLog().contains(log))
    {
      return;
    }

    this.getLstLog().add(log);

    log.setLogManagerMain(this);

    this.setStrLog(null);
    this.setStrLogResumido(null);

    this.onLogAdicionado(log);
  }

  public void addLog(Log.EnmTipo enmTipo, final String strLog)
  {
    if (Utils.getBooStrVazia(strLog))
    {
      return;
    }

    this.addLog(new Log(enmTipo, strLog));
  }

  protected List<Log> getLstLog()
  {
    if (_lstLog != null)
    {
      return _lstLog;
    }

    _lstLog = new ArrayList<>();

    return _lstLog;
  }

  public String getStrLog()
  {
    if (_strLog != null)
    {
      return _strLog;
    }

    StringBuilder stbLog = new StringBuilder();

    for (Log log : this.getLstLog())
    {
      if (log == null)
      {
        continue;
      }

      stbLog.append(log.getStrLogFormatado());
    }

    _strLog = stbLog.toString();

    return _strLog;
  }

  public String getStrLogResumido()
  {
    if (_strLogResumido != null)
    {
      return _strLogResumido;
    }

    StringBuffer stbLog = new StringBuffer();

    int i = (this.getLstLog().size() - INT_LIMITE_LOG_RESUMIDO);

    i = (i > 0) ? i : 0;

    for (; i < this.getLstLog().size(); i++)
    {
      stbLog.append(this.getLstLog().get(i).getStrLogFormatado());
      stbLog.append("\n");
    }

    _strLogResumido = stbLog.toString();

    return _strLogResumido;
  }

  protected void onLogAdicionado(final Log log)
  {
    if (log == null)
    {
      return;
    }

    System.out.println(log.getStrLogFormatado());
  }

  private void setStrLog(final String strLog)
  {
    _strLog = strLog;
  }

  private void setStrLogResumido(final String strLogResumido)
  {
    _strLogResumido = strLogResumido;
  }
}