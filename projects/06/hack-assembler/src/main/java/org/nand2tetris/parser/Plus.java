package org.nand2tetris.parser;

import lombok.Getter;
import org.nand2tetris.lexer.TokenType;

@Getter
public class Plus extends BinaryOperation {

  public Plus(Factor factor1, Factor factor2) {
    super(TokenType.PLUS, factor1, factor2);
  }

}
