package org.nand2tetris.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;

public class ParserEngineTest {

  @Test
  public void parseEmptyClass() throws Exception {
    List<Token> tokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, "class"),
        Token.build(TokenType.IDENTIFIER, "Main"),
        Token.build(TokenType.SYMBOL, "{"),
        Token.build(TokenType.SYMBOL, "}")
    );

    AST ast = parse(tokens);

    String expectedXml = concat(Arrays.asList(
        openTag("class"),
        leafTag("keyword", "class"),
        leafTag("identifier", "Main"),
        leafTag("symbol", "{"),
        leafTag("symbol", "}"),
        closeTag("class")));

    assertEquals(expectedXml, ast.toXMLString());
  }

  private AST parse(List<Token> tokens) {
    return new ParserEngine(new TokenizerStub(tokens)).parse();
  }

  private String openTag(String tagName) {
    return String.format("<%s>", tagName);
  }

  private String closeTag(String tagName) {
    return String.format("</%s>", tagName);
  }

  private String leafTag(String tagName, String innerText) {
    return String.format("<%1$s> %2$s </%1$s>", tagName, innerText);
  }

  private String concat(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String s : list) {
      sb.append(s);
    }
    return sb.toString();
  }
}
