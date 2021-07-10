package org.nand2tetris;

import java.util.HashMap;
import java.util.Map;
import lombok.Value;

@Value
public class Token {

  TokenType type;
  String lexeme;

  public final static Token EOF = new Token(TokenType.EOF, "EOF");

  private final static Token PUSH = new Token(TokenType.PUSH, "PUSH");
  private final static Token POP = new Token(TokenType.POP, "POP");

  private final static Token ADD = new Token(TokenType.ADD, "ADD");
  private final static Token SUB = new Token(TokenType.SUB, "SUB");
  private final static Token NEG = new Token(TokenType.NEG, "NEG");

  private final static Token EQ = new Token(TokenType.EQ, "EQ");
  private final static Token LT = new Token(TokenType.LT, "LT");
  private final static Token GT = new Token(TokenType.GT, "GT");

  private final static Token AND = new Token(TokenType.AND, "AND");
  private final static Token OR = new Token(TokenType.OR, "OR");
  private final static Token NOT = new Token(TokenType.NOT, "NOT");

  private final static Token ARGUMENT = new Token(TokenType.ARGUMENT, "ARGUMENT");
  private final static Token LOCAL = new Token(TokenType.LOCAL, "LOCAL");
  private final static Token THIS = new Token(TokenType.THIS, "THIS");
  private final static Token THAT = new Token(TokenType.THAT, "THAT");
  private final static Token STATIC = new Token(TokenType.STATIC, "STATIC");
  private final static Token TEMP = new Token(TokenType.TEMP, "TEMP");
  private final static Token POINTER = new Token(TokenType.POINTER, "POINTER");
  private final static Token CONSTANT = new Token(TokenType.CONSTANT, "CONSTANT");

  private final static Token LABEL = new Token(TokenType.LABEL_DEFINITION, "LABEL");

  private static final Map<TokenType, Token> table = new HashMap<>();

  static {
    table.put(TokenType.ADD, ADD);
    table.put(TokenType.SUB, SUB);
    table.put(TokenType.NEG, NEG);

    table.put(TokenType.PUSH, PUSH);
    table.put(TokenType.POP, POP);

    table.put(TokenType.EQ, EQ);
    table.put(TokenType.LT, LT);
    table.put(TokenType.GT, GT);

    table.put(TokenType.AND, AND);
    table.put(TokenType.OR, OR);

    table.put(TokenType.NOT, NOT);

    table.put(TokenType.LOCAL, LOCAL);
    table.put(TokenType.ARGUMENT, ARGUMENT);
    table.put(TokenType.THIS, THIS);
    table.put(TokenType.THAT, THAT);
    table.put(TokenType.STATIC, STATIC);
    table.put(TokenType.CONSTANT, CONSTANT);
    table.put(TokenType.POINTER, POINTER);
    table.put(TokenType.TEMP, TEMP);
    table.put(TokenType.LABEL_DEFINITION, LABEL);
  }

  public static Token get(TokenType type) {
    Token token = table.get(type);
    if (token == null) {
      throw new IllegalArgumentException("invalid TokenType " + type);
    }
    return token;
  }
}
