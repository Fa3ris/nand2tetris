package org.nand2tetris.parser.ast;

import java.util.ArrayList;
import java.util.List;

public class JackAST implements AST {

  private final List<Node> nodes = new ArrayList<>();

  @Override
  public Node getRoot() {
    return nodes.get(0);
  }

  @Override
  public String toXMLString() {
    StringBuilder sb = new StringBuilder();
    for (Node node : nodes) {
      sb.append(node.toXMLString());
    }
    return sb.toString();
  }

  @Override
  public void addNode(Node node) {
    nodes.add(node);
  }
}
