package org.nand2tetris.lexer;

import lombok.Value;

@Value
public class Token {

  TokenType type;

  String value;

}
