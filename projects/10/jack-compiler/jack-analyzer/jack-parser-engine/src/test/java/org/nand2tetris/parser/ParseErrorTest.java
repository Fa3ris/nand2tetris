package org.nand2tetris.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.tokenizer.Token;

public class ParseErrorTest {


  /**
   * duplicated class name
   * class Main Foo {}
   */
  @Test
  public void errorContainsExpectedTokenType() throws Exception {
    List<Token> tokens = Arrays.asList(
        Token.classToken(),
        Token.identifierToken("Main"),
        Token.identifierToken("Foo"),
        Token.openBrace(),
        Token.closeBrace()
    );
    try {
      new ParserEngine(new TokenizerStub(tokens)).parse();
    } catch (Exception e) {
      Assert.assertTrue(e.getMessage().contains("SYMBOL"));
    }
  }

  /**
   * invalid type
   * field { size;
   */
  @Test
  public void invalidClassVarType() throws Exception {
    List<Token> tokens = new ArrayList<>();
    tokens.add(Token.field());
    tokens.add(Token.openParen());
    tokens.add(Token.identifierToken("size"));
    try {
      new ParserEngine(new TokenizerStub(tokens)).parse();
    } catch (Exception e) {
      Assert.assertTrue(e.getMessage().contains("type"));
    }
  }

}
