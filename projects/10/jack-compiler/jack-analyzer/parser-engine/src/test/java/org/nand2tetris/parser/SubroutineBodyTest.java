package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTags;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTokens;
import static org.nand2tetris.parser.utils.XMLUtils.booleanTag;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.tokenizer.Token.booleanToken;
import static org.nand2tetris.tokenizer.Token.charToken;
import static org.nand2tetris.tokenizer.Token.closeBrace;
import static org.nand2tetris.tokenizer.Token.openBrace;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class SubroutineBodyTest {


  /**
   * {
   *       var char key;
   *       var boolean exit;
   *  }
   */
  @Test
  public void onlyVarDec() throws Exception {
    List<Token> tokens = new ArrayList<>();
    String first = "key";
    String second = "exit";
    tokens.add(openBrace());
    tokens.addAll(varDecTokens(charToken(), first));
    tokens.addAll(varDecTokens(booleanToken(), second));
    tokens.add(closeBrace());

    List<String> tags = new ArrayList<>();
    tags.add(openBraceTag());
    tags.addAll(varDecTags(charTag(), first));
    tags.addAll(varDecTags(booleanTag(), second));
    tags.add(closeBraceTag());
    tags = encloseInTag(TagNames.subroutineBody, tags);
    assertASTXML(tokens, tags);
  }

}
