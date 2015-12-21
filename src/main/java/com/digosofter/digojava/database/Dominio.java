package com.digosofter.digojava.database;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.erro.Erro;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class Dominio extends Objeto {

  private boolean _booAtivo;
  private GregorianCalendar _dttAlteracao;
  private GregorianCalendar _dttCadastro;
  private int _intId;

  /**
   * Carrega os dados contidos na posição atual do ResultSet nos atributos desta instância.
   */
  public void carregarDados(ResultSet rst) {

    List<Integer> lstIntClnIndexCarregada;

    try {

      if (rst == null) {

        return;
      }

      if (rst.getMetaData().getColumnCount() < 1) {

        return;
      }

      lstIntClnIndexCarregada = new ArrayList<>();

      this.carregarDados(rst, this.getClass(), lstIntClnIndexCarregada);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  private void carregarDados(ResultSet rst, Class<?> cls, List<Integer> lstIntClnIndexCarregada) {

    try {

      if (cls == null) {

        return;
      }

      this.carregarDados(rst, cls.getSuperclass(), lstIntClnIndexCarregada);

      for (Field objField : cls.getDeclaredFields()) {

        this.carregarDados(rst, objField, lstIntClnIndexCarregada);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  private void carregarDados(ResultSet rst, Field objField, int intClnIndex) {

    try {

      objField.setAccessible(true);

      if (boolean.class.equals(objField.getType())) {

        objField.set(this, DbUtils.getBoo(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (double.class.equals(objField.getType())) {

        objField.set(this, DbUtils.getDbl(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (GregorianCalendar.class.equals(objField.getType())) {

        objField.set(this, DbUtils.getDtt(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (int.class.equals(objField.getType())) {

        objField.set(this, DbUtils.getInt(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (String.class.equals(objField.getType())) {

        objField.set(this, DbUtils.getStr(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {

      objField.setAccessible(false);
    }
  }

  private void carregarDados(ResultSet rst, Field objField, int intClnIndex, List<Integer> lstIntClnIndexCarregada) {

    String strClnNomeSimplificado;
    String strFieldNomeSimplificado;

    try {

      strClnNomeSimplificado = rst.getMetaData().getColumnName(intClnIndex);
      strClnNomeSimplificado = Utils.simplificar(strClnNomeSimplificado);
      strClnNomeSimplificado = strClnNomeSimplificado.replace("_", "");

      strFieldNomeSimplificado = objField.getName();
      strFieldNomeSimplificado = Utils.simplificar(strFieldNomeSimplificado);
      strFieldNomeSimplificado = strFieldNomeSimplificado.replace("_", "");

      if (!strClnNomeSimplificado.equals(strFieldNomeSimplificado)) {

        return;
      }

      this.carregarDados(rst, objField, intClnIndex);

      lstIntClnIndexCarregada.add(intClnIndex);
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {
    }
  }

  private void carregarDados(ResultSet rst, Field objField, List<Integer> lstIntClnIndexCarregada) {

    try {

      if (objField == null) {

        return;
      }

      for (int intClnIndex = 1; intClnIndex <= rst.getMetaData().getColumnCount(); intClnIndex++) {

        if (lstIntClnIndexCarregada.contains(intClnIndex)) {

          continue;
        }

        this.carregarDados(rst, objField, intClnIndex, lstIntClnIndexCarregada);
      }
    }
    catch (Exception ex) {

      new Erro("Erro inesperado.\n", ex);
    }
    finally {

      objField.setAccessible(false);
    }
  }

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