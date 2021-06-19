package org.nand2tetris.parser;

import org.nand2tetris.lexer.Token;

public class LabelDefinition implements Node {

  private final Token label;

  public LabelDefinition(Token label) {
    this.label = label;
  }

  public String getLabel() {
    return label.getValue();
  }

  @Override
  public String toString() {
    return "LabelDefinition{" +
        "identifier=" + label +
        '}';
  }
}
