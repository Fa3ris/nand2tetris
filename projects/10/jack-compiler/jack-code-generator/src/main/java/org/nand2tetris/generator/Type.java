package org.nand2tetris.generator;

public class Type {

  public static final Type INT = new Type("int");
  public static final Type CHAR = new Type("char");
  public static final Type BOOLEAN = new Type("boolean");

  public Type(String name) {
    this.name = name;
  }

  private final String name;
}
