package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.leafTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.nand2tetris.tokenizer.Token;

public class ClassVarDecNode extends AbstractNode {

  private Token scope;
  private Token type;
  private Token varName;

  private List<Token> varNames = new ArrayList<>();

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
    List<String> tags = new ArrayList<>();
    tags.add(openTag("classVarDec"));
    tags.add(leafTag("keyword", scope.getLexeme()));
    tags.add(leafTag("keyword", type.getLexeme()));
    Iterator<Token> it = varNames.iterator();
    tags.add(leafTag("identifier", it.next().getLexeme()));

    while (it.hasNext()) {
      tags.add(leafTag("symbol", ","));
      tags.add(leafTag("identifier", it.next().getLexeme()));
    }

    tags.add(leafTag("symbol", ";"));
    tags.add(closeTag("classVarDec"));

    return concat(tags);
  }

  public void addVarName(Token token) {
    varNames.add(token);
  }
}
