package org.nand2tetris.tokenizer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.nand2tetris.tokenizer.stubs.CharReaderStub;

public class FileTokenizerTest {


  @Test
  public void shouldHaveTokenIfNoContent() throws Exception {
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
  public void shouldHaveTokenIfContent() throws Exception {

    CharReader[] readers = new CharReader[]{
        new CharReaderStub("Hello"),
        new CharReaderStub("{"),
        new CharReaderStub("+"),
    };
    for (CharReader reader : readers) {
      Tokenizer tokenizer = new FileTokenizer(reader);
      tokenizer.advance();
      assertTrue(tokenizer.hasToken());
    }
  }

  @Test
  public void shouldReturnIdentifierToken() throws Exception {
    String[] lexemes = new String[] {"Hello", "Bye"};
    for (String lexeme : lexemes) {
      CharReader reader = new CharReaderStub(lexeme);
      Tokenizer tokenizer = new FileTokenizer(reader);
      tokenizer.advance();
      Token actual = tokenizer.peekToken();

      Token expected = new Token();
      expected.setLexeme(lexeme);
      expected.setType(TokenType.IDENTIFIER);
      assertEquals(expected, actual);
    }
  }

  @Test
  public void shouldReturnKeyword_class() throws Exception {
    String lexeme = "class";
    CharReader reader = new CharReaderStub(lexeme);
    Tokenizer tokenizer = new FileTokenizer(reader);
    tokenizer.advance();
    Token actual = tokenizer.peekToken();
    Token expected = new Token();
    expected.setLexeme(lexeme);
    expected.setType(TokenType.KEYWORD);
    assertEquals(expected, actual);
  }

}