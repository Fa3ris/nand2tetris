package org.nand2tetris.tokenizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.tokenizer.stubs.CharReaderStub;

public class FileTokenizerTest {

  @Test
  public void hasNoTokenIfNoContent() throws Exception {
    CharReader[] readers = new CharReader[]{
        null,
        new CharReaderStub("")
    };
    for (CharReader reader : readers) {
      Tokenizer tokenizer = new FileTokenizer(reader);
      tokenizer.advance();
      assertFalse(tokenizer.hasToken());
    }
  }

  @Test
  public void hasTokenIfContent() throws Exception {

    String[] charStreams = {"Hello", "{", "+"};
    for (String charStream : charStreams) {
      CharReader reader = new CharReaderStub(charStream);
      Tokenizer tokenizer = new FileTokenizer(reader);
      tokenizer.advance();
      assertTrue(tokenizer.hasToken());
    }
  }

  @Test
  public void returnsIdentifierToken() throws Exception {
    String[] lexemes = new String[] {"Hello", "Bye"};
    for (String lexeme : lexemes) {
      Tokenizer tokenizer = buildTokenizer(lexeme);
      List<Token> expectedTokens = Collections.singletonList(
          Token.build(TokenType.IDENTIFIER, lexeme)
      );
      TokenMatcher.matchAll(expectedTokens, tokenizer);
    }
  }

  @Test
  public void returnsKeyword_class() throws Exception {
    String lexeme = "class";
    Tokenizer tokenizer = buildTokenizer(lexeme);
    List<Token> expectedTokens = Collections.singletonList(
        Token.build(TokenType.KEYWORD, lexeme)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }

  @Test
  public void returnsKeywordAndIdentifier() throws Exception {
    String classLexeme = "class";
    String identifier = "MyClass";
    String stream =  String.join(" ", Arrays.asList(classLexeme, identifier));
    Tokenizer tokenizer = buildTokenizer(stream);
    List<Token> expectedTokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, classLexeme),
        Token.build(TokenType.IDENTIFIER, identifier)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }


  @Test
  public void returnsSymbol_plus() throws Exception {
    String symbolLexeme = "+";
    Tokenizer tokenizer = buildTokenizer(symbolLexeme);
    List<Token> expectedTokens = Collections.singletonList(
        Token.build(TokenType.SYMBOL, symbolLexeme)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }


  @Test
  public void returnsInteger() throws Exception {
    String integerLexeme = "1342";
    Tokenizer tokenizer = buildTokenizer(integerLexeme);
    List<Token> expectedTokens = Collections.singletonList(
        Token.build(TokenType.INTEGER, integerLexeme)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }

  @Test
  public void returnsTokensNotSeparatedBySpace() throws Exception {
    String a = "a";
    String plus = "+";
    String b = "b";
    String stream = String.join("", Arrays.asList(a, plus, b)) ;
    Tokenizer tokenizer = buildTokenizer(stream);
    List<Token> expectedTokens = Arrays.asList(
        Token.build(TokenType.IDENTIFIER, a),
        Token.build(TokenType.SYMBOL, plus),
        Token.build(TokenType.IDENTIFIER, b)
    );
    TokenMatcher.matchAll(expectedTokens, tokenizer);
  }

  @Test
  public void returnStringLiteral() throws Exception {
    String content = "my string literal";
    String literal = "\"" + content + "\"";
  }
  @Test
  public void ignoresComment() throws Exception {
    String lineComment = "function // this is a comment";
    String blockComment = "/* this is a comment */let";
    String docComment = "}/** this is a comment */";

  }

  private Tokenizer buildTokenizer(String charStream) {
    CharReader reader = new CharReaderStub(charStream);
    return new FileTokenizer(reader);
  }

}