package org.nand2tetris.generator;

public enum Scope {

  FIELD,
  STATIC,
  ARGUMENT,
  LOCAL;

  public static Scope toScope(String scope) {
    switch (scope) {
      case "field":
        return Scope.FIELD;
      case "static":
        return Scope.STATIC;
      case "argument":
        return Scope.ARGUMENT;
      case "local":
        return Scope.LOCAL;
      default:
        throw new IllegalArgumentException("invalid scope " + scope);
    }
  }
}
