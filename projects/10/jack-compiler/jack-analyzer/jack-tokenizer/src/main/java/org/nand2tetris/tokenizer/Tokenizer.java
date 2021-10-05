package org.nand2tetris.tokenizer;

public interface Tokenizer {
  /**
   * construct next token
   */
  void advance();
  /**
   * call after hasToken
   */
  Token peekToken();
  boolean hasToken();
}
