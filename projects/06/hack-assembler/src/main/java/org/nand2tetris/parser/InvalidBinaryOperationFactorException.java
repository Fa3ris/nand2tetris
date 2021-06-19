package org.nand2tetris.parser;

import org.nand2tetris.lexer.TokenType;

public class InvalidBinaryOperationFactorException extends RuntimeException {

  private final TokenType operator;
  private final Factor factor1;
  private final Factor factor2;


  public InvalidBinaryOperationFactorException(TokenType operator, Factor factor1,
      Factor factor2) {
    this.operator = operator;
    this.factor1 = factor1;
    this.factor2 = factor2;
  }
}
