package org.nand2tetris.parser;

import static org.junit.Assert.assertEquals;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.leafTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

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
    String expectedXml = concat(Arrays.asList(
        openTag("class"),
        leafTag("keyword", "class"),
        leafTag("identifier", className),
        leafTag("symbol", "{"),
        leafTag("symbol", "}"),
        closeTag("class")));

    assertASTXML(tokens, expectedXml);
  }

  @Test
  public void parseSimpleClassVarDec() throws Exception {
    List<Token> tokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, "field"),
        Token.build(TokenType.KEYWORD, "int"),
        Token.build(TokenType.IDENTIFIER, "size"),
        Token.build(TokenType.SYMBOL, ";")
    );

    String expectedXml = concat(Arrays.asList(
        openTag("classVarDec"),
        leafTag("keyword", "field"),
        leafTag("keyword", "int"),
        leafTag("identifier", "size"),
        leafTag("symbol", ";"),
        closeTag("classVarDec")));

    assertASTXML(tokens, expectedXml);
  }

  @Test
  public void parseMultipleClassVarDec() throws Exception {
    List<Token> tokens = Arrays.asList(
        Token.build(TokenType.KEYWORD, "field"),
        Token.build(TokenType.KEYWORD, "int"),
        Token.build(TokenType.IDENTIFIER, "x"),
        Token.build(TokenType.SYMBOL, ","),
        Token.build(TokenType.IDENTIFIER, "y"),
        Token.build(TokenType.SYMBOL, ";")
    );

    String expectedXml = concat(Arrays.asList(
        openTag("classVarDec"),
        leafTag("keyword", "field"),
        leafTag("keyword", "int"),
        leafTag("identifier", "x"),
        leafTag("symbol", ","),
        leafTag("identifier", "y"),
        leafTag("symbol", ";"),
        closeTag("classVarDec")));

    assertASTXML(tokens, expectedXml);
  }

  private void assertASTXML(List<Token> tokens,  String expectedXml) {
    AST ast = parse(tokens);
    assertEquals(expectedXml, ast.toXMLString());

  }

  private AST parse(List<Token> tokens) {
    return new ParserEngine(new TokenizerStub(tokens)).parse();
  }
}
