package org.nand2tetris.lexer;

import java.util.HashMap;
import java.util.Map;

public class KeywordTable {


  private static final Map<String, TokenType> table = new HashMap<>();

  static {
    table.put("A", TokenType.A);
    table.put("D", TokenType.D);
    table.put("M", TokenType.M);

    table.put("AD", TokenType.AD);
    table.put("AM", TokenType.AM);
    table.put("MD", TokenType.MD);
    table.put("AMD", TokenType.AMD);

    table.put("JEQ", TokenType.JEQ);
    table.put("JGE", TokenType.JGE);
    table.put("JGT", TokenType.JGT);
    table.put("JLE", TokenType.JLE);
    table.put("JLT", TokenType.JLT);
    table.put("JMP", TokenType.JMP);
    table.put("JNE", TokenType.JNE);
  }

  static TokenType lookup(String str) {
    return table.get(str);
  }

}
