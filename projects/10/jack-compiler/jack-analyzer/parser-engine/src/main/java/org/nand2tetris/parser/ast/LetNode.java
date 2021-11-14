package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.equalTag;
import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.letTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;

import java.util.LinkedList;
import java.util.List;
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
    tags.add(equalTag());
    tags.add(rightExpression.toXMLString());
    tags.add(semicolonTag());
    return tags;
  }
}
