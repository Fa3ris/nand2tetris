package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.test_utils.TestUtils.joinTags;
import static org.nand2tetris.parser.test_utils.TestUtils.joinTokens;
import static org.nand2tetris.parser.utils.XMLUtils.booleanTag;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.fieldTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.intTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.parser.utils.XMLUtils.staticTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;

public class ParseClassVarDecTest {

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

  /**
   * field 1, 2, ..., n-1, n;
   */
  @Test
  public void parseNClassVarDec() throws Exception {
    int n = 10;
    String[] identifiers = IntStream.rangeClosed(1, n)
        .mapToObj(Objects::toString).collect(Collectors.toList()).toArray(new String[]{});
    List<Token> tokens = classVarDecTokens(Token.field(), Token.intToken(), identifiers);
    List<String> expectedTags = classVarDecTags(fieldTag(), intTag(), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }

  /**
   * static int ROW;
   */
  @Test
  public void parseStatic() throws Exception {
    String[] identifiers = { "ROW"};
    List<Token> tokens = classVarDecTokens(Token.staticToken(), Token.intToken(), identifiers);
    List<String> expectedTags = classVarDecTags(staticTag(), intTag(), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }

  /**
   * field char joe;
   */
  @Test
  public void parseChar() throws Exception {
    String[] identifiers = { "joe"};
    List<Token> tokens = classVarDecTokens(Token.field(), Token.charToken(), identifiers);
    List<String> expectedTags = classVarDecTags(fieldTag(), charTag(), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);

  }

  /**
   * field boolean isOK;
   */
  @Test
  public void parseBoolean() throws Exception {
    String[] identifiers = { "isOK"};
    List<Token> tokens = classVarDecTokens(Token.field(), Token.booleanToken(), identifiers);
    List<String> expectedTags = classVarDecTags(fieldTag(), booleanTag(), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }

  /**
   * field MyClass myClass;
   */
  @Test
  public void parseClass() throws Exception {
    String[] identifiers = { "myClass"};
    String classType = "MyClass";
    List<Token> tokens = classVarDecTokens(Token.field(), Token.identifierToken(classType), identifiers);
    List<String> expectedTags = classVarDecTags(fieldTag(), identifierTag(classType), identifiers);
    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
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
    return XMLUtils.encloseInTag(TagNames.classVarDec, tags);
  }
}
