package org.nand2tetris.tokenizer;

import java.util.Objects;

public class FileTokenizer implements Tokenizer {

  private final CharReader reader;

  public FileTokenizer(CharReader reader) {
    this.reader = Objects.requireNonNull(reader);
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


  boolean currentCharUsedByPreviousToken = true;

  private final StringBuilder sb = new StringBuilder();

  private Token token;

  private void resetToken() {
    token = null;
    sb.setLength(0);
  }

  @Override
  public void advance() {

    resetToken();

    if (reader.isEOF()) {
      return;
    }

    while (true) {
      if (currentCharUsedByPreviousToken) {
        reader.advance();
        if (reader.isEOF()) {
          return;
        }
      } else {
        currentCharUsedByPreviousToken = true;
      }

      // TODO check accepting state for last token
      char charRead = reader.peekChar();

      if (Character.isWhitespace(charRead)) {
        continue;
      }

      if (charRead == '"') {
        tokenizeStringLiteral();
        return;
      }

      boolean isIdentifierStartChar = Character.isLetter(charRead) || charRead == '_';
      if (isIdentifierStartChar) {
        sb.append(charRead);
        tokenizeIdentifier();
        return;
      }

      if (Character.isDigit(charRead)) {
        sb.append(charRead);
        tokenizeInteger();
        return;
      }

      if (charRead == '/') {
        reader.advance();
        if (reader.isEOF()) {
          buildDivide();
          return;
        }
        charRead = reader.peekChar();

        if (isLineBreak(charRead)) { // TODO test slash then line break
          buildDivide();
          return;
        }

        if (charRead == '/') {
          consumeLineComment();
          if (reader.isEOF()) {
            return;
          }
          continue;
        }

        if (charRead == '*') {
          consumeBlockComment();
          if (reader.isEOF()) {
            return;
          }
          continue;
        }

        buildDivide();
        return;
      }

      if (isSymbol(charRead)) {
        sb.append(charRead);
        token = Token.build(TokenType.SYMBOL, sb.toString());
        return;
      }
    }
  }

  private void buildDivide() {
    token = Token.build(TokenType.SYMBOL, "/");
  }

  private void consumeBlockComment() {
    while(true) {
      reader.advance();
      if (reader.isEOF()) {
        throw new RuntimeException("block comment not closed but EOF");
      }
      char charRead = reader.peekChar();
      if (charRead == '*') {
        reader.advance();
        if (reader.isEOF()) {
          throw new RuntimeException("block comment not closed but EOF");
        }
        charRead = reader.peekChar();
        if (charRead == '/') {
          return;
        }
      }
    }
  }

  private void consumeLineComment() {
    char charRead;
    while(true) {
      reader.advance();
      if (reader.isEOF()) {
        return;
      }
      charRead = reader.peekChar();
      if (isLineBreak(charRead)) {
        return;
      }
    }
  }

  private boolean isLineBreak(char charRead) {
    return charRead == '\n' || charRead == '\r';
  }

  private void tokenizeStringLiteral() {
    while(true) {
      reader.advance();
      if (reader.isEOF()) {
        throw new RuntimeException("string literal not completed but EOF " + sb);
      }

      char charRead = reader.peekChar();
      if (isLineBreak(charRead)) {
        throw new RuntimeException("string literal not completed but line break");
      }

      if (charRead == '\\') {
        reader.advance();
        if (reader.isEOF()) {
          throw new RuntimeException("escape char not completed" + sb);
        }

        charRead = reader.peekChar();
        if (charRead == '"') {
          sb.append(charRead);
          continue;
        }
      }

      if (charRead == '"') {
        token = Token.build(TokenType.STRING, sb.toString());
        return;
      }
      sb.append(charRead);
    }
  }

  private void tokenizeInteger() {
    while(true) {
      reader.advance();
      if (reader.isEOF()) {
        token = Token.build(TokenType.INTEGER, sb.toString());
        return;
      }

      char charRead = reader.peekChar();
      if (Character.isDigit(charRead)) {
        sb.append(charRead);
        continue;
      }

      if (!Character.isWhitespace(charRead)) {
        currentCharUsedByPreviousToken = false;
      }

      token = Token.build(TokenType.INTEGER, sb.toString());
      return;
    }
  }

  private void tokenizeIdentifier() {
    while(true) {
      reader.advance();
      if (reader.isEOF()) {
        buildIdentifierOrKeyword();
        return;
      }

      char charRead = reader.peekChar();
      boolean isIdentifierInsideChar = Character.isLetterOrDigit(charRead) || charRead == '_';
      if (isIdentifierInsideChar) {
        sb.append(charRead);
        continue;
      }

      if (!Character.isWhitespace(charRead)) {
        currentCharUsedByPreviousToken = false;
      }

      buildIdentifierOrKeyword();
      return;
    }
  }

  private void buildIdentifierOrKeyword() {
    String lexeme = sb.toString();
    TokenType tokenType = TokenType.IDENTIFIER;
    if (isKeyword(lexeme)) {
      tokenType = TokenType.KEYWORD;
    }
    token = Token.build(tokenType, lexeme);
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
