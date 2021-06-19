package org.nand2tetris.parser;

import org.nand2tetris.lexer.TokenType;

public class Not extends UnaryOperation {

  public Not(Factor factor) {
    super(TokenType.NOT, factor);
  }
}
