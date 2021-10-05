package org.nand2tetris.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCharReader implements CharReader {

  private BufferedReader reader;
  private char currentChar;
  private boolean eof;
  private final Path path;

  public FileCharReader(Path path) {

    this.path = path;

    if (this.path == null) {
      eof = true;
      return;
    }

    long fileSize;
    try {
      fileSize = Files.size(path);
    } catch (IOException e) {
      fileSize = 0;
    }

    if (fileSize <= 0) {
      eof = true;
      return;
    }
    try {
      reader = Files.newBufferedReader(path);
    } catch (IOException e) {
      eof = true;
    }
  }

  public void advance() {
    int charRead;
    try {
      charRead = reader.read();
    } catch (IOException e) {
      throw new RuntimeException("error reading next char from file " + path.toAbsolutePath(), e);
    }
    if (charRead < 0) {
      eof = true;
      try {
        reader.close();
      } catch (IOException e) {
        throw new RuntimeException("error closing file " + path.toAbsolutePath(), e);
      }
      return;
    }
    currentChar = (char) charRead;
  }


  public char peekChar() {
    return currentChar;
  }

  public boolean isEOF() {
    return eof;
  }
}
