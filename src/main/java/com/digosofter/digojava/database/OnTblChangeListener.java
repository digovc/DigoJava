package com.digosofter.digojava.database;

public interface OnTblChangeListener {

  /**
   * Evento disparado quando um registro for adicionado para tabela.
   *
   * @param arg Argumentos do evento.
   */
  void onTblAdicionar(OnChangeArg arg);

  /**
   * Evento disparado quando um registro for apagado para tabela.
   *
   * @param arg Argumentos do evento.
   */
  void onTblApagar(OnChangeArg arg);

  /**
   * Evento disparado quando um registro for atualizado para tabela.
   *
   * @param arg Argumentos do evento.
   */
  void onTblAtualizar(OnChangeArg arg);
}