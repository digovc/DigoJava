package com.digosofter.digojava.dominio;

import com.digosofter.digojava.Objeto;
import com.digosofter.digojava.Utils;
import com.digosofter.digojava.database.UtilsDataBase;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class DominioMain extends Objeto
{
  private boolean _booAtivo;
  private GregorianCalendar _dttAlteracao;
  private GregorianCalendar _dttCadastro;
  private int _intId;
  private int _intUsuarioAlteracaoId;
  private int _intUsuarioCadastroId;

  /**
   * Carrega os dados contidos na posição atual do ResultSet nos atributos desta instância.
   */
  public void carregarDados(ResultSet rst)
  {
    if (rst == null)
    {
      return;
    }

    try
    {
      if (rst.getMetaData().getColumnCount() < 1)
      {
        return;
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    List<Integer> lstIntClnIndexCarregada = new ArrayList<>();

    this.carregarDados(rst, this.getClass(), lstIntClnIndexCarregada);
  }

  private void carregarDados(ResultSet rst, Class<?> cls, List<Integer> lstIntClnIndexCarregada)
  {
    if (cls == null)
    {
      return;
    }

    this.carregarDados(rst, cls.getSuperclass(), lstIntClnIndexCarregada);

    for (Field objField : cls.getDeclaredFields())
    {
      this.carregarDados(rst, objField, lstIntClnIndexCarregada);
    }
  }

  private void carregarDados(ResultSet rst, Field objField, int intClnIndex)
  {
    try
    {
      objField.setAccessible(true);

      if (boolean.class.equals(objField.getType()))
      {
        objField.set(this, UtilsDataBase.getBoo(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (double.class.equals(objField.getType()))
      {
        objField.set(this, UtilsDataBase.getDbl(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (GregorianCalendar.class.equals(objField.getType()))
      {
        objField.set(this, UtilsDataBase.getDtt(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (int.class.equals(objField.getType()))
      {
        objField.set(this, UtilsDataBase.getInt(rst, rst.getMetaData().getColumnName(intClnIndex)));
        return;
      }

      if (String.class.equals(objField.getType()))
      {
        objField.set(this, UtilsDataBase.getStr(rst, rst.getMetaData().getColumnName(intClnIndex)));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      objField.setAccessible(false);
    }
  }

  private void carregarDados(ResultSet rst, Field objField, int intClnIndex, List<Integer> lstIntClnIndexCarregada)
  {
    String strClnNomeSimplificado = null;

    try
    {
      strClnNomeSimplificado = rst.getMetaData().getColumnName(intClnIndex);
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }

    if (strClnNomeSimplificado == null)
    {
      return;
    }

    strClnNomeSimplificado = Utils.simplificar(strClnNomeSimplificado);
    strClnNomeSimplificado = strClnNomeSimplificado.replace("_", "");

    String strFieldNomeSimplificado = objField.getName();

    strFieldNomeSimplificado = Utils.simplificar(strFieldNomeSimplificado);
    strFieldNomeSimplificado = strFieldNomeSimplificado.replace("_", "");

    if (!strClnNomeSimplificado.equals(strFieldNomeSimplificado))
    {
      return;
    }

    this.carregarDados(rst, objField, intClnIndex);

    lstIntClnIndexCarregada.add(intClnIndex);
  }

  private void carregarDados(ResultSet rst, Field objField, List<Integer> lstIntClnIndexCarregada)
  {
    if (objField == null)
    {
      return;
    }

    try
    {
      for (int intClnIndex = 1; intClnIndex <= rst.getMetaData().getColumnCount(); intClnIndex++)
      {
        if (lstIntClnIndexCarregada.contains(intClnIndex))
        {
          continue;
        }

        this.carregarDados(rst, objField, intClnIndex, lstIntClnIndexCarregada);
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      objField.setAccessible(false);
    }
  }

  public boolean getBooAtivo()
  {
    return _booAtivo;
  }

  public GregorianCalendar getDttAlteracao()
  {
    return _dttAlteracao;
  }

  public GregorianCalendar getDttCadastro()
  {
    return _dttCadastro;
  }

  public int getIntId()
  {
    return _intId;
  }

  public int getIntUsuarioAlteracaoId()
  {
    return _intUsuarioAlteracaoId;
  }

  public int getIntUsuarioCadastroId()
  {
    return _intUsuarioCadastroId;
  }

  public void setBooAtivo(boolean booAtivo)
  {
    _booAtivo = booAtivo;
  }

  public void setDttAlteracao(GregorianCalendar dttAlteracao)
  {
    _dttAlteracao = dttAlteracao;
  }

  public void setDttCadastro(GregorianCalendar dttCadastro)
  {
    _dttCadastro = dttCadastro;
  }

  public void setIntId(int intId)
  {
    _intId = intId;
  }

  public void setIntUsuarioAlteracaoId(int intUsuarioAlteracaoId)
  {
    _intUsuarioAlteracaoId = intUsuarioAlteracaoId;
  }

  public void setIntUsuarioCadastroId(int intUsuarioCadastroId)
  {
    _intUsuarioCadastroId = intUsuarioCadastroId;
  }
}