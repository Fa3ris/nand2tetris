package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTags;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTokens;
import static org.nand2tetris.parser.utils.XMLUtils.booleanTag;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.tokenizer.Token.booleanToken;
import static org.nand2tetris.tokenizer.Token.charToken;
import static org.nand2tetris.tokenizer.Token.closeBrace;
import static org.nand2tetris.tokenizer.Token.identifierToken;
import static org.nand2tetris.tokenizer.Token.openBrace;
import static org.nand2tetris.tokenizer.Token.semicolon;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
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

  /**
   * {
   *   let game = game;
   * }
   */
  @Test
  public void oneStatement() throws Exception {
    List<Token> tokens = new ArrayList<>();
    String id = "game";
    tokens.add(openBrace());
    tokens.add(Token.letToken());
    tokens.add(identifierToken(id));
    tokens.add(Token.equalToken());
    tokens.add(identifierToken(id));
    tokens.add(semicolon());
    tokens.add(closeBrace());

    /*
    <subroutineBody>
      <symbol> { </symbol>
       <letStatement>
                <keyword> let </keyword>
                <identifier> game </identifier>
                <symbol> = </symbol>
                <expression>
                  <term>
                    <identifier> game </identifier>
                  </term>
                </expression>
                <symbol> ; </symbol>
              </letStatement>
      <symbol> } </symbol>
    </subroutineBody>
     */

    List<String> termTags = new ArrayList<>();
    termTags.add(identifierTag(id));
    termTags = encloseInTag(TagNames.termTag, termTags);

    List<String> expressionTags = new ArrayList<>();
    expressionTags.addAll(termTags);
    expressionTags = encloseInTag(TagNames.expressionTag, expressionTags);

    List<String> letTags = new ArrayList<>();
    letTags.add(XMLUtils.letTag());
    letTags.add(identifierTag(id));
    letTags.add(XMLUtils.equalTag());
    letTags.addAll(expressionTags);
    letTags.add(semicolonTag());
    letTags = encloseInTag(TagNames.letStatement, letTags);

    List<String> tags = new ArrayList<>();
    tags.add(openBraceTag());
    tags.addAll(letTags);
    tags.add(closeBraceTag());
    tags = encloseInTag(TagNames.subroutineBody, tags);
    assertASTXML(tokens, tags);
  }

}
