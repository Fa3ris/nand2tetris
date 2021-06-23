package org.nand2tetris.parser;

public class SymbolAlreadyDefinedException extends RuntimeException {

  private final String symbol;


  public SymbolAlreadyDefinedException(String symbol) {
    this.symbol = symbol;
  }
}
