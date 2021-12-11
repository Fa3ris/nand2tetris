package org.nand2tetris.generator;

import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

@ToString
public class Type {

  public static final Type INT = new Type("int");
  public static final Type CHAR = new Type("char");
  public static final Type BOOLEAN = new Type("boolean");

  private static final Map<String, Type> map = new HashMap<>();

  static {
    map.put("int", INT);
    map.put("char", CHAR);
    map.put("boolean", BOOLEAN);
  }

  public Type(String name) {
    this.name = name;
  }

  private final String name;

  public String name() {
    return name;
  }

  public static Type resolve(String type) {
    if (type == null || type.isEmpty()) {
      throw new IllegalArgumentException("type cannot be empty " + type);
    }
    Type res = map.get(type);
    if (res == null) {
      res = new Type(type);
      map.put(type, res);
    }
    return res;
  }

}
