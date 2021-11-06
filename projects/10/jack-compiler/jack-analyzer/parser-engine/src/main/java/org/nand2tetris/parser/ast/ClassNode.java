package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.*;

import java.util.Arrays;

public class ClassNode extends AbstractNode {

  private String className;

  public void setClassName(String name) {
    className = name;
  }

  @Override
  public String toXMLString() {
    return concat(Arrays.asList(
        openTag("class"),
        leafTag("keyword", "class"),
        leafTag("identifier", className),
        leafTag("symbol", "{"),
        leafTag("symbol", "}"),
        closeTag("class")));
  }
}
