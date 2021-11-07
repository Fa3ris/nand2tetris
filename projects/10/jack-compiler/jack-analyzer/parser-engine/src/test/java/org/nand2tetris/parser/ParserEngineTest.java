package org.nand2tetris.parser;

import static org.junit.Assert.assertEquals;
import static org.nand2tetris.parser.utils.XMLUtils.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.TokenType;

public class ParserEngineTest {

  @Test
  public void parseEmptyClass() throws Exception {
    parseEmptyClass("Main");
    parseEmptyClass("Foo");
  }

  private void parseEmptyClass(String className) {
    List<Token> tokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, "class"),
        Token.build(TokenType.IDENTIFIER, className),
        Token.build(TokenType.SYMBOL, "{"),
        Token.build(TokenType.SYMBOL, "}")
    );
    AST ast = parse(tokens);
    String expectedXml = concat(Arrays.asList(
        openTag("class"),
        leafTag("keyword", "class"),
        leafTag("identifier", className),
        leafTag("symbol", "{"),
        leafTag("symbol", "}"),
        closeTag("class")));
    assertEquals(expectedXml, ast.toXMLString());
  }

  @Test
  public void parseSimpleClassVarDec() throws Exception {
    List<Token> tokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, "field"),
        Token.build(TokenType.KEYWORD, "int"),
        Token.build(TokenType.IDENTIFIER, "size"),
        Token.build(TokenType.SYMBOL, ";")
    );

    AST ast = parse(tokens);

    String expectedXml = concat(Arrays.asList(
        openTag("classVarDec"),
        leafTag("keyword", "field"),
        leafTag("keyword", "int"),
        leafTag("identifier", "size"),
        leafTag("symbol", ";"),
        closeTag("classVarDec")));

    assertEquals(expectedXml, ast.toXMLString());
  }

  private AST parse(List<Token> tokens) {
    return new ParserEngine(new TokenizerStub(tokens)).parse();
  }
}
