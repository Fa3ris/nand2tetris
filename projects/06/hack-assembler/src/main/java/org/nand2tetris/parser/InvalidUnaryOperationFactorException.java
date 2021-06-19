package org.nand2tetris.parser;

import org.nand2tetris.lexer.TokenType;

public class InvalidUnaryOperationFactorException extends RuntimeException {

  private final TokenType operator;
  private final Factor factor;


  public InvalidUnaryOperationFactorException(TokenType operator, Factor factor) {
    this.operator = operator;
    this.factor = factor;
  }
}
