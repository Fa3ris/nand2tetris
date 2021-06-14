package org.nand2tetris;

public class FileCloseException extends RuntimeException {

  public FileCloseException() {
    super();
  }

  public FileCloseException(String message) {
    super(message);
  }

  public FileCloseException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileCloseException(Throwable cause) {
    super(cause);
  }
}
