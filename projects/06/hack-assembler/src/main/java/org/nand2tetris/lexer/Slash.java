package org.nand2tetris.lexer;

public class Slash implements State {

  @Override
  public void handle(StateMachine stateMachine) {
    char inputChar = stateMachine.peek();

    if (inputChar == '/') {
      stateMachine.writeToToken();
      stateMachine.setState(StateHelper.LINE_COMMENT);
    } else {
      throw new UnexpectedCharException('/', inputChar);
    }
  }

  @Override
  public TokenType getTokenType() {
    throw new NonFinalStateException(this);
  }
}
