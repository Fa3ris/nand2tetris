package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.keywordTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;
import static org.nand2tetris.parser.utils.XMLUtils.symbolTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;

public class ClassVarDecNode extends AbstractNode {

  private static final String tagName = "classVarDec";
  private Token scope;
  private Token type;
  private List<Token> varNames = new ArrayList<>();

  public void setScope(Token scope) {
    this.scope = scope;
  }

  public void setType(Token type) {
    this.type = type;
  }

  @Override
  public String toXMLString() {
    List<String> tags = new ArrayList<>();
    tags.add(openTag(tagName));
    tags.add(keywordTag(scope.getLexeme()));
    String typeTag;
    if (type.getType() == TokenType.IDENTIFIER) {
      typeTag = identifierTag(type.getLexeme());
    } else {
      typeTag = keywordTag(type.getLexeme());
    }
    tags.add(typeTag);
    Iterator<Token> it = varNames.iterator();
    tags.add(identifierTag(it.next().getLexeme()));

    while (it.hasNext()) {
      tags.add(symbolTag( ","));
      tags.add(identifierTag(it.next().getLexeme()));
    }

    tags.add(symbolTag(";"));
    tags.add(closeTag(tagName));

    return concat(tags);
  }

  public void addVarName(Token token) {
    varNames.add(token);
  }
}
