package org.nand2tetris.generator.symbol_table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
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

  public String description() {
    List<TableEntry> entries = new ArrayList<>(classTable.size() + subroutineTable.size());

    entries.addAll(classTable.values());
    entries.addAll(subroutineTable.values());

    if (entries.isEmpty()) {
      return "\t\tno entry in table";
    }
    entries.sort(Comparator.comparing(TableEntry::getScope).thenComparing(TableEntry::getIndex));

    return entries.stream().map(entry -> "\t\t" + entry.description()).collect(Collectors.joining("\n"));
  }

  public int getFieldCount() {
    return fieldCount + 1;
  }
}
