package org.nand2tetris.parser;

import java.util.LinkedList;
import java.util.List;

public class Program implements Node {

  private final List<Node> statements;

  public Program(List<Node> statements) {
    this.statements = statements;
  }

  @Override
  public List<Node> getChildren() {
    return statements;
  }

  @Override
  public String toString() {
    return "Program{" +
        "statements=" + statements +
        '}';
  }
}
