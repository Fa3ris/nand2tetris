package org.nand2tetris.parser;

import static org.junit.Assert.assertEquals;
import static org.nand2tetris.parser.utils.XMLUtils.classTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.fieldTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.intTag;
import static org.nand2tetris.parser.utils.XMLUtils.leafTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;

public class ParserEngineTest {

  @Test
  public void parseEmptyClass() throws Exception {
    parseEmptyClass("Main");
    parseEmptyClass("Foo");
  }

  private void parseEmptyClass(String className) {
    List<Token> tokens = Arrays.asList(
        Token.classToken(),
        Token.identifierToken(className),
        Token.openBrace(),
        Token.closeBrace()
    );
    String parentTag = "class";
    String expectedXml = concat(Arrays.asList(
        openTag(parentTag),
        classTag(),
        identifierTag(className),
        openBraceTag(),
        closeBraceTag(),
        closeTag(parentTag)));

    assertASTXML(tokens, expectedXml);
  }

  /**
   * field int size;
   */
  @Test
  public void parseSimpleClassVarDec() throws Exception {
    String[] identifiers = { "size"};
    List<Token> tokens = classVarDecTokens(Token.field(), Token.intToken(), identifiers);
    List<String> expectedTags = classVarDecTags(fieldTag(), intTag(), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }

  /**
   * field int x, y;
   */
  @Test
  public void parseTwoClassVarDec() throws Exception {

    String[] identifiers = { "x", "y"};
    List<Token> tokens = classVarDecTokens(Token.field(), Token.intToken(), identifiers);
    List<String> expectedTags = classVarDecTags(fieldTag(), intTag(), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }

  /**
   * field foo, bar, baz;
   */
  @Test
  public void parseMultipleClassVarDec() throws Exception {
    String[] identifiers = { "foo", "bar", "baz"};
    List<Token> tokens = classVarDecTokens(Token.field(), Token.intToken(), identifiers);
    List<String> expectedTags = classVarDecTags(fieldTag(), intTag(), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }

  private List<String> classVarDecTags(String scope, String type, String[] identifiers) {
    List<String> tags = new ArrayList<>();
    tags.add(scope);
    tags.add(type);
    List<String> identifierTags = Arrays.stream(identifiers)
        .map(XMLUtils::identifierTag)
        .collect(Collectors.toList());
    List<String> joinedIdTags = joinTags(identifierTags, commaTag());
    tags.addAll(joinedIdTags);
    tags.add(semicolonTag());
    return encloseInTag("classVarDec", tags);
  }

  private List<String> encloseInTag(String parentTag, List<String> tags) {
    List<String> enclosed = new ArrayList<>();
    enclosed.add(openTag(parentTag));
    enclosed.addAll(tags);
    enclosed.add(closeTag(parentTag));
    return enclosed;
  }

  private List<String> joinTags(List<String> identifierTags, String delimiterTag) {
    List<String> joined = new ArrayList<>();
    for (String identifierTag : identifierTags) {
      if (!joined.isEmpty()) {
        joined.add(delimiterTag);
      }
      joined.add(identifierTag);
    }
    return joined;
  }
  
  private List<Token> classVarDecTokens(Token scope, Token type,
      String[] identifiers) {

    List<Token> tokens = new ArrayList<>();
    tokens.add(scope);
    tokens.add(type);

    List<Token> identifierTokens = Arrays.stream(identifiers)
        .map(Token::identifierToken)
        .collect(Collectors.toList());

    List<Token> joinedTokens = joinTokens(identifierTokens, Token.comma());

    tokens.addAll(joinedTokens);
    tokens.add(Token.semicolon());

    return tokens;
  }

  private List<Token> joinTokens(List<Token> identifierTokens, Token delimiter) {
    List<Token> joined = new ArrayList<>();
    for (Token identifierToken : identifierTokens) {
      if (!joined.isEmpty()) {
        joined.add(delimiter);
      }
      joined.add(identifierToken);
    }
    return joined;
  }

  private void assertASTXML(List<Token> tokens,  String expectedXml) {
    AST ast = parse(tokens);
    assertEquals(expectedXml, ast.toXMLString());

  }

  private AST parse(List<Token> tokens) {
    return new ParserEngine(new TokenizerStub(tokens)).parse();
  }
}
