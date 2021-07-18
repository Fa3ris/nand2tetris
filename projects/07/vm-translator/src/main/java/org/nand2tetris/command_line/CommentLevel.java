package org.nand2tetris.command_line;

import java.util.HashMap;
import java.util.Map;

public enum CommentLevel {
  NONE("none"),
  FUNCTION_ONLY("function-only"),
  ALL("all"),
  ;

  private final String value;
  private final static Map<String, CommentLevel> map = new HashMap<>();

  CommentLevel(String value) {
    this.value = value;
  }

  public static CommentLevel getFromValue(String value) {
    return map.get(value);
  }

  static {
    for (CommentLevel level : CommentLevel.values()) {
      map.put(level.value, level);
    }
  }
}
