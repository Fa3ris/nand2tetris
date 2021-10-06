package org.nand2tetris.tokenizer;

public interface CharReader {

  /**
   * go to next char or set EOF
   */
  void advance();
  /**
   * return current char
   * call after isEOF
   */
  char peekChar();
  boolean isEOF();
}
