package org.nand2tetris.lexer;

public interface TokenStream {

  Token nextToken();

  Token peekToken();
}
