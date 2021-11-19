package org.nand2tetris.parser.test_utils;

import static org.junit.Assert.assertEquals;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.parser.utils.XMLUtils.varTag;
import static org.nand2tetris.tokenizer.Token.comma;
import static org.nand2tetris.tokenizer.Token.semicolon;
import static org.nand2tetris.tokenizer.Token.varToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.transform.Source;
import org.junit.Assert;
import org.nand2tetris.parser.ParserEngine;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.parser.utils.Joiner;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

public abstract class TestUtils {

  public static void assertASTXML(List<Token> tokens,  String expectedXml) {
    AST ast = parse(tokens);
    assertEquals(expectedXml, ast.toXMLString());

  }

  public static void assertASTXML(List<Token> tokens,  List<String> expectedTags) {
    AST ast = parse(tokens);
    assertEquals(concat(expectedTags), ast.toXMLString());

  }

  public static void assertASTXML(List<Token> tokens,  File file) {
    String actual = parse(tokens).toXMLString();
    Source actualSource = Input.fromString(actual).build();
    Source expectedSource = Input.fromFile(file).build();
    try {
      assertEqualSources(expectedSource, actualSource);
    } catch (AssertionError e) {
      System.out.println(actual);
      throw e;
    }
  }

  private static void assertEqualSources(Source expected, Source actual) {
    Diff myDiff = DiffBuilder.compare(expected)
        .withTest(actual)
        .normalizeWhitespace()
        .build();

    Assert.assertFalse(myDiff.fullDescription(), myDiff.hasDifferences());
  }

  public static AST parse(List<Token> tokens) {
    return new ParserEngine(new TokenizerStub(tokens)).parse();
  }

  public static List<String> joinTags(List<String> identifierTags, String delimiterTag) {
    Joiner<String> tagJoiner = new Joiner<>(delimiterTag);
    return tagJoiner.join(identifierTags);
  }

  public static List<String> encloseInTag(String parentTag, List<String> tags) {
    List<String> enclosed = new ArrayList<>();
    enclosed.add(openTag(parentTag));
    enclosed.addAll(tags);
    enclosed.add(closeTag(parentTag));
    return enclosed;
  }

  public static List<Token> joinTokens(List<Token> identifierTokens, Token delimiter) {
    Joiner<Token> tokenJoiner = new Joiner<>(delimiter);
    return tokenJoiner.join(identifierTokens);
  }

  public static List<Token> varDecTokens(Token type, String identifier) {
    return varDecTokens(type, Collections.singletonList(identifier));
  }

  public static List<Token> varDecTokens(Token type, List<String> identifiers) {
    List<Token> tokens = new ArrayList<>();
    tokens.add(varToken());
    tokens.add(type);
    List<Token> joinedIdentifiers = TestUtils.joinTokens(identifiers.stream()
        .map(Token::identifierToken)
        .collect(Collectors.toList()), comma());
    tokens.addAll(joinedIdentifiers);
    tokens.add(semicolon());
    return tokens;
  }


  public static List<String> varDecTags(String type, String identifier) {
    return varDecTags(type, Collections.singletonList(identifier));
  }

  public static List<String> varDecTags(String type, List<String> identifiers) {
    List<String> tags = new ArrayList<>();
    tags.add(varTag());
    tags.add(type);
    List<String> joinedIdentifiers = TestUtils.joinTags(identifiers.stream()
        .map(XMLUtils::identifierTag)
        .collect(Collectors.toList()), commaTag());
    tags.addAll(joinedIdentifiers);
    tags.add(semicolonTag());
    return XMLUtils.encloseInTag(TagNames.varDec, tags);
  }

}
