package org.nand2tetris.parser;

import lombok.Getter;
import org.nand2tetris.lexer.Token;

public class AInstruction implements Node {

  @Getter
  private final Token address;

  public AInstruction(Token address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "AInstruction{" +
        "address=" + address +
        '}';
  }
}
