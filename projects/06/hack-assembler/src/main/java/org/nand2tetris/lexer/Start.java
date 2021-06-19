package org.nand2tetris.lexer;

public class Start implements State {

  @Override
  public void handle(StateMachine stateMachine) {
    char inputChar = stateMachine.peek();

    /* single-char token */
    TokenType tokenType = null;
    switch (inputChar) {
      case '@':
        tokenType = TokenType.AT;
        break;
      case '(':
        tokenType = TokenType.OPEN_PAREN;
        break;
      case ')':
        tokenType = TokenType.CLOSE_PAREN;
        break;
      case '+':
        tokenType = TokenType.PLUS;
        break;
      case '-':
        tokenType = TokenType.MINUS;
        break;
      case '|':
        tokenType = TokenType.BIT_OR;
        break;
      case '&':
        tokenType = TokenType.BIT_AND;
        break;
      case '!':
        tokenType = TokenType.NOT;
        break;
      case '=':
        tokenType = TokenType.ASSIGN;
        break;
      case ';':
        tokenType = TokenType.SEMI_COLON;
        break;
    }

    if (tokenType != null) {
      stateMachine.writeSingleCharToken(tokenType);
      return;
    }

    /* new state */

    State newState = null;

    if (Identifier.isValidFirstChar(inputChar)) {
      newState = StateHelper.IDENTIFIER;
    }

    if (Integer.isValidChar(inputChar)) {
      newState = StateHelper.INTEGER;
    }

    if (inputChar == '/') { // begin line comment
      newState = StateHelper.SLASH;
    }

    if (newState != null) {
      stateMachine.setState(newState);
      stateMachine.writeToToken();
      return;
    }

    /* stay in Start */
    if (Character.isWhitespace(inputChar)) {
      stateMachine.read(); // remove from buffer
      return;
    }

    /* unknown char */
    throw new UnexpectedCharException(inputChar);
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.WHITE_SPACE;
  }
}
