package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.formatTag;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class TermNode extends AbstractNode {

  private Token varName;
  public void addVarName(Token token) {
    varName = token;
  }

  @Override
  protected String parentTag() {
    return TagNames.termTag;
  }

  @Override
  protected List<String> childrenTags() {
    return Arrays.asList(
        formatTag(varName)
    );
  }
}