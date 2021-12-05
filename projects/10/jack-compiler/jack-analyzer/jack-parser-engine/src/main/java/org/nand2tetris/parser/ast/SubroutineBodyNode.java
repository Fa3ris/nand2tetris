package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.nand2tetris.parser.utils.TagNames;

public class SubroutineBodyNode extends AbstractNode {

  private final List<Node> varDecs = new LinkedList<>();
  private final List<Node> statements = new LinkedList<>();

  @Override
  protected String parentTag() {
    return TagNames.subroutineBody;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new LinkedList<>();
    tags.add(openBraceTag());
    tags.addAll(varDecs.stream().map(Node::toXMLString).collect(Collectors.toList()));
    List<String> statementsTags = statements.stream().map(Node::toXMLString).collect(Collectors.toList());
    statementsTags = encloseInTag(TagNames.statements, statementsTags);
    tags.addAll(statementsTags);
    tags.add(closeBraceTag());
    return tags;
  }

  public void addVarDec(Node varDec) {
    varDecs.add(varDec);
  }

  public void addStatement(Node statement) {
    statements.add(statement);
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visit(this);
    for (Node varDec : varDecs) {
      varDec.accept(visitor);
    }
    for (Node statement : statements) {
      statement.accept(visitor);
    }
  }

}
