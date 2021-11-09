package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.test_utils.TestUtils.joinTags;
import static org.nand2tetris.parser.test_utils.TestUtils.joinTokens;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.fieldTag;
import static org.nand2tetris.parser.utils.XMLUtils.intTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
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
    return encloseInTag("classVarDec", tags);
  }
}
