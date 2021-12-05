package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.returnTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;

import java.util.ArrayList;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;

public class ReturnNode extends AbstractNode {

  private Node expression;

  @Override
  protected String parentTag() {
    return TagNames.returnStatement;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new ArrayList<>();
    tags.add(returnTag());
    if (hasExpression()) {
      tags.add(expression.toXMLString());
    }
    tags.add(semicolonTag());
    return tags;
  }

  public void setExpression(Node node) {
    expression = node;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    if (hasExpression()) {
      expression.accept(visitor);
    }
    visitor.visit(this);
  }

  public boolean hasExpression() {
    return expression != null;
  }
}
