package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeBracketTag;
import static org.nand2tetris.parser.utils.XMLUtils.equalTag;
import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.letTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBracketTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class LetNode extends AbstractNode {

  private Token varName;
  private Node rightExpression;
  private Node leftExpression;

  public void setVarName(Token varName) {
    this.varName = varName;
  }

  public void setRightExpression(Node rightExpression) {
    this.rightExpression = rightExpression;
  }

  public void setLeftExpression(Node leftExpression) {
    this.leftExpression = leftExpression;
  }

  @Override
  protected String parentTag() {
    return TagNames.letStatement;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new LinkedList<>();
    tags.add(letTag());
    tags.add(formatTag(varName));
    if (leftExpression != null) {
      tags.add(openBracketTag());
      tags.add(leftExpression.toXMLString());
      tags.add(closeBracketTag());
    }
    tags.add(equalTag());
    tags.add(rightExpression.toXMLString());
    tags.add(semicolonTag());
    return tags;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visit(this);
  }

  public Node getRightExpression() {
    return rightExpression;
  }

  public Token getVarName() {
    return varName;
  }

  public Optional<Node> getLeftExpression() {
    return Optional.ofNullable(leftExpression);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("let %s", varName.getLexeme()));
    if (leftExpression != null) {
      sb.append(String.format("[%s]", leftExpression));
    }
    sb.append(String.format(" = %s", rightExpression));
    return sb.toString();
  }
}
