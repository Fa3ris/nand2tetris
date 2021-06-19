package org.nand2tetris.lexer;

import lombok.Value;

@Value
public class Token {

  TokenType type;

  String value;

  @Override
  public String toString() {
    return String.format("{ type: \"%s\", value: \"%s\" }", type, value);
  }
}
