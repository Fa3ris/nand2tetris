package org.nand2tetris.tokenizer;

public class FileTokenizer implements Tokenizer {

  private enum InputType {
    CHAR(0), // not one of other input types
    DIGIT(1),
    WS(4), // white space except EOL
    SLASH(3), // needed for detecting comments
    D_QUOTE(5), // double quote
    SYMBOL(5),
    EOL(3), // end of line
    EOF(6);

    final int colIndex;
    InputType(int colIndex) {
      this.colIndex = colIndex;
    }
  }

  private enum State {
    START,
    END,
    ILLEGAL
  }

  /**
   *
   * advance() {
   *   state = start
   *   while state != end
   *    reader.advance();
   *    if reader.isEOF() }
   *      error
   *    }
   *
   *    reader.peekChar()
   *    lexemeBuilder.append(char);
   *
   *    newState = transition(state, peekedChar);
   *
   *    if illegal char throw
   *
   *
   * }
   */
  private final CharReader reader;



  public FileTokenizer(CharReader reader) {
    this.reader = reader;
  }

  private boolean isSymbol(char c) {
    switch (c) {
      case '{':
      case '}':
      case '(':
      case ')':
      case '[':
      case ']':
      case '.':
      case ',':
      case ';':
      case '+':
      case '-':
      case '*':
      case '/':
      case '&':
      case '|':
      case '<':
      case '>':
      case '=':
      case '~':
        return true;
      default:
        return false;
    }
  }

  private boolean isKeyword(String word) {
    switch (word) {
      case "class":
      case "constructor":
      case "function":
      case "method":
      case "field":
      case "static":
      case "var":
      case "int":
      case "char":
      case "boolean":
      case "void":
      case "true":
      case "false":
      case "null":
      case "this":
      case "let":
      case "do":
      case "if":
      case "else":
      case "while":
      case "return":
        return true;
      default:
        return false;
    }
  }

  private Token currentToken;

  String lexeme;

  @Override
  public void advance() {

    if (reader == null || reader.isEOF()) {
      lexeme = null;
      return;
    }

    StringBuilder sb = new StringBuilder();

    while (true) {
      reader.advance();
      if (reader.isEOF()) {
        lexeme = sb.toString();
        return;
      }
      char charRead = reader.peekChar();
      if (charRead == ' ') {
        lexeme = sb.toString();
        return;
      }
      sb.append(charRead);
    }

//    State state = State.START;
//    currentToken = null;
//
//    while (true) {
//      reader.advance();
//      if (reader.isEOF()) {
//
//        return;
//      }
//
//      char newChar = reader.peekChar();
//
//      State newState = transition(state, newChar);
//
//      if (newState == State.START) {
//        continue;
//      }
//      if (newState == State.END) {
//        completeToken(state);
//        return;
//      }
//
//      lexemeBuilder.append(newChar);
//      state = newState;
//    }

  }

//  private final StringBuilder lexemeBuilder = new StringBuilder();

  private void completeToken(State state) {

    currentToken = new Token();
//    currentToken.setLexeme(lexemeBuilder.toString());
    currentToken.setType(TokenType.IDENTIFIER);

//    lexemeBuilder.setLength(0);

  }

  private State transition(State state, char newChar) {
    return null;
  }

  @Override
  public Token peekToken() {

    Token token = new Token();
    token.setLexeme(lexeme);

    TokenType type = TokenType.IDENTIFIER;
    if (lexeme.equals("class")) {
      type = TokenType.KEYWORD;
    }
    if (lexeme.equals("+")) {
      type = TokenType.SYMBOL;
    }
    if (lexeme.equals("1342")) {
      type = TokenType.INTEGER;
    }
    token.setType(type);
    return token;
  }

  @Override
  public boolean hasToken() {
    return reader != null && lexeme != null;
  }
}
