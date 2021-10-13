package org.nand2tetris.tokenizer.tester;

import org.nand2tetris.tokenizer.Token;

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
    String escapedLexeme = escape(token.getLexeme());
    return String.format("<%1$s> %2$s </%1$s>",tagName, escapedLexeme);
  }

  private String escape(String lexeme) {
    switch (lexeme) {

      case "<":
        return "&lt;";
      case ">":
        return "&gt;";
      case "\"":
        return "&quot;";
      case "&":
        return "&amp;";
      default:
        return lexeme;
    }
  }

}
