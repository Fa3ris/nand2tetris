package org.nand2tetris;

public class FileReadingException extends RuntimeException {

  public FileReadingException() {
    super();
  }

  public FileReadingException(String message) {
    super(message);
  }

  public FileReadingException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileReadingException(Throwable cause) {
    super(cause);
  }
}
