package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;

public class VarDecTest {


  /**
   * var char key;
   */
  @Test
  public void singleCharDec() throws Exception {

    String varName = "key";
    List<Token> tokens = Arrays.asList(
        Token.varToken(),
        Token.charToken(),
        Token.identifierToken(varName),
        Token.semicolon());

    List<String> tags = Arrays.asList(
        XMLUtils.varTag(),
        charTag(),
        identifierTag(varName),
        semicolonTag()
    );

    tags = encloseInTag(TagNames.varDec, tags);
    String expectedXML = concat(tags);

    assertASTXML(tokens, expectedXML);

  }
}
