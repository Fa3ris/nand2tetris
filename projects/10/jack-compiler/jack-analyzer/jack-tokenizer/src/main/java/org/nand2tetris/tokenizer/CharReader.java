package org.nand2tetris.tokenizer;

interface CharReader {

  /**
   * go to next char or set EOF
   */
  void advance();
  /**
   * call after isEOF
   */
  char peekChar();
  boolean isEOF();
}
