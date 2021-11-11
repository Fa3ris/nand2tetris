package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.utils.XMLUtils.classTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.utils.TagNames;
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
    String parentTag = TagNames.classTag;
    String expectedXml = concat(encloseInTag(parentTag, Arrays.asList(
        classTag(),
        identifierTag(className),
        openBraceTag(),
        closeBraceTag())));

    assertASTXML(tokens, expectedXml);
  }

}
