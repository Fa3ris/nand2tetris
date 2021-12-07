package org.nand2tetris.generator.symbol_table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.nand2tetris.generator.Scope;
import org.nand2tetris.generator.Type;

@EqualsAndHashCode
@ToString
@Getter
public class TableEntry {

  private final String name;
  private final Type type;
  private final Scope scope;
  private final int index;

  public Scope getScope() {
    return scope;
  }

  public int getIndex() {
    return index;
  }

  public TableEntry(String name, Type type, Scope scope, int index) {
    this.name = name;
    this.type = type;
    this.scope = scope;
    this.index = index;
  }

  public String description() {
    return String.format("%s %s %s %s", type.name(), name, scope.name(), index);
  }
}
