package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.ifTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openParenTag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;

public class IfNode extends AbstractNode {

  private Node expression;

  private final List<Node> ifStatements = new LinkedList<>();

  boolean elseBlockPresent;
  private final List<Node> elseStatements = new LinkedList<>();


  public void addExpression(Node node) {
    expression = node;
  }

  public void addIfStatement(Node statement) {
    ifStatements.add(statement);
  }

  /**
   * need to set external to else statement because else block can be empty
   */
  public void setElseBlockPresent() {
    elseBlockPresent = true;
  }
  public void addElseStatement(Node statement) {
    elseStatements.add(statement);
  }

  @Override
  protected String parentTag() {
    return TagNames.ifStatement;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new ArrayList<>();
    tags.add(ifTag());
    tags.add(openParenTag());
    tags.add(expression.toXMLString());
    tags.add(closeParenTag());
    tags.add(openBraceTag());
    tags.addAll(XMLUtils.formatStatements(ifStatements));
    tags.add(closeBraceTag());
    if (!elseBlockPresent) {
      return tags;
    }
    tags.add(XMLUtils.elseTag());
    tags.add(openBraceTag());
    tags.addAll(XMLUtils.formatStatements(elseStatements));
    tags.add(closeBraceTag());
    return tags;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visit(this);
    }

  public boolean isElseBlockPresent() {
    return elseBlockPresent && elseStatements.size() > 0;
  }

  public Node getExpression() {
    return expression;
  }
  public List<Node> getIfStatements() {
    return ifStatements;
  }

  public List<Node> getElseStatements() {
    return elseStatements;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("if (");
    sb.append(expression).append(") {");
    sb.append(ifStatements);
    sb.append("}");
    if (!elseBlockPresent) {
      return sb.toString();
    }
    sb.append("else {");
    sb.append(elseStatements);
    sb.append("}");
    return sb.toString();
  }
}
