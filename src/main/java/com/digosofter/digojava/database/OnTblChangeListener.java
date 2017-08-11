package com.digosofter.digojava.database;

public interface OnTblChangeListener
{
  /**
   * Evento disparado quando um registro for adicionado para tabela.
   *
   * @param arg Argumentos do evento.
   */
  void onTabelaAdicionar(OnChangeArg arg);

  /**
   * Evento disparado quando um registro for apagado para tabela.
   *
   * @param arg Argumentos do evento.
   */
  void onTabelaApagar(OnChangeArg arg);

  /**
   * Evento disparado quando um registro for atualizado para tabela.
   *
   * @param arg Argumentos do evento.
   */
  void onTabelaAtualizar(OnChangeArg arg);
}