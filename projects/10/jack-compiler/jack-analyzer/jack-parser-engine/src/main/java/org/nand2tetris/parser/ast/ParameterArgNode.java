package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.formatTag;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.tokenizer.Token;

public class ParameterArgNode extends AbstractNode {

  private Token type;
  private Token name;

  @Override
  protected List<String> childrenTags() {
    return Arrays.asList(
        formatTag(type),
        formatTag(name));
  }

  public void setType(Token type) {
    this.type = type;
  }

  public void setName(Token name) {
    this.name = name;
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return String.format("%s %s", type.getLexeme(), name.getLexeme());
  }

  public String getType() {
    return type.getLexeme();
  }

  public String getName() {
    return name.getLexeme();
  }
}
