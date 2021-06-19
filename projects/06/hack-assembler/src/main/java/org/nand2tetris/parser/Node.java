package org.nand2tetris.parser;

import java.util.Collections;
import java.util.List;

public interface Node {

  default List<Node> getChildren() {
    return Collections.emptyList();
  }

}
