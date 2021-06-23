package org.nand2tetris.lexer;

/**
 * accepts letter as first symbol
 * then letter|digit|'_'
 */
public class Identifier implements State {

  @Override
  public void handle(StateMachine stateMachine) {
    char inputChar = stateMachine.peek();

    if (isValidInsideChar(inputChar)) {
      stateMachine.writeToToken();
    } else {
      stateMachine.setEndToken(TokenType.IDENTIFIER);
    }
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.IDENTIFIER;
  }

  public boolean isValidInsideChar(char input) {
    return Character.isLetterOrDigit(input) || input == '_' || input == '.' || input == '$';
  }

  public static boolean isValidFirstChar(char input) {
    return Character.isLetter(input) || input == '_';
  }
}
