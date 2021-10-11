package org.nand2tetris.tokenizer.tester;

import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;

public class TokenToTagMapper {


  public String convertToken(Token token) {

    String tagName;
    switch (token.getType()) {
      case KEYWORD:
        tagName = "keyword";
        break;
      case STRING:
        tagName = "stringConst";
        break;
      case INTEGER:
        tagName = "intConst";
        break;
      case SYMBOL:
        tagName = "symbol";
        break;
      case IDENTIFIER:
        tagName = "identifier";
        break;
      default:
        throw new RuntimeException("invalid type");
    }
//    if (token.getType() == TokenType.SYMBOL) {
//      tagName = "symbol";
//    }
    return String.format("<%1$s> %2$s </%1$s>",tagName, token.getLexeme());
  }

}
