package org.nand2tetris.lexer;

import java.util.LinkedList;
import java.util.Queue;
import org.nand2tetris.CharBuffer;

public class Lexer implements TokenStream, StateMachine {

  private final StringBuffer stringBuffer = new StringBuffer();

  public Lexer(CharBuffer charBuffer) {
    buffer = charBuffer;
    if (buffer.isEmpty()) {
      EOF = true;
    }
  }

  private boolean EOF;

  private final CharBuffer buffer;

  private final Queue<Token> tokens = new LinkedList<>();

  private State state = StateHelper.START;

  public void setState(State newState) {
    state = newState;
  }

  private boolean endState;

  public void setEndToken(TokenType type) {

    String tokenValue = stringBuffer.toString();
    type = updateType(type, tokenValue);

    boolean notWhiteSpace = TokenType.WHITE_SPACE != type;
    boolean notLineComment = TokenType.LINE_COMMENT != type;
    boolean validTokenToReturn = notWhiteSpace && notLineComment;
    if (validTokenToReturn) {
      Token token = new Token(type, tokenValue);
      tokens.offer(token);
      // break from while loop
      endState = true;
    }

    /* reset stringBuffer */
    stringBuffer.setLength(0);
    /* reset state */
    state = StateHelper.START;
  }

  private TokenType updateType(TokenType type, final String tokenValue) {
    if (TokenType.IDENTIFIER == type) {
      TokenType keywordType = KeywordTable.lookup(tokenValue);
      if (keywordType != null) {
        type = keywordType;
      }
    }
    return type;
  }

  @Override
  public char read() {
    char cRead = buffer.read();
    checkEOF();
    return cRead;
  }

  @Override
  public char peek() {
    return buffer.peek();
  }

  @Override
  public void writeToToken() {
    char cRead = buffer.read();
    stringBuffer.append(cRead);
    checkEOF();
  }

  private void checkEOF() {
    EOF = buffer.isEmpty();
    if (shouldCloseToken()) {
      closeToken();
    }
  }

  private boolean shouldCloseToken() {
    return EOF && stringBuffer.length() > 0;
  }

  private void closeToken() {
    setEndToken(state.getTokenType());
  }

  @Override
  public void writeSingleCharToken(TokenType type) {
    char cRead = buffer.read();
    stringBuffer.append(cRead);
    setEndToken(type);
    checkEOF();
  }

  @Override
  public Token peekToken() {
    return tokens.peek();
  }

  @Override
  public Token nextToken() {
    if (EOF) {
      return EOFToken();
    }
    readToken();
    Token token = tokens.poll();
    return token;
  }

  private void readToken() {
    endState = false;
    while (!EOF && !endState) {
      state.handle(this);
    }
    if (EOF) {
      tokens.offer(EOFToken());
    }
  }

  private Token EOFToken() {
    return new Token(TokenType.EOF, "EOF");
  }
}
