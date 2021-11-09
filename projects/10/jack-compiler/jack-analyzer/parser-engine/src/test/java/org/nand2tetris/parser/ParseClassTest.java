package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.utils.XMLUtils.classTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.tokenizer.Token;

public class ParseClassTest {

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

}
