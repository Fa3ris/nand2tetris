package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.parser.utils.XMLUtils.varTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.nand2tetris.tokenizer.Token;

public class VarDecNode extends AbstractNode {

  private Token type;

  private List<Token> varNames = new ArrayList<>();

  @Override
  protected String parentTag() {
    return "varDec";
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new ArrayList<>();
    tags.add(varTag());
    tags.add(formatTag(type));
    tags.add(formatTag(varNames.get(0)));
    tags.add(semicolonTag());
    return tags;
  }

  public void setType(Token token) {
    type = token;

  }

  public void addVarName(Token token) {
    varNames.add(token);
  }
}
