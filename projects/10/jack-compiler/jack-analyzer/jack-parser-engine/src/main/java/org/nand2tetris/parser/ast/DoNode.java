package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class DoNode extends AbstractNode {

  private Token identifier;
  private Token subroutineName;
  private Node expressionListNode;
  public void addIdentifier(Token token) {
    identifier = token;
  }

  @Override
  protected String parentTag() {
    return TagNames.doStatement;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new ArrayList<>();
    tags.add(doTag());
    if (identifier != null) {
      tags.add(formatTag(identifier));
      tags.add(dotTag());
    }
    tags.add(formatTag(subroutineName));
    tags.add(openParenTag());
    tags.add(expressionListNode.toXMLString());
    tags.add(closeParenTag());
    tags.add(semicolonTag());
    return tags;
  }

  public void addSubroutineName(Token token) {
    subroutineName = token;
  }

  public void addExpressionList(Node expressionListNode) {
    this.expressionListNode = expressionListNode;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    if (identifier != null) {
      visitor.visitMethodCall(identifier, subroutineName, (ExpressionListNode) expressionListNode);
    } else {
      visitor.visitFunctionCall(subroutineName, (ExpressionListNode) expressionListNode);
    }
    visitor.visit(this);
  }

}
