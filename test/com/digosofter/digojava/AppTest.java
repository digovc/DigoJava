package com.digosofter.digojava;

import com.digosofter.digojava.MsgUsuario.EnmLingua;
import com.digosofter.digojava.database.DbTabela;
import com.digosofter.digojava.database.Dominio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class AppTest extends TestMain {

  private App _app;
  private DbTabela<?> _tbl;

  private App getApp() {

    return _app;
  }

  private DbTabela<?> getTbl() {

    return _tbl;
  }

  @Override
  protected void inicializar() {

    this.setApp(new App() {});

    this.setTbl(new DbTabela<Dominio>("tbl_test", Dominio.class) {});
  }

  private void setApp(App app) {

    _app = app;
  }

  private void setTbl(DbTabela<?> tbl) {

    _tbl = tbl;
  }

  @Test
  public void testAddTbl() {

    this.getApp().addTbl(this.getTbl());
    assertSame(this.getTbl().getStrNome(), this.getApp().getLstTbl().get(0).getStrNome());
  }

  @Test
  public void testGetBooDebug() {

    this.getApp().setBooDebug(true);
    assertTrue(this.getApp().getBooDebug());
    this.getApp().setBooDebug(false);
    assertFalse(this.getApp().getBooDebug());
  }

  @Test
  public void testGetI() {

    assertNotNull(App.getI());
  }

  @Test
  public void testGetIntMilisegLigado() {

    assertNotSame(0, this.getApp().getIntMilisegLigado());
  }

  @Test
  public void testGetIntSegLigado() {

    assertNotSame(0, this.getApp().getIntSegLigado());
  }

  @Test
  public void testGetIntVersao() {

    this.getApp().setIntVersao(10);
    assertSame(this.getApp().getIntVersao(), 10);
    this.getApp().setIntVersao(0);
    assertSame(this.getApp().getIntVersao(), 0);
  }

  @Test
  public void testGetLstMsgUsr() {

    this.getApp().getLstMsgUsr().add(new MsgUsuario("Teste de mensagem", 0, EnmLingua.PORTUGUES_BRASIL));
    assertEquals(this.getApp().getLstMsgUsr().get(0).getStrTexto(), this.getApp().getStrMsgUsr(0));
  }

  @Test
  public void testGetLstMsgUsrPadrao() {

    assertNotNull(this.getApp().getLstMsgUsrPadrao());
  }

  @Test
  public void testGetLstTbl() {

    this.getApp().addTbl(this.getTbl());
    assertNotNull(this.getApp().getLstTbl());
  }

  @Test
  public void testGetObjGson() {

    assertNotNull(this.getApp().getObjGson());
  }

  @Test
  public void testGetStrMsgUsrInt() {

    this.getApp().getLstMsgUsr().clear();

    for (int i = 0; i < 10; i++) {

      this.getApp().getLstMsgUsr().add(new MsgUsuario("Teste de mensagem: " + i, i, EnmLingua.PORTUGUES_BRASIL));
    }

    for (int i = 0; i < 10; i++) {

      assertEquals("Teste de mensagem: " + i, this.getApp().getStrMsgUsr(i));
    }
  }

  @Test
  public void testGetStrMsgUsrIntEnmLinguaBoolean() {

    this.getApp().getLstMsgUsr().clear();

    for (int i = 0; i < 10; i++) {

      this.getApp().getLstMsgUsr().add(new MsgUsuario("Teste de mensagem: " + i, i, EnmLingua.PORTUGUES_BRASIL));
      this.getApp().getLstMsgUsr().add(new MsgUsuario("Message test: " + i, i, EnmLingua.INGLES));
    }

    for (int i = 0; i < 10; i++) {

      assertEquals("Teste de mensagem: " + i, this.getApp().getStrMsgUsr(i, EnmLingua.PORTUGUES_BRASIL, false));
      assertEquals("Message test: " + i, this.getApp().getStrMsgUsr(i, EnmLingua.INGLES, false));
    }
  }

  @Test
  public void testGetStrVersao() {

    for (int i = 0; i < 10; i++) {

      this.getApp().setStrVersao("1.0." + i);
      assertEquals("1.0." + i, this.getApp().getStrVersao());
    }
  }

  @Test
  public void testGetTblSelec() {

    this.getApp().setTblSelec(this.getTbl());
    assertSame(this.getTbl(), this.getApp().getTblSelec());
  }
}
