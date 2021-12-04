package org.nand2tetris.parser.ast;

public interface Node {

  String toXMLString();

  void accept(NodeVisitor visitor);
}
