package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.letTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.nand2tetris.parser.utils.Joiner;
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

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visit(this);
    term.accept(visitor);

    checkOpTermMatch();

    if (ops.size() > 0) {
      for (int i = 0; i < ops.size(); i++) {
        additionalTerms.get(i).accept(visitor);
        visitor.visitOperator(ops.get(i).getLexeme());
      }
    }

  }

  private void checkOpTermMatch() {
    if (ops.size() != additionalTerms.size()) {
      String msg = String.format("mismatch op [%s] and term [%s]", ops.size(),
          additionalTerms.size());
      throw new IllegalStateException(msg);
    }
  }

  @Override
  public String toString() {
    List<String> descriptions = new ArrayList<>();
    descriptions.add(term.toString());

    checkOpTermMatch();
    for (int i = 0; i < ops.size(); i++) {
      descriptions.add(ops.get(i).getLexeme());
      descriptions.add(additionalTerms.get(i).toString());
    }

    return String.join(" ", descriptions);
  }
}
