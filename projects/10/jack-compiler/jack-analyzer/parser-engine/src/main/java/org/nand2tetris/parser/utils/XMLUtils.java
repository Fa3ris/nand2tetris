package org.nand2tetris.parser.utils;

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
    return leafTag("symbol", innerText);
  }

  public static String openBraceTag() {
    return symbolTag("{");
  }

  public static String closeBraceTag() {
    return symbolTag("}");
  }

  public static String fieldTag() {
    return keywordTag("field");
  }
  public static String staticTag() {
    return keywordTag("static");
  }

  public static String functionTag() {
    return keywordTag("function");
  }

  public static String voidTag() {
    return keywordTag("void");
  }



  public static String intTag() {
    return keywordTag("int");
  }

  public static String charTag() {
    return keywordTag("char");
  }

  public static String booleanTag() {
    return keywordTag("boolean");
  }

  public static String classTag() {
    return keywordTag("class");
  }

  public static String semicolonTag() {
    return symbolTag(";");
  }

  public static String commaTag() {
    return symbolTag(",");
  }

  public static String openParenTag() {
    return symbolTag("(");
  }

  public static String closeParenTag() {
    return symbolTag(")");
  }

  public static String keywordTag(String innerText) {
    return leafTag("keyword", innerText);
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
    return leafTag("identifier", innerText);
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

  public static String varTag() {
    return keywordTag("var");
  }
}
