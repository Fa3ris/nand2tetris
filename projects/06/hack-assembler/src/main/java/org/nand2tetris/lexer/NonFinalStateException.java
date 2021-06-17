package org.nand2tetris.lexer;

public class NonFinalStateException extends RuntimeException {

  private final State state;
  private String message;

  public NonFinalStateException(State state) {
    super();
    this.state = state;
    message = state.getClass() + " is not a final state";
  }

  @Override
  public String getMessage() {
    return message;
  }
}
