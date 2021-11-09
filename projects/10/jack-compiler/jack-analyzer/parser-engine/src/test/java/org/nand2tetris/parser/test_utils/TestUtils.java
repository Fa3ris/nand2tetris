package org.nand2tetris.parser.test_utils;

import static org.junit.Assert.assertEquals;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

import java.util.ArrayList;
import java.util.List;
import org.nand2tetris.parser.ParserEngine;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.tokenizer.Token;

public abstract class TestUtils {

  public static void assertASTXML(List<Token> tokens,  String expectedXml) {
    AST ast = parse(tokens);
    assertEquals(expectedXml, ast.toXMLString());

  }

  public static AST parse(List<Token> tokens) {
    return new ParserEngine(new TokenizerStub(tokens)).parse();
  }

  public static List<String> joinTags(List<String> identifierTags, String delimiterTag) {
    List<String> joined = new ArrayList<>();
    for (String identifierTag : identifierTags) {
      if (!joined.isEmpty()) {
        joined.add(delimiterTag);
      }
      joined.add(identifierTag);
    }
    return joined;
  }

  public static List<String> encloseInTag(String parentTag, List<String> tags) {
    List<String> enclosed = new ArrayList<>();
    enclosed.add(openTag(parentTag));
    enclosed.addAll(tags);
    enclosed.add(closeTag(parentTag));
    return enclosed;
  }

  public static List<Token> joinTokens(List<Token> identifierTokens, Token delimiter) {
    List<Token> joined = new ArrayList<>();
    for (Token identifierToken : identifierTokens) {
      if (!joined.isEmpty()) {
        joined.add(delimiter);
      }
      joined.add(identifierToken);
    }
    return joined;
  }

}
