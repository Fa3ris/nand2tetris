package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.formatStatements;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.whileTag;

import java.util.ArrayList;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;

public class WhileNode extends AbstractNode {

  private Node expression;
  private final List<Node> statements = new ArrayList<>();

  public void setExpression(Node node) {
    expression = node;
  }

  public void addStatements(List<Node> nodes) {
    statements.addAll(nodes);
  }

  @Override
  protected String parentTag() {
    return TagNames.whileStatement;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new ArrayList<>();
    tags.add(whileTag());
    tags.add(openParenTag());
    tags.add(expression.toXMLString());
    tags.add(closeParenTag());
    tags.add(openBraceTag());
    tags.addAll(formatStatements(statements));
    tags.add(closeBraceTag());
    return tags;
  }
}
