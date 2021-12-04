package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.letTag;

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
    List<String> tags = new LinkedList<>();
    tags.add(term.toXMLString());

    if (ops.size() != additionalTerms.size()) {
      String msg = String.format("mismatch op [%s] and term [%s]", ops.size(),
          additionalTerms.size());
      throw new IllegalStateException(msg);
    }

    if (ops.size() > 0) {
      for (int i = 0; i < ops.size(); i++) {
        tags.add(formatTag(ops.get(i)));
        tags.add(additionalTerms.get(i).toXMLString());
      }
    }
    return tags;
  }

  public void addAdditionalTerm(Token op, Node term) {
    ops.add(op);
    additionalTerms.add(term);
  }
}
