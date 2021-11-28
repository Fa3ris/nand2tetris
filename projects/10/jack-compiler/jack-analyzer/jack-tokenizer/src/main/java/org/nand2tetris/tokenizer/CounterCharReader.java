package org.nand2tetris.tokenizer;

public class CounterCharReader implements CharReader {

  private final CharReader reader;

  public CounterCharReader(CharReader reader) {
    this.reader = reader;
  }

  private final StringBuilder sb = new StringBuilder();

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
        sb.setLength(0); // reset
        return;
      }
      if (charRead == '\r') {
        return;
      }
      sb.append(charRead);
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

  public String getLineContent() {
    fillBufferUntilLFOrEOF();
    return sb.toString();
  }

  private void fillBufferUntilLFOrEOF() {
    if (reader.isEOF()) {
      return;
    }
    while (true) {
      reader.advance();
      if (reader.isEOF()) {
        return;
      }
      char charRead = reader.peekChar();
      if (charRead == '\n' || charRead == '\r') {
        return;
      }
      sb.append(charRead);
    }
  }
}
