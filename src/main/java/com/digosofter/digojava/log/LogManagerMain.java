package com.digosofter.digojava.log;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class LogManagerMain extends Objeto
{
  private List<Log> _lstLog;

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

  private List<Log> getLstLog()
  {
    if (_lstLog != null)
    {
      return _lstLog;
    }

    _lstLog = new ArrayList<>();

    return _lstLog;
  }

  protected void onLogAdicionado(final Log log)
  {
    if (log == null)
    {
      return;
    }

    System.out.println(log.getStrLogFormatado());
  }
}