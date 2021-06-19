package org.nand2tetris.parser;

import org.nand2tetris.lexer.TokenType;

public class BitAnd extends BinaryOperation {


  public BitAnd(Factor factor1, Factor factor2) {
    super(TokenType.BIT_AND, factor1, factor2);
  }


  @Override
  public String toString() {
    return "BitAnd{" +
        "factor1=" + factor1 +
        ", factor2=" + factor2 +
        '}';
  }
}
