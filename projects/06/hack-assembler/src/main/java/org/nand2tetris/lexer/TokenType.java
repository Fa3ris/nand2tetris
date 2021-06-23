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

  NULL,
}
