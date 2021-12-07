package org.nand2tetris.generator;

import lombok.ToString;

@ToString
public class Type {

  public static final Type INT = new Type("int");
  public static final Type CHAR = new Type("char");
  public static final Type BOOLEAN = new Type("boolean");


  public Type(String name) {
    this.name = name;
  }

  private final String name;

  public static Type resolve(String type) {
    if (type == null || type.isEmpty()) {
      throw new IllegalArgumentException("type cannot be empty " + type);
    }
    switch (type) {
      case "int":
        return INT;
      case "char":
        return CHAR;
      case "boolean":
        return BOOLEAN;
    }
    return new Type(type);
  }

}
