package org.nand2tetris.tokenizer;

import static org.nand2tetris.tokenizer.Keyword.BOOLEAN;
import static org.nand2tetris.tokenizer.Keyword.CHAR;
import static org.nand2tetris.tokenizer.Keyword.CLASS;
import static org.nand2tetris.tokenizer.Keyword.CONSTRUCTOR;
import static org.nand2tetris.tokenizer.Keyword.DO;
import static org.nand2tetris.tokenizer.Keyword.ELSE;
import static org.nand2tetris.tokenizer.Keyword.FALSE;
import static org.nand2tetris.tokenizer.Keyword.FIELD;
import static org.nand2tetris.tokenizer.Keyword.FUNCTION;
import static org.nand2tetris.tokenizer.Keyword.IF;
import static org.nand2tetris.tokenizer.Keyword.INT;
import static org.nand2tetris.tokenizer.Keyword.LET;
import static org.nand2tetris.tokenizer.Keyword.METHOD;
import static org.nand2tetris.tokenizer.Keyword.NULL;
import static org.nand2tetris.tokenizer.Keyword.RETURN;
import static org.nand2tetris.tokenizer.Keyword.STATIC;
import static org.nand2tetris.tokenizer.Keyword.THIS;
import static org.nand2tetris.tokenizer.Keyword.TRUE;
import static org.nand2tetris.tokenizer.Keyword.VAR;
import static org.nand2tetris.tokenizer.Keyword.VOID;
import static org.nand2tetris.tokenizer.Keyword.WHILE;
import static org.nand2tetris.tokenizer.Symbol.*;

import java.util.Objects;

public class FileTokenizer implements Tokenizer {

  private final CharReader reader;

  public FileTokenizer(CharReader reader) {
    this.reader = Objects.requireNonNull(reader);
  }

  private boolean isSymbol(char c) {
    String s = String.valueOf(c);
    switch (s) {
      case OPEN_BRACE:
      case CLOSE_BRACE:
      case OPEN_PAREN:
      case CLOSE_PAREN:
      case OPEN_BRACK:
      case CLOSE_BRACK:
      case DOT:
      case COMMA:
      case SEMICOLON:
      case PLUS:
      case MINUS:
      case STAR:
      case SLASH:
      case AND:
      case OR:
      case LT:
      case GT:
      case EQ:
      case NOT:
        return true;
      default:
        return false;
    }
  }

  private boolean isKeyword(String word) {
    switch (word) {
      case CLASS:
      case CONSTRUCTOR:
      case FUNCTION:
      case METHOD:
      case FIELD:
      case STATIC:
      case VAR:
      case INT:
      case CHAR:
      case BOOLEAN:
      case VOID:
      case TRUE:
      case FALSE:
      case NULL:
      case THIS:
      case LET:
      case DO:
      case IF:
      case ELSE:
      case WHILE:
      case RETURN:
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
