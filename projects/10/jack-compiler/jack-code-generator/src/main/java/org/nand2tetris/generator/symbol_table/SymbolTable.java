package org.nand2tetris.generator.symbol_table;

import java.util.HashMap;
import java.util.Map;
import org.nand2tetris.generator.Scope;
import org.nand2tetris.generator.Type;

public class SymbolTable {

  private final Map<String, TableEntry> classTable = new HashMap<>();
  private final Map<String, TableEntry> subroutineTable = new HashMap<>();

  private int fieldCount = -1;
  private int staticCount = -1;

  private int localCount = -1;
  private int argumentCount = -1;

  public void define(String name, Type type, Scope scope) {
    if (scope == Scope.LOCAL || scope == Scope.ARGUMENT) {
      addSubroutineTable(name, type, scope);
      return;
    }
    int index;
    if (scope == Scope.STATIC) {
      index = ++staticCount;
    } else {
      index = ++fieldCount;
    }
    TableEntry newEntry = new TableEntry(name, type, scope, index);
    classTable.put(name, newEntry);
  }


  private void addSubroutineTable(String name, Type type, Scope scope) {
    int index;
    if (scope == Scope.LOCAL) {
      index = ++localCount;
    } else {
      index = ++argumentCount;
    }
    TableEntry newEntry = new TableEntry(name, type, scope, index);
    subroutineTable.put(name, newEntry);
  }

  public TableEntry get(String name) {
    if (subroutineTable.containsKey(name))
      return subroutineTable.get(name);
    return classTable.get(name);
  }

  public void resetSubroutine() {
    subroutineTable.clear();
    localCount = -1;
    argumentCount = -1;
  }

  public int getLocalCount() {
    return localCount + 1;
  }
}
