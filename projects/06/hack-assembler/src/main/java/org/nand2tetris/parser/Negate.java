package org.nand2tetris.parser;

import org.nand2tetris.lexer.TokenType;

public class Negate extends UnaryOperation {

  public Negate(Factor factor) {
    super(TokenType.MINUS, factor);
  }

}
