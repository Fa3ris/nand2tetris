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

  public static Token classToken() {
    return Token.build(TokenType.KEYWORD, "class");
  }

  public static Token identifierToken(String name) {
    return Token.build(TokenType.IDENTIFIER, name);
  }

  public static Token openBrace() {
    return Token.build(TokenType.SYMBOL, "{");
  }

  public static Token closeBrace() {
    return Token.build(TokenType.SYMBOL, "}");
  }

  public static Token field() {
    return Token.build(TokenType.KEYWORD, "field");
  }

  public static Token intToken() {
    return Token.build(TokenType.KEYWORD, "int");
  }

  public static Token semicolon() {
    return Token.build(TokenType.SYMBOL, ";");
  }

  public static Token comma() {
    return Token.build(TokenType.SYMBOL, ",");
  }

  public static Token staticToken() {
    return Token.build(TokenType.KEYWORD, "static");
  }

  public static Token charToken() {
    return Token.build(TokenType.KEYWORD, "char");
  }

  public static Token booleanToken() {
    return Token.build(TokenType.KEYWORD, "boolean");
  }
}
