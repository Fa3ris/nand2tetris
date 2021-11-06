package org.nand2tetris.parser;

public class JackAST implements AST {

  private Node root;

  @Override
  public Node getRoot() {
    return root;
  }

  @Override
  public String toXMLString() {
    return "<class><keyword> class </keyword><identifier> Main </identifier><symbol> { </symbol><symbol> } </symbol></class>";
  }
}
