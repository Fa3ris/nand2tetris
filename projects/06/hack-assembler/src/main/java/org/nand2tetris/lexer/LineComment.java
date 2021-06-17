package org.nand2tetris.lexer;

public class LineComment implements State {

  private static TokenType tokenType = TokenType.LINE_COMMENT;
  @Override
  public void handle(StateMachine stateMachine) {
    char inputChar = stateMachine.peek();

    boolean isLineBreak = inputChar == '\r' || inputChar == '\n';
    if (isLineBreak) {
      stateMachine.setEndToken(tokenType);
    } else {
      stateMachine.writeToToken();
//      if (stateMachine.eof()) {
//        stateMachine.setEndToken(TokenType.LINE_COMMENT);
//      }
    }

  }

  @Override
  public TokenType getTokenType() {
    return tokenType;
  }
}
