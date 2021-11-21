package org.nand2tetris.parser.ast;

import java.util.ArrayList;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;

public class ExpressionListNode extends AbstractNode {

  private final List<Node> expressions = new ArrayList<>();

  @Override
  protected String parentTag() {
    return TagNames.expressionListTag;
  }

  @Override
  protected List<String> childrenTags() {
    return XMLUtils.formatNodes(expressions);
  }

  public void addExpression(Node node) {
    expressions.add(node);
  }
}
