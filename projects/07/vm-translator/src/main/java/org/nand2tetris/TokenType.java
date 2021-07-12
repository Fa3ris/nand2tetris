package org.nand2tetris;

public enum TokenType {

  EOF,

  INTEGER,

  PUSH,
  POP,

  ADD,
  SUB,
  NEG,

  EQ,
  GT,
  LT,

  AND,
  OR,
  NOT,

  ARGUMENT,
  LOCAL,
  STATIC,
  CONSTANT,
  THIS,
  THAT,
  POINTER,
  TEMP,

  LABEL_DEFINITION,
  IDENTIFIER,

  GOTO,
  IF_GOTO,

  RETURN,
  FUNCTION,
  CALL,
}
