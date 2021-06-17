package org.nand2tetris.lexer;

/**
 * accepts only digits
 */
public class Integer implements State {

  @Override
  public void handle(StateMachine stateMachine) {
    char inputChar = stateMachine.peek();

    if (Character.isDigit(inputChar)) {
      stateMachine.writeToToken();
    } else {
      stateMachine.setEndToken(TokenType.INTEGER);
    }
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.INTEGER;
  }

  public static boolean isValidChar(char input) {
    return Character.isDigit(input);
  }
}
