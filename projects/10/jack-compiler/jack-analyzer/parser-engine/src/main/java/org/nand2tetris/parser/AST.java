package org.nand2tetris.parser;

public interface AST {

  Node getRoot();

  String toXMLString();
}
