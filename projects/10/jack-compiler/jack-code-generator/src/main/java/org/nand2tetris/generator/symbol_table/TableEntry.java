package org.nand2tetris.generator.symbol_table;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.nand2tetris.generator.Scope;
import org.nand2tetris.generator.Type;

@EqualsAndHashCode
@ToString
public class TableEntry {

  private final String name;
  private final Type type;
  private final Scope scope;
  private final int index;


  public TableEntry(String name, Type type, Scope scope, int index) {
    this.name = name;
    this.type = type;
    this.scope = scope;
    this.index = index;
  }
}
