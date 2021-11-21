package org.nand2tetris.tokenizer;

import static org.nand2tetris.tokenizer.Keyword.*;
import static org.nand2tetris.tokenizer.Symbol.*;

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
    return Token.build(TokenType.KEYWORD, CLASS);
  }

  public static Token identifierToken(String name) {
    return Token.build(TokenType.IDENTIFIER, name);
  }

  public static Token openBrace() {
    return Token.build(TokenType.SYMBOL, OPEN_BRACE);
  }

  public static Token closeBrace() {
    return Token.build(TokenType.SYMBOL, CLOSE_BRACE);
  }

  public static Token openParen() {
    return Token.build(TokenType.SYMBOL, OPEN_PAREN);
  }

  public static Token closeParen() {
    return Token.build(TokenType.SYMBOL, CLOSE_PAREN);
  }

  public static Token field() {
    return Token.build(TokenType.KEYWORD, FIELD);
  }

  public static Token intToken() {
    return Token.build(TokenType.KEYWORD, INT);
  }

  public static Token semicolon() {
    return Token.build(TokenType.SYMBOL, SEMICOLON);
  }

  public static Token comma() {
    return Token.build(TokenType.SYMBOL, COMMA);
  }

  public static Token staticToken() {
    return Token.build(TokenType.KEYWORD, STATIC);
  }

  public static Token charToken() {
    return Token.build(TokenType.KEYWORD, CHAR);
  }

  public static Token booleanToken() {
    return Token.build(TokenType.KEYWORD, BOOLEAN);
  }

  public static Token constructorToken() {
    return Token.build(TokenType.KEYWORD, CONSTRUCTOR);
  }

  public static Token voidToken() {
    return Token.build(TokenType.KEYWORD, VOID);
  }

  public static Token functionToken() {
    return Token.build(TokenType.KEYWORD, FUNCTION);
  }
  
  public static Token varToken() {
    return  Token.build(TokenType.KEYWORD, VAR);
  }

  public static Token methodToken() {
    return Token.build(TokenType.KEYWORD, METHOD);
  }

  public static Token letToken() {
    return Token.build(TokenType.KEYWORD, LET);
  }

  public static Token equalToken() {
    return Token.build(TokenType.SYMBOL, EQ);
  }

  public static Token returnToken() {
    return Token.build(TokenType.KEYWORD, RETURN);
  }

  public static Token doToken() {
    return Token.build(TokenType.KEYWORD, DO);
  }

  public static Token dot() {
    return Token.build(TokenType.SYMBOL, DOT);
  }

  public static Token ifToken() {
    return Token.build(TokenType.KEYWORD, IF);
  }

  public static Token elseToken() {
    return Token.build(TokenType.KEYWORD, ELSE);
  }

  public static Token whileToken() {
    return Token.build(TokenType.KEYWORD, WHILE);
  }
}
