package org.nand2tetris.lexer;

public class StateHelper {

  static final State IDENTIFIER = new Identifier();
  static final State INTEGER = new Integer();

  static final State LINE_COMMENT = new LineComment();

  static final State SLASH = new Slash();

  static final State START = new Start();

}
