package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.leafTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

import java.util.Arrays;
import org.nand2tetris.tokenizer.Token;

public class ClassVarDecNode extends AbstractNode {

  private Token scope;
  private Token type;
  private Token varName;

  public void setScope(Token scope) {
    this.scope = scope;
  }

  public void setType(Token type) {
    this.type = type;
  }

  public void setVarName(Token varName) {
    this.varName = varName;
  }

  @Override
  public String toXMLString() {

    return concat(Arrays.asList(openTag("classVarDec"),
        leafTag("keyword", scope.getLexeme()),
        leafTag("keyword", type.getLexeme()),
        leafTag("identifier", varName.getLexeme()),
        leafTag("symbol", ";"),
        closeTag("classVarDec")));
  }
}
