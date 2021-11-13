package org.nand2tetris.parser.test_utils;

import static org.junit.Assert.assertEquals;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;
import static org.nand2tetris.tokenizer.Token.comma;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.nand2tetris.parser.ParserEngine;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.parser.utils.Joiner;
import org.nand2tetris.tokenizer.Token;

public abstract class TestUtils {

  public static void assertASTXML(List<Token> tokens,  String expectedXml) {
    AST ast = parse(tokens);
    assertEquals(expectedXml, ast.toXMLString());

  }

  public static void assertASTXML(List<Token> tokens,  List<String> expectedTags) {
    AST ast = parse(tokens);
    assertEquals(concat(expectedTags), ast.toXMLString());

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

}
