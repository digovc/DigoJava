package com.digosofter.digojava.database;

import java.util.GregorianCalendar;

import com.digosofter.digojava.Objeto;

public abstract class Dominio extends Objeto {

  private boolean _booAtivo;
  private GregorianCalendar _dttAlteracao;
  private GregorianCalendar _dttCadastro;
  private int _intId;

  public boolean getBooAtivo() {

    return _booAtivo;
  }

  public GregorianCalendar getDttAlteracao() {

    return _dttAlteracao;
  }

  public GregorianCalendar getDttCadastro() {

    return _dttCadastro;
  }

  public int getIntId() {

    return _intId;
  }

  public void setBooAtivo(boolean booAtivo) {

    _booAtivo = booAtivo;
  }

  public void setDttAlteracao(GregorianCalendar dttAlteracao) {

    _dttAlteracao = dttAlteracao;
  }

  public void setDttCadastro(GregorianCalendar dttCadastro) {

    _dttCadastro = dttCadastro;
  }

  public void setIntId(int intId) {

    _intId = intId;
  }
}