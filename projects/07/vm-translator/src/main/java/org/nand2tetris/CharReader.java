package org.nand2tetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class CharReader {

  private final BufferedReader reader;
  private char currentChar;
  private boolean eof;

  public CharReader(Reader reader) throws IOException {
    if (reader instanceof BufferedReader) {
      this.reader = (BufferedReader) reader;
    } else {
      this.reader = new BufferedReader(reader);
    }
    advance();
  }

  public boolean eof() {
    return eof;
  }

  public char peek() {
    return currentChar;
  }

  public char read() throws IOException {
    char nextChar = currentChar;
    advance();
    return nextChar;
  }

  private void advance() throws IOException {
    int charRead = reader.read();
    if (charRead < 0) {
      eof = true;
    } else {
      currentChar = (char) charRead;
    }
  }
}
