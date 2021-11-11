package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.functionTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.voidTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class ParseSubroutineDecTest {

  /**
   * function void myFunction() {}
   * @throws Exception
   */
  @Test
  public void argumentLessNoBody() throws Exception {

    String functionName = "myFunction";
    List<Token> tokens = Arrays.asList(
        Token.functionToken(),
        Token.voidToken(),
        Token.identifierToken(functionName),
        Token.openParen(),
        Token.closeParen(),
        Token.openBrace(),
        Token.closeBrace());

    List<String> expectedTags = subroutineDecTags(
        functionTag(),
        voidTag(),
        identifierTag(functionName),
        parameterListTags(),
        subroutineBodyTags());

    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }


  private List<String> subroutineDecTags(String routineType, String returnType, String routineName,
      List<String> parameterListTag, List<String> subRoutineBodyTag) {
    List<String> tags = new ArrayList<>();
    tags.add(routineType);
    tags.add(returnType);
    tags.add(routineName);
    tags.add(openParenTag());
    tags.add(concat(parameterListTag));
    tags.add(closeParenTag());
    tags.add(openBraceTag());
    tags.add(concat(subRoutineBodyTag));
    tags.add(closeBraceTag());
    return encloseInTag(TagNames.subroutineDec, tags);
  }

  private List<String> parameterListTags() {
    List<String> tags = new ArrayList<>();
    return encloseInTag(TagNames.parameterList, tags);
  }

  private List<String> subroutineBodyTags() {
    List<String> tags = new ArrayList<>();
    return encloseInTag(TagNames.subroutineBody, tags);
  }

}
