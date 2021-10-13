package org.nand2tetris.tokenizer.tester;

import static org.junit.Assert.*;

import org.junit.Test;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;

public class TokenToTagMapperTest {

  private final TokenToTagMapper mapper = new TokenToTagMapper();

  @Test
  public void returnKeywordTag() throws Exception {
    Token token = Token.build(TokenType.KEYWORD, "if");
    String tag = mapper.convertToken(token);
    String expectedContent = "<keyword> if </keyword>";
    assertEquals(expectedContent, tag);
  }

  @Test
  public void returnSymbolTag() throws Exception {
    Token token = Token.build(TokenType.SYMBOL, "(");
    String tag = mapper.convertToken(token);
    String expectedContent = "<symbol> ( </symbol>";
    assertEquals(expectedContent, tag);
  }

  @Test
  public void returnIntegerTag() throws Exception {
    Token token = Token.build(TokenType.INTEGER, "0");
    String tag = mapper.convertToken(token);
    String expectedContent = "<intConst> 0 </intConst>";
    assertEquals(expectedContent, tag);
  }

  @Test
  public void returnStringTag() throws Exception {
    Token token = Token.build(TokenType.STRING, "negative");
    String tag = mapper.convertToken(token);
    String expectedContent = "<stringConst> negative </stringConst>";
    assertEquals(expectedContent, tag);
  }

  @Test
  public void returnIdentifierTag() throws Exception {
    Token token = Token.build(TokenType.IDENTIFIER, "sign");
    String tag = mapper.convertToken(token);
    String expectedContent = "<identifier> sign </identifier>";
    assertEquals(expectedContent, tag);
  }

  @Test
  public void escapeHTMLSymbols() throws Exception {
    String[] symbols = { "<", ">", "\"", "&"};
    String[] escaped = {"&lt;",	"&gt;",	"&quot;", "&amp;"};

    for (int i = 0; i < symbols.length; i++) {
      Token token = Token.build(TokenType.IDENTIFIER, symbols[i]);
      String tag = mapper.convertToken(token);
      String expectedContent = "<identifier> " + escaped[i] + " </identifier>";
      assertEquals(expectedContent, tag);

    }


  }

}