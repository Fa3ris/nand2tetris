package org.nand2tetris.parser.ast;

public interface AST {

  Node getRoot();

  String toXMLString();

  void addNode(Node node);
}
