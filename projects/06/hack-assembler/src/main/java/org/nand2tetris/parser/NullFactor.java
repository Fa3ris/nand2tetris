package org.nand2tetris.parser;

import org.nand2tetris.lexer.Token;

public class NullFactor extends Factor {

  public NullFactor() {
    super(Token.NULL);
  }
}
