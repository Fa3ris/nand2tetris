package org.nand2tetris.parser;

import org.nand2tetris.lexer.Token;

public class UnexpectedTokenException extends RuntimeException {

  private Token actual;
  private Token expected;

  public UnexpectedTokenException(Token actual) {
    super();
    this.actual = actual;
  }

  public UnexpectedTokenException(Token expected, Token actual) {
    super();
    this.expected = expected;
    this.actual = actual;
  }
}
