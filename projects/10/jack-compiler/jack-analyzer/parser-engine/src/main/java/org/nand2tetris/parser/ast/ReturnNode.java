package org.nand2tetris.parser.ast;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;

public class ReturnNode extends AbstractNode {

  private Node expression;

  @Override
  protected String parentTag() {
    return TagNames.returnStatement;
  }

  @Override
  protected List<String> childrenTags() {
    return Arrays.asList(
        XMLUtils.returnTag(),
        XMLUtils.semicolonTag());
  }

  public void setExpression(Node node) {
    expression = node;
  }
}
