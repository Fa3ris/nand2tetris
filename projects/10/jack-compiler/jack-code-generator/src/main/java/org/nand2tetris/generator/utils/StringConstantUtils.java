package org.nand2tetris.generator.utils;

public class StringConstantUtils {

  public static char[] toCharsArray(String s) {
    char[] res = new char[s.length()];
    s.getChars(0, s.length(), res, 0);
    return res;
  }
}
