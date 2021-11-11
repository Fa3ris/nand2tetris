package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.tokenizer.Token.*;

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
        varToken(),
        charToken(),
        identifierToken(varName),
        semicolon());

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

  /**
   * var char foo, bar;
   */
  @Test
  public void twoCharDec() throws Exception {

    String foo = "foo";
    String bar = "bar";
    List<Token> tokens = Arrays.asList(
        varToken(),
        charToken(),
        identifierToken(foo),
        comma(),
        identifierToken(bar),
        semicolon());

    List<String> tags = Arrays.asList(
        XMLUtils.varTag(),
        charTag(),
        identifierTag(foo),
        commaTag(),
        identifierTag(bar),
        semicolonTag()
    );

    tags = encloseInTag(TagNames.varDec, tags);
    String expectedXML = concat(tags);

    assertASTXML(tokens, expectedXML);

  }
}
