package org.nand2tetris.lexer;

public enum TokenType {
  EOF, // end of file when reach end of stream

  IDENTIFIER, // foo bar LOOP forBar foo_bar foo1Bar letter(letter|digit|underscore)*
  INTEGER, // 0 1 2 123 digit*

  // parentheses ( )
  OPEN_PAREN, CLOSE_PAREN,

  // operators @ - ! + & | = ;
  AT, MINUS, NOT, PLUS, BIT_AND, BIT_OR, ASSIGN, SEMI_COLON,

  LINE_COMMENT, /* // this is a one line comment exclude new line */

  WHITE_SPACE, // space \n \r \t

  // keywords
  A, D, M, AM, AD, MD, AMD,
  JEQ, JGE, JGT ,JLE, JLT, JMP, JNE,

  /**
   * @deprecated not used
   */
  NEW_LINE, // \n \r \r\n
  /*
  R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15,
  SCREEN, KBD,
  SP, LCL, ARG, THIS, THAT
  */
}
