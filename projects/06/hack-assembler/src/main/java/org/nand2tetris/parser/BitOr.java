package org.nand2tetris.parser;

import org.nand2tetris.lexer.TokenType;

public class BitOr extends BinaryOperation {

  public BitOr(Factor factor1, Factor factor2) {
    super(TokenType.BIT_OR, factor1, factor2);
  }
}
