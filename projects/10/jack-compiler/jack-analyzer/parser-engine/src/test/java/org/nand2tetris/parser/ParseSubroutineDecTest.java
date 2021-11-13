package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.constructorTag;
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
   */
  @Test
  public void functionArgumentLessBodyLess() throws Exception {

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

  /**
   * constructor MyClass new() {}
   */
  @Test
  public void constructorArgumentLessBodyLess() throws Exception {
    String type = "MyClass";
    String methodName = "new";
    List<Token> tokens = Arrays.asList(
        Token.constructorToken(),
        Token.identifierToken(type),
        Token.identifierToken(methodName),
        Token.openParen(),
        Token.closeParen(),
        Token.openBrace(),
        Token.closeBrace());

    List<String> expectedTags = subroutineDecTags(
        constructorTag(),
        identifierTag(type),
        identifierTag(methodName),
        parameterListTags(),
        subroutineBodyTags());

    String expectedXml = concat(expectedTags);
    assertASTXML(tokens, expectedXml);
  }

  /**
   * method void printHello() {}
   */
  @Test
  public void methodArgumentLessBodyLess() throws Exception {

  }

  /**
   * method void printHello() {}
   */
  @Test
  public void name() throws Exception {

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
    tags.add(concat(subRoutineBodyTag));
    return encloseInTag(TagNames.subroutineDec, tags);
  }

  private List<String> parameterListTags() {
    List<String> tags = new ArrayList<>();
    return encloseInTag(TagNames.parameterList, tags);
  }

  private List<String> subroutineBodyTags() {
    List<String> tags = new ArrayList<>();
    tags.add(openBraceTag());
    tags.add(closeBraceTag());
    return encloseInTag(TagNames.subroutineBody, tags);
  }

}
