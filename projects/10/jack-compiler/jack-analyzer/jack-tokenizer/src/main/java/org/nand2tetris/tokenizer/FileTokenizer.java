package org.nand2tetris.tokenizer;

import java.util.Objects;

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
    ILLEGAL,
    ALPHA_NUM,
    SYMBOL,
    DIGIT,
    ;
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
    this.reader = Objects.requireNonNull(reader);
  }

  private boolean isSingleSymbol(char c) {
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
      case '/': // TODO special case of comment
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

  boolean currentCharUsedByPreviousToken = true;

  private final StringBuilder sb = new StringBuilder();

  private TokenType tokenType;

  private Token token;

  @Override
  public void advance() {

    // reset lexeme and type;
    lexeme = null;
    tokenType = null;
    token = null;

    if (reader.isEOF()) {
      return;
    }

    sb.setLength(0);

    while (true) {
      if (currentCharUsedByPreviousToken) {
        reader.advance();
        if (reader.isEOF()) {
          return;
        }
      } else {
        // reset
        currentCharUsedByPreviousToken = true;
      }

      // TODO check accepting state for last token

      char charRead = reader.peekChar();

      if (Character.isWhitespace(charRead)) {
        continue;
      }

      if (Character.isLetter(charRead) || charRead == '_') {
        sb.append(charRead);
        tokenizeIdentifier();
        return;
      }

      if (Character.isDigit(charRead)) {
        sb.append(charRead);
        tokenizeInteger();
        return;
      }

      if (isSingleSymbol(charRead)) {
        sb.append(charRead);
        tokenType = TokenType.SYMBOL;
        lexeme = sb.toString();
        token = Token.build(tokenType, lexeme);
        return;
      }
    }
  }

  private void tokenizeInteger() {
    while(true) {
      reader.advance();
      if (reader.isEOF()) {
        tokenType = TokenType.INTEGER;
        lexeme = sb.toString();
        token = Token.build(tokenType, lexeme);
        return;
      }

      char charRead = reader.peekChar();
      if (Character.isDigit(charRead)) {
        sb.append(charRead);
        continue;
      }

      if (charRead == '+') {
        currentCharUsedByPreviousToken = false;
      }

      tokenType = TokenType.INTEGER;
      lexeme = sb.toString();
      token = Token.build(tokenType, lexeme);
      return;
    }
  }

  private void tokenizeIdentifier() {
    while(true) {
      reader.advance();
      if (reader.isEOF()) {
        lexeme = sb.toString();
        tokenType = TokenType.IDENTIFIER;
        if (isKeyword(lexeme)) {
          tokenType = TokenType.KEYWORD;
        }
        token = Token.build(tokenType, lexeme);
        return;
      }

      char charRead = reader.peekChar();
      if (Character.isLetterOrDigit(charRead) || charRead == '_') {
        sb.append(charRead);
        continue;
      }

      if (charRead == '+') {
        currentCharUsedByPreviousToken = false;
      }

      lexeme = sb.toString();
      tokenType = TokenType.IDENTIFIER;
      if (isKeyword(lexeme)) {
        tokenType = TokenType.KEYWORD;
      }
      token = Token.build(tokenType, lexeme);
      return;
    }
  }

  @Override
  public Token peekToken() {
    return token;
  }

  @Override
  public boolean hasToken() {
    return token != null;
  }
}
