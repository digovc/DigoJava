package com.digosofter.digojava.log;

import com.digosofter.digojava.Utils;

import java.util.Calendar;

public class Log
{
  public enum EnmTipo
  {
    DEBUG,
    ERRO,
    INFO,
  }

  private Calendar _dtt = Calendar.getInstance();
  private EnmTipo _enmTipo = EnmTipo.INFO;
  private LogManagerMain _logManagerMain;
  private String _strLog;
  private String _strLogFormatado;

  public Log(EnmTipo enmTipo, String strLog)
  {
    this.setEnmTipo(enmTipo);
    this.setStrLog(strLog);
  }

  private Calendar getDtt()
  {
    return _dtt;
  }

  public EnmTipo getEnmTipo()
  {
    return _enmTipo;
  }

  private LogManagerMain getLogManagerMain()
  {
    return _logManagerMain;
  }

  private String getStrLog()
  {
    return _strLog;
  }

  public String getStrLogFormatado()
  {
    if (_strLogFormatado != null)
    {
      return _strLogFormatado;
    }

    if (Utils.getBooStrVazia(this.getStrLog()))
    {
      return null;
    }

    if (this.getLogManagerMain() == null)
    {
      return null;
    }

    if (this.getDtt() == null)
    {
      this.setDtt(Calendar.getInstance());
    }

    String strResultado = "_log_data - _log_tipo - _log_conteudo";

    strResultado = strResultado.replace("_log_data", Utils.getStrDataFormatada(this.getDtt(), Utils.EnmDataFormato.HH_MM_SS_DD_MM_YYYY));
    strResultado = strResultado.replace("_log_tipo", this.getStrTipo());
    strResultado = strResultado.replace("_log_conteudo", this.getStrLog());

    _strLogFormatado = strResultado;

    return _strLogFormatado;
  }

  private String getStrTipo()
  {
    switch (this.getEnmTipo())
    {
      case DEBUG:
        return "Debug";

      case ERRO:
        return "Erro";

      default:
        return "Info";
    }
  }

  private void setDtt(Calendar dtt)
  {
    _dtt = dtt;
  }

  private void setEnmTipo(EnmTipo enmTipo)
  {
    _enmTipo = enmTipo;
  }

  void setLogManagerMain(LogManagerMain logManagerMain)
  {
    _logManagerMain = logManagerMain;
  }

  private void setStrLog(String strLog)
  {
    _strLog = strLog;
  }
}