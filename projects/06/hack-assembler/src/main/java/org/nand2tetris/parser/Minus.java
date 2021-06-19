package org.nand2tetris.parser;

import org.nand2tetris.lexer.TokenType;

public class Minus extends BinaryOperation {

  public Minus(Factor factor1, Factor factor2) {
    super(TokenType.MINUS, factor1, factor2);
  }

}
