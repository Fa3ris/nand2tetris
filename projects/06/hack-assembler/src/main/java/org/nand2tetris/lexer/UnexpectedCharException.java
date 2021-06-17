package org.nand2tetris.lexer;

public class UnexpectedCharException extends RuntimeException {

  private final char expected;
  private final char actual;

  public UnexpectedCharException(char expected, char actual) {
    super();
    this.expected = expected;
    this.actual = actual;
  }

  public UnexpectedCharException(char actual) {
    this('\0', actual);
  }

  @Override
  public String getMessage() {
    if (expected == '\0') {
      return String.format("unexpected char %c", actual);
    } else {
      return String.format("expected: %c, actual: %c", expected, actual);
    }

  }
}
