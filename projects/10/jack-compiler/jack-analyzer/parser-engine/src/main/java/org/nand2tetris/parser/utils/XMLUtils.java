package org.nand2tetris.parser.utils;

import static org.nand2tetris.parser.utils.TagNames.classTag;
import static org.nand2tetris.parser.utils.TagNames.symbol;
import static org.nand2tetris.tokenizer.Keyword.BOOLEAN;
import static org.nand2tetris.tokenizer.Keyword.CHAR;
import static org.nand2tetris.tokenizer.Keyword.CONSTRUCTOR;
import static org.nand2tetris.tokenizer.Keyword.FIELD;
import static org.nand2tetris.tokenizer.Keyword.FUNCTION;
import static org.nand2tetris.tokenizer.Keyword.INT;
import static org.nand2tetris.tokenizer.Keyword.STATIC;
import static org.nand2tetris.tokenizer.Keyword.VOID;
import static org.nand2tetris.tokenizer.Symbol.CLOSE_BRACE;
import static org.nand2tetris.tokenizer.Symbol.CLOSE_PAREN;
import static org.nand2tetris.tokenizer.Symbol.COMMA;
import static org.nand2tetris.tokenizer.Symbol.OPEN_BRACE;
import static org.nand2tetris.tokenizer.Symbol.OPEN_PAREN;
import static org.nand2tetris.tokenizer.Symbol.SEMICOLON;

import java.util.ArrayList;
import java.util.List;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;

public abstract class XMLUtils {

  public static String openTag(String tagName) {
    return String.format("<%s>", tagName);
  }

  public static String closeTag(String tagName) {
    return String.format("</%s>", tagName);
  }

  public static String leafTag(String tagName, String innerText) {
    return String.format("<%1$s> %2$s </%1$s>", tagName, innerText);
  }

  public static String symbolTag(String innerText) {
    return leafTag(symbol, innerText);
  }

  public static String openBraceTag() {
    return symbolTag(OPEN_BRACE);
  }

  public static String closeBraceTag() {
    return symbolTag(CLOSE_BRACE);
  }

  public static String fieldTag() {
    return keywordTag(FIELD);
  }
  public static String staticTag() {
    return keywordTag(STATIC);
  }

  public static String functionTag() {
    return keywordTag(FUNCTION);
  }
  public static String constructorTag() {
    return keywordTag(CONSTRUCTOR);
  }

  public static String voidTag() {
    return keywordTag(VOID);
  }



  public static String intTag() {
    return keywordTag(INT);
  }

  public static String charTag() {
    return keywordTag(CHAR);
  }

  public static String booleanTag() {
    return keywordTag(BOOLEAN);
  }

  public static String classTag() {
    return keywordTag(classTag);
  }

  public static String semicolonTag() {
    return symbolTag(SEMICOLON);
  }

  public static String commaTag() {
    return symbolTag(COMMA);
  }

  public static String openParenTag() {
    return symbolTag(OPEN_PAREN);
  }

  public static String closeParenTag() {
    return symbolTag(CLOSE_PAREN);
  }

  public static String varTag() {
    return keywordTag(TagNames.varTag);
  }

  public static String keywordTag(String innerText) {
    return leafTag(TagNames.keyword, innerText);
  }

  public static String formatTag(Token token) {
    if (token.getType() == TokenType.KEYWORD) {
      return keywordTag(token.getLexeme());
    }
    if (token.getType() == TokenType.IDENTIFIER) {
      return identifierTag(token.getLexeme());
    }
    return leafTag("", token.getLexeme());
  }

  public static String identifierTag(String innerText) {
    return leafTag(TagNames.identifier, innerText);
  }

  public static List<String> encloseInTag(String parentTag, List<String> tags) {
    List<String> enclosed = new ArrayList<>();
    enclosed.add(openTag(parentTag));
    enclosed.addAll(tags);
    enclosed.add(closeTag(parentTag));
    return enclosed;
  }


  public static String concat(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      sb.append(s);
    }
    return sb.toString();
  }

  public static List<String> joinTags(List<String> identifierTags, String delimiterTag) {
    List<String> joined = new ArrayList<>();
    for (String identifierTag : identifierTags) {
      if (!joined.isEmpty()) {
        joined.add(delimiterTag);
      }
      joined.add(identifierTag);
    }
    return joined;
  }
}
