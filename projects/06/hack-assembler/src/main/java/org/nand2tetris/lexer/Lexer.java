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
    boolean isIdentifier = TokenType.IDENTIFIER == type;
    if (isIdentifier) {
      TokenType keywordType = KeywordTable.lookup(tokenValue);
      if (keywordType != null) {
        type = keywordType;
      }
    }

    boolean notWhiteSpace = TokenType.WHITE_SPACE != type;
    boolean notLineComment = TokenType.LINE_COMMENT != type;
    if (notWhiteSpace && notLineComment) {
      Token token = new Token(type, tokenValue);
      tokens.offer(token);
    }

    /* reset stringBuffer */
    stringBuffer.setLength(0);
    state = StateHelper.START;

    // break from while loop
    endState = true;
  }

  @Override
  public char peek() {
    return buffer.peek();
  }


  @Override
  public void writeToToken() {
    char cRead = buffer.read();
    stringBuffer.append(cRead);

    if (EOF = buffer.isEmpty() && stringBuffer.length() > 0) {
      setEndToken(state.getTokenType());
    }
  }

  @Override
  public void writeSingleCharToken(TokenType type) {
    char cRead = buffer.read();
    stringBuffer.append(cRead);

    setEndToken(type);

  }

  @Override
  public Token peekToken() {
    return tokens.peek();
  }

  @Override
  public Token nextToken() {
    readToken();
    Token token = tokens.poll();
    return token;
  }

  private void readToken() {
    while (!EOF && !endState) {
      state.handle(this);
    }
    checkEOF();
    endState = false;
  }

  private void checkEOF() {
    if (EOF) {
      tokens.offer(new Token(TokenType.EOF, "eof"));
    }
  }
}
