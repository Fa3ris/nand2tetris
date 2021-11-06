package org.nand2tetris.parser.utils;

import java.util.List;

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

  public static String concat(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      sb.append(s);
    }
    return sb.toString();
  }
}
