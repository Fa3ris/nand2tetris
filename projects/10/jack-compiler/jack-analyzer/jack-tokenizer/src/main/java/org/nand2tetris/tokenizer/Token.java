package org.nand2tetris.tokenizer;

import java.util.Objects;
import lombok.Data;

@Data
public class Token {
  private TokenType type;
  private String lexeme;
  
  public static Token build(TokenType type, String lexeme) {
    Token token = new Token();
    token.type = Objects.requireNonNull(type);
    token.lexeme = Objects.requireNonNull(lexeme);
    return token;
  }
}
