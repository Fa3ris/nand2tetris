package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.constructorTag;
import static org.nand2tetris.parser.utils.XMLUtils.functionTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.methodTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.voidTag;
import static org.nand2tetris.tokenizer.Token.*;

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
        functionToken(),
        voidToken(),
        identifierToken(functionName),
        openParen(),
        closeParen(),
        openBrace(),
        closeBrace());

    List<String> expectedTags = subroutineDecTags(
        functionTag(),
        voidTag(),
        identifierTag(functionName),
        parameterListTags(),
        subroutineBodyTags());

    assertASTXML(tokens, expectedTags);
  }

  /**
   * constructor MyClass new() {}
   */
  @Test
  public void constructorArgumentLessBodyLess() throws Exception {
    String type = "MyClass";
    String methodName = "new";
    List<Token> tokens = Arrays.asList(
        constructorToken(),
        identifierToken(type),
        identifierToken(methodName),
        openParen(),
        closeParen(),
        openBrace(),
        closeBrace());

    List<String> expectedTags = subroutineDecTags(
        constructorTag(),
        identifierTag(type),
        identifierTag(methodName),
        parameterListTags(),
        subroutineBodyTags());

    assertASTXML(tokens, expectedTags);
  }

  /**
   * method void printHello() {}
   */
  @Test
  public void methodArgumentLessBodyLess() throws Exception {
    String functionName = "printHello";
    List<Token> tokens = Arrays.asList(
        methodToken(),
        voidToken(),
        identifierToken(functionName),
        openParen(),
        closeParen(),
        openBrace(),
        closeBrace());

    List<String> expectedTags = subroutineDecTags(
        methodTag(),
        voidTag(),
        identifierTag(functionName),
        parameterListTags(),
        subroutineBodyTags());

    assertASTXML(tokens, expectedTags);

  }

  /**
   * method Square create() {}
   */
  @Test
  public void methodReturnClass() throws Exception {
    String type = "Square";
    String methodName = "create";
    List<Token> tokens = Arrays.asList(
        methodToken(),
        identifierToken(type),
        identifierToken(methodName),
        openParen(),
        closeParen(),
        openBrace(),
        closeBrace());

    List<String> expectedTags = subroutineDecTags(
        methodTag(),
        identifierTag(type),
        identifierTag(methodName),
        parameterListTags(),
        subroutineBodyTags());

    assertASTXML(tokens, expectedTags);
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
