package org.nand2tetris;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

  private final Map<String, TokenType> table = new HashMap<>();

  private SymbolTable parent;

  public SymbolTable() {
    init();
  }

  private void init() {

    table.put("push", TokenType.PUSH);
    table.put("pop", TokenType.POP);

    table.put("add", TokenType.ADD);
    table.put("sub", TokenType.SUB);
    table.put("neg", TokenType.NEG);

    table.put("eq", TokenType.EQ);
    table.put("lt", TokenType.LT);
    table.put("gt", TokenType.GT);

    table.put("and", TokenType.AND);
    table.put("or", TokenType.OR);
    table.put("not", TokenType.NOT);

    table.put("argument", TokenType.ARGUMENT);
    table.put("local", TokenType.LOCAL);
    table.put("this", TokenType.THIS);
    table.put("that", TokenType.THAT);
    table.put("static", TokenType.STATIC);
    table.put("temp", TokenType.TEMP);
    table.put("pointer", TokenType.POINTER);
    table.put("constant", TokenType.CONSTANT);

    table.put("label", TokenType.LABEL_DEFINITION);
    table.put("goto", TokenType.GOTO);
  }

  public TokenType lookup(String identifier) {
    TokenType type = table.get(identifier);
    if (type == null) {
      type = TokenType.IDENTIFIER;
    }
    return type;
  }

}
