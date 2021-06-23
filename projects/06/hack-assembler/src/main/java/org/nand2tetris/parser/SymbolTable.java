package org.nand2tetris.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SymbolTable {

  private final Map<String, Integer> table = new HashMap<>();

  private int nextVarAddress = 16;

  public SymbolTable() {
    fillPredefinedSymbols();
  }

  private void fillPredefinedSymbols() {
    for (int i = 0; i < 16 ; i++) {
      String register = "R" + i;
      table.put(register, i);
    }
    table.put("SCREEN", 16384);
    table.put("KBD", 24576);

    table.put("SP", 0);
    table.put("LCL", 1);
    table.put("ARG", 2);
    table.put("THIS", 3);
    table.put("THAT", 4);
  }

  public Integer lookup(String key) {
    return table.get(key);
  }

  public Integer addLabel(String label, int labelAddr) {
    notAlreadyInTable(label);
    return table.put(label, labelAddr);
  }

  public Integer addVariable(String var) {
    notAlreadyInTable(var);
    return table.put(var, nextVarAddress++);
  }

  private void notAlreadyInTable(String key) {
    if (table.get(key) != null) {
      throw new SymbolAlreadyDefinedException(key);
    }
  }

}
