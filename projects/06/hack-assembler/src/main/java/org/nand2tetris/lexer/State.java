package org.nand2tetris.lexer;

public interface State {
  void handle(StateMachine stateMachine);
  TokenType getTokenType();
}
