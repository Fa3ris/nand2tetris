package org.nand2tetris.parser.ast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class ExpressionNode extends AbstractNode{

  private Node term;

  private final List<Token> ops = new LinkedList<>();
  private final List<Node> additionalTerms = new LinkedList<>();
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

  public void addOp(Token token) {
    ops.add(token);
  }

  public void addAdditionalTerm(Node term) {
    additionalTerms.add(term);
  }
}
