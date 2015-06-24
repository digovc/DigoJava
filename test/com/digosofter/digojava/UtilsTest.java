package com.digosofter.digojava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import com.digosofter.digojava.Utils.EnmDataFormato;
import com.digosofter.digojava.Utils.EnmStrTipo;

public class UtilsTest {

  @Test
  public void testAddMascara() {

    assertEquals("9.9-9/9\\9", Utils.addMascara("99999", "*.*-*/*\\*"));
  }

  @Test
  public void testAddMascaraCep() {

    assertEquals("99.999-999", Utils.addMascaraCep("99999999"));
  }

  @Test
  public void testAddMascaraCnpj() {

    assertEquals("99.999.999/9999-99", Utils.addMascaraCnpj("99999999999999"));
  }

  @Test
  public void testAddMascaraCpf() {

    assertEquals("99.999.999-99", Utils.addMascaraCpf("9999999999"));
  }

  @Test
  public void testArredondar() {

    assertEquals(9, Utils.arredondar(9.9999, 0), 0.001);
    assertEquals(9.9, Utils.arredondar(9.9999, 1), 0.001);
    assertEquals(9.99, Utils.arredondar(9.9999, 2), 0.001);
  }

  @Test
  public void testGetBooNumeral() {

    assertTrue(Utils.getBooNumeral("9.999"));
    assertTrue(Utils.getBooNumeral("9,999"));
    assertFalse(Utils.getBooNumeral("A9,999"));
    assertFalse(Utils.getBooNumeral("aaa"));
  }

  @Test
  public void testGetBooStrVazia() {

    assertTrue(Utils.getBooStrVazia(Utils.STR_VAZIA));
    assertTrue(Utils.getBooStrVazia(null));
    assertFalse(Utils.getBooStrVazia(" "));
    assertFalse(Utils.getBooStrVazia("A"));
  }

  @Test
  public void testGetBooUrlValida() {

    assertTrue(Utils.getBooUrlValida("google.com"));
    assertTrue(Utils.getBooUrlValida("google.com/subfolder"));
  }

  @Test
  public void testGetIntNumeroAleatorio() {

    for (int i = 0; i < 10; i++) {

      assertTrue(Utils.getIntNumeroAleatorio(50) < 51 && Utils.getIntNumeroAleatorio(50) > -1);
    }
  }

  @Test
  public void testGetMd5() {

    assertEquals("BFD6EE58466FFEAFEB7915163FA27CC1", Utils.getMd5("3a1f561asd6fa"));
    assertEquals("4603867A7AFA08A5CFB63388B554E953", Utils.getMd5("f56a4sd568f1a"));
    assertEquals("1F0CB081070BB960721BD16447F01B5B", Utils.getMd5("f56r156f1a000"));
  }

  @Test
  public void testGetStrAleatoria() {

    List<String> lstStr = new ArrayList<String>();
    String strAll;

    for (int i = 0; i < 10; i++) {

      lstStr.add(Utils.getStrAleatoria(i + 10, EnmStrTipo.ALPHANUMERICO));
    }

    strAll = Utils.STR_VAZIA;

    for (int i = 0; i < 10; i++) {

      if (strAll.contains(lstStr.get(i))) {

        fail();
      }

      assertEquals(i + 10, lstStr.get(i).length());

      strAll = strAll.concat(lstStr.get(i));
    }
  }

  @Test
  public void testGetStrConcatenarLstListOfStringStringBoolean() {

    List<String> lstStr = new ArrayList<String>();
    lstStr.add("laranja");
    lstStr.add("manga");
    lstStr.add("morango");
    lstStr.add("laranja");

    assertEquals("laranja;manga;morango;laranja", Utils.getStrConcatenarLst(lstStr, ";", false));
    assertEquals("laranja;manga;morango", Utils.getStrConcatenarLst(lstStr, ";", true));
  }

  @Test
  public void testGetStrConcatenarLstStringArrayStringBoolean() {

    String[] arrStr = new String[] { "laranja", "manga", "morango", "laranja" };

    assertEquals("laranja;manga;morango;laranja", Utils.getStrConcatenarLst(arrStr, ";", false));
    assertEquals("laranja;manga;morango", Utils.getStrConcatenarLst(arrStr, ";", true));
  }

  @Test
  public void testGetStrDataFormatada() {

    assertEquals("31/12/2000 23:59:00", Utils.getStrDataFormatada(new GregorianCalendar(2000, 11, 31, 23, 59, 00), EnmDataFormato.DD_MM_YYYY_HH_MM_SS));
    assertEquals("23:59:00 31/12/2000", Utils.getStrDataFormatada(new GregorianCalendar(2000, 11, 31, 23, 59, 00), EnmDataFormato.HH_MM_SS_DD_MM_YYYY));
  }

  @Test
  public void testGetStrFixoIntInt() {

    assertEquals("0000000010", Utils.getStrFixa(10, 10));
  }

  @Test
  public void testGetStrFixoStringInt() {

    assertEquals("nome muito", Utils.getStrFixa("nome muito grande", 10));
  }

  @Test
  public void testGetStrPrimeiraMaiuscula() {

    assertEquals("Texto", Utils.getStrPrimeiraMaiuscula("texto"));
  }

  @Test
  public void testGetStrValor() {

    String strLista = Utils.STR_VAZIA;

    for (int i = 0; i < 10; i++) {

      strLista = strLista.concat("key" + i + "=valor" + i + ";");
    }

    for (int i = 0; i < 10; i++) {

      assertEquals("valor" + i, Utils.getStrValor(strLista, "key" + i));
    }
  }

  @Test
  public void testGetStrValorMonetario() {

    assertEquals("R$ 0,00", Utils.getStrValorMonetario(0));
    assertEquals("R$ 1,12", Utils.getStrValorMonetario(1.12345));
    assertEquals("R$ 100,00", Utils.getStrValorMonetario(100));
    assertEquals("R$ 9.999,00", Utils.getStrValorMonetario(9999));
  }

  @Test
  public void testGetStrValorNumerico() {

    assertEquals("1,123", Utils.getStrValorNumerico(1.12345));
  }

  @Test
  public void testGetStrValorPercentual() {

    assertEquals("1,123 %", Utils.getStrValorPercentual(1.123));
    assertEquals("99 %", Utils.getStrValorPercentual(99));
  }

  @Test
  public void testPing() {

    assertTrue(Utils.ping("google.com"));
  }

  @Test
  public void testRemoverMascara() {

    assertEquals("9999", Utils.removerMascara("9.9-9/9"));
  }

  @Test
  public void testRemoverUltimaLetraString() {

    assertEquals("texto de uma strin", Utils.removerUltimaLetra("texto de uma string"));
  }

  @Test
  public void testRemoverUltimaLetraStringInt() {

    assertEquals("texto de uma s", Utils.removerUltimaLetra("texto de uma string", 5));
  }

  @Test
  public void testSimplificar() {

    assertEquals("texto_em_caixa_alta_123", Utils.simplificar("TEXTO EM CAIXA ALTA 123"));
  }

  @Test
  public void testStrToDtt() {

    assertTrue(new GregorianCalendar(2000, 11, 31).equals(Utils.strToDtt("31/12/2000")));
    assertTrue(new GregorianCalendar(2000, 11, 31).equals(Utils.strToDtt("2000/12/31")));
  }

  @Test
  public void testToDouble() {

    assertEquals(50.12345, Utils.toDouble("50,12345"), 0.0001);
  }
}