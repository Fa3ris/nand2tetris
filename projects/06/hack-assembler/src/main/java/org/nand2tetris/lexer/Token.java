package org.nand2tetris.lexer;

import lombok.Value;

@Value
public class Token {

  public static Token NULL = new Token(TokenType.NULL, "null");

  TokenType type;

  String value;

  @Override
  public String toString() {
    return String.format("{ type: \"%s\", value: \"%s\" }", type, value);
  }
}
