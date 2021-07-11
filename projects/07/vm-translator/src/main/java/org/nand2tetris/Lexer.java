package org.nand2tetris;

import java.io.IOException;

public class Lexer {

  private enum InputType {
    CHAR(0),
    DIGIT(1),
    SLASH(2),
    EOL(3), // end of line
    WS(4), // white space except EOL
    ID_SPECIAL_CHAR(5),
    EOF(6);

    final int colIndex;
    InputType(int colIndex) {
      this.colIndex = colIndex;
    }
  }

  private enum State {
    START(0),
    ID(1),
    INT(2),
    SLASH(3),
    COMMENT(4),
    END,
    ILLEGAL;

    final int rowIndex;
    State(int rowIndex) {
      this.rowIndex = rowIndex;
    }

    State() {
      rowIndex = -1;
    }
  }

  public Lexer(CharReader reader, SymbolTable symbolTable) throws IOException {
    this.reader = reader;
    this.symbolTable = symbolTable;
    findNextToken();
  }

  private State state = State.START;
  private Token token;
  private boolean nextTokenFound = false;
  private InputType inputType;

  private final CharReader reader;
  private final SymbolTable symbolTable;
  private final StringBuffer lexemeBuffer = new StringBuffer();

  private final State[][] transitions = {
      // CHAR          DIGIT          SLASH           EOL            WS             ID_SPECIAL_CHAR    EOF
      { State.ID,      State.INT,     State.SLASH,    State.START,   State.START,   State.ID,          State.START }, // START
      { State.ID,      State.ILLEGAL, State.ILLEGAL,  State.END,     State.END,     State.ID,          State.END },   // ID
      { State.ILLEGAL, State.INT,     State.END,      State.END,     State.END,     State.ILLEGAL,     State.END },   // INT
      { State.ILLEGAL, State.ILLEGAL, State.COMMENT,  State.ILLEGAL, State.ILLEGAL, State.ILLEGAL,     State.ILLEGAL},// SLASH
      { State.COMMENT, State.COMMENT, State.COMMENT,  State.END,     State.COMMENT, State.COMMENT,     State.END},    // COMMENT
  };

  private State transition() {
    return transitions[state.rowIndex][inputType.colIndex];
  }

  public Token next() throws IOException {
    Token nextToken = token;
    findNextToken();
    return nextToken;
  }

  public Token scan() {
    return token;
  }

  private void findNextToken() throws IOException {

    while (true) {
      if (reader.eof()) { // EOF reached
        inputType = InputType.EOF;
        State newState = transition();
        handleEOFState(newState);
        if (!nextTokenFound) {
          token = Token.EOF;
        }
        return;
      } else { // next char available
        setInputType();
        State newState = transition();
        handleState(newState);
        if (nextTokenFound) {
          nextTokenFound = false;
          return;
        }
      }
    }
  }

  private void handleEOFState(State newState) {
    switch (newState) {
      case START: // no current token being constructed
        nextTokenFound = false;
        return;
      case END: // token completed
        completeToken();
        return;
      case ILLEGAL: // uncompleted token
      default:
        throw new IllegalStateException("state " + state + " input: EOF");
    }
  }

  private void handleState(State newState) throws IOException {
    switch (newState) {
      case START: // ignore white space
        reader.read();
        break;
      case END:
        completeToken();
        break;
      case ILLEGAL:
        throw new IllegalStateException("state " + state + " input " + reader.peek());
      default:
        state = newState;
        lexemeBuffer.append(reader.read());
    }
  }

  private void completeToken() {
    String lexeme = lexemeBuffer.toString();
    switch (state) {
      case INT:
        token = new Token(TokenType.INTEGER, lexeme);
        nextTokenFound = true;
        break;
      case ID:
        TokenType tokenType = symbolTable.lookup(lexeme);
        if (tokenType == TokenType.IDENTIFIER) {
          token = new Token(tokenType, lexeme);
        } else {
          token = Token.get(tokenType);
        }
        nextTokenFound = true;
        break;
    }
    // reset
    lexemeBuffer.setLength(0);
    state = State.START;
  }

  private void setInputType() {
    char input = reader.peek();

    if (state == State.COMMENT) {
      if (input == '\r' || input == '\n') {
        inputType = InputType.EOL;
      } else {
        inputType = InputType.CHAR;
      }
    } else {

      if (Character.isLetter(input)) {
        inputType = InputType.CHAR;
      } else if (Character.isDigit(input)) {
        inputType = InputType.DIGIT;
      } else if (Character.isWhitespace(input)) {
        inputType = InputType.WS;
      } else if (input == '/') {
        inputType = InputType.SLASH;
      } else if (isValidIdSpecChar(input)) {
        inputType = InputType.ID_SPECIAL_CHAR;
      } else {
        throw new IllegalArgumentException(String.valueOf(input));
      }
    }
  }

  private boolean isValidIdSpecChar(char input) {
    return input == '_' || input == '-';
  }

}
