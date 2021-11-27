package org.nand2tetris.tokenizer;

public class CounterCharReader implements CharReader {

  private final CharReader reader;

  public CounterCharReader(CharReader reader) {
    this.reader = reader;
  }

  private int lineNumber;
  private int columnNumber;
  @Override
  public void advance() {
      reader.advance();
      if (reader.isEOF()) {
        return;
      }
      char charRead = reader.peekChar();
      if (charRead == '\n') {
        lineNumber++;
        columnNumber = 0;
        return;
      }
      if (charRead == '\r') {
        return;
      }
      columnNumber++;
  }

  @Override
  public char peekChar() {
    return reader.peekChar();
  }

  @Override
  public boolean isEOF() {
    return reader.isEOF();
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getColumnNumber() {
    return columnNumber;
  }
}
