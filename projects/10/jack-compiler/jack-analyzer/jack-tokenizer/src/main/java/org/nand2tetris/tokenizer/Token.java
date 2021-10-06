package org.nand2tetris.tokenizer;

import lombok.Data;

@Data
public class Token {
  private TokenType type;
  private String lexeme;
}
