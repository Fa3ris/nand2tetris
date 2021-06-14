package org.nand2tetris;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.Getter;

public class Parser {

  private Path srcPath;

  private BufferedReader reader;

  @Getter
  private String currentLine;

  public void parse(Path srcPath) {
    this.clean();
    this.srcPath = srcPath;
    createReader();
    readNextCommand();
  }

  private void createReader() {
    try {
      reader = Files.newBufferedReader(srcPath);
    } catch (Exception e) {
      throw new FileReadingException();
    }
  }

  public boolean hasMoreCommand() {
    return currentLine != null;
  }

  private void readNextCommand() {
    while ((currentLine = readLine()) != null) {
      currentLine = currentLine.trim();
      if (isCommandLine(currentLine)) {
        break;
      }
    }
  }

  private String readLine() {
    try {
      return reader.readLine();
    } catch (Exception e) {
      throw new FileReadingException();
    }
  }

  private boolean isCommandLine(String line) {
    if (line.isEmpty()) {
      return false;
    }
    if (line.length() < 2) {
      return false;
    }
    boolean isLineComment = line.substring(0, 2).equalsIgnoreCase("//");
    return !isLineComment;
  }

  public List<String> getLines() {
    try {
      return Files.readAllLines(srcPath);
    } catch (Exception e) {
      throw new FileReadingException();
    }
  }

  public void clean() {
    if (reader != null) {
      try {
        reader.close();
      } catch (Exception e) {
        throw new FileCloseException();
      }
    }
  }
}
