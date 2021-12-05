package org.nand2tetris.tokenizer.error;

import org.nand2tetris.tokenizer.CharReader;
import org.nand2tetris.tokenizer.CounterCharReader;

public abstract class CharReaderErrorFormatter {

  public static String format(CharReader charReader) {
    return charReader.toString();
  }

  public static String format(CounterCharReader charReader) {
    return String.format("parse error at line %s column %s\n%s",
        charReader.getLineNumber() + 1,
        charReader.getColumnNumber() + 1,
        charReader.getLineContent());
  }

}
