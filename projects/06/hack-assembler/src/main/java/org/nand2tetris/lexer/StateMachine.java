package org.nand2tetris.lexer;

public interface StateMachine {

  char read();

  char peek();

  void writeToToken();

  void writeSingleCharToken(TokenType type);

  void setEndToken(TokenType type);

  void setState(State newState);

}
