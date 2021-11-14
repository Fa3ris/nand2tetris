package org.nand2tetris.parser.ast;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;

public class ExpressionNode extends AbstractNode{

  private Node term;

  public void addTerm(Node term) {
    this.term = term;
  }

  @Override
  protected String parentTag() {
    return TagNames.expressionTag;
  }

  @Override
  protected List<String> childrenTags() {
    return Arrays.asList(
        term.toXMLString()
    );
  }
}
