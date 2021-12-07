package org.nand2tetris.generator;

public enum Segment {

  CONST("constant"),
  ARG("argument"),
  LOCAL("local"),
  STATIC("static"),
  THIS("this"),
  THAT("that"),
  POINTER("pointer"),
  TMP("temp");

  private String value;
  Segment(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public static Segment resolve(Scope scope) {
    switch (scope) {
      case LOCAL:
        return LOCAL;
      case FIELD:
        return THIS;
      case STATIC:
        return STATIC;
      case ARGUMENT:
        return ARG;
      default:
        throw new IllegalArgumentException("invalid scope " + scope);
    }
  }
}
