package com.digosofter.digojava;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjetoTest extends TestMain {

  private Objeto _obj;

  private Objeto getObj() {

    return _obj;
  }

  @Override
  protected void inicializar() {

    this.setObj(new Objeto() {});
  }

  private void setObj(Objeto obj) {

    _obj = obj;
  }

  @Test
  public void testGetIntObjetoId() {

    Objeto obj;

    for (int i = 0; i < 100; i++) {

      obj = new Objeto() {};

      assertEquals(i + 1, obj.getIntObjetoId());
    }
  }

  @Test
  public void testGetStrDescricao() {

    for (int i = 0; i < 10; i++) {

      this.getObj().setStrDescricao("Descri��o: " + i);
      assertEquals("Descri��o: " + i, this.getObj().getStrDescricao());
    }
  }

  @Test
  public void testGetStrNome() {

    for (int i = 0; i < 10; i++) {

      this.getObj().setStrNome("Nome: " + i);
      assertEquals("Nome: " + i, this.getObj().getStrNome());
    }
  }

  @Test
  public void testGetStrNomeExibicao() {

    for (int i = 0; i < 10; i++) {

      this.getObj().setStrNomeExibicao("Nome de exibi��o: " + i);
      assertEquals("Nome de exibi��o: " + i, this.getObj().getStrNomeExibicao());
    }
  }

  @Test
  public void testGetStrNomeSimplificado() {

    this.getObj().setStrNome("NOME EM CAIXA ALTA COM ESPA�OS");
    assertEquals("nome_em_caixa_alta_com_espacos", this.getObj().getStrNomeSimplificado());
  }
}