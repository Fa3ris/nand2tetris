package org.nand2tetris.parser;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AST {

  @Getter
  private final Node program;

  public List<Node> instructions() {
    return program.getChildren();
  }
}
