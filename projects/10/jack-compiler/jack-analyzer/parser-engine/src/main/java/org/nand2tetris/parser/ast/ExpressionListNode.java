package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.commaTag;

import java.util.ArrayList;
import java.util.List;
import org.nand2tetris.parser.utils.Joiner;
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
    Joiner<String> joiner = new Joiner<>(commaTag());
    return joiner.join(XMLUtils.formatNodes(expressions));
  }

  public void addExpression(Node node) {
    expressions.add(node);
  }
}
