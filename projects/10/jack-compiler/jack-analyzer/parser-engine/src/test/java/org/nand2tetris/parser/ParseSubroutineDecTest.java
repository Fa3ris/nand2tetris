package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.booleanTag;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.constructorTag;
import static org.nand2tetris.parser.utils.XMLUtils.functionTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.intTag;
import static org.nand2tetris.parser.utils.XMLUtils.methodTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.voidTag;
import static org.nand2tetris.tokenizer.Token.booleanToken;
import static org.nand2tetris.tokenizer.Token.charToken;
import static org.nand2tetris.tokenizer.Token.closeBrace;
import static org.nand2tetris.tokenizer.Token.closeParen;
import static org.nand2tetris.tokenizer.Token.comma;
import static org.nand2tetris.tokenizer.Token.constructorToken;
import static org.nand2tetris.tokenizer.Token.functionToken;
import static org.nand2tetris.tokenizer.Token.identifierToken;
import static org.nand2tetris.tokenizer.Token.intToken;
import static org.nand2tetris.tokenizer.Token.methodToken;
import static org.nand2tetris.tokenizer.Token.openBrace;
import static org.nand2tetris.tokenizer.Token.openParen;
import static org.nand2tetris.tokenizer.Token.voidToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.parser.utils.Joiner;
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

  /**
   * constructor Square new(int Ax, int Ay) {}
   */
  @Test
  public void constructorParameterList() throws Exception {
    String type = "Square";
    String methodName = "new";
    String firstArg = "Ax";
    String secondArg = "Ay";
    List<Token> tokens = Arrays.asList(
        constructorToken(),
        identifierToken(type),
        identifierToken(methodName),
        openParen(),
        intToken(),
        identifierToken(firstArg),
        comma(),
        intToken(),
        identifierToken(secondArg),
        closeParen(),
        openBrace(),
        closeBrace());

    List<String> expectedTags = subroutineDecTags(
        constructorTag(),
        identifierTag(type),
        identifierTag(methodName),
        parameterListTags(Arrays.asList(
            intTag(),
            identifierTag(firstArg),
            commaTag(),
            intTag(),
            identifierTag(secondArg)
        )),
        subroutineBodyTags());

    assertASTXML(tokens, expectedTags);

  }

  /**
   * constructor Square new(boolean foo, char bar, int baz, Rectangle rectangle) {}
   */
  @Test
  public void constructorMultiParameterList() throws Exception {
    String type = "Square";
    String methodName = "new";
    String firstArg = "foo";
    String secondArg = "bar";
    String thirdArg = "baz";
    String fourthType = "Rectangle";
    String fourthArg = "rectangle";
    List<Token> argTokens = new ArrayList<>();
    argTokens.addAll(booleanArg(firstArg));
    argTokens.add(comma());
    argTokens.addAll(charArg(secondArg));
    argTokens.add(comma());
    argTokens.addAll(intArg(thirdArg));
    argTokens.add(comma());
    argTokens.addAll(classArg(fourthType, fourthArg));
    List<Token> tokens = new ArrayList<>();
    tokens.add(constructorToken());
    tokens.add(identifierToken(type));
    tokens.add(identifierToken(methodName));
    tokens.add(openParen());
    tokens.addAll(argTokens);
    tokens.add(closeParen());
    tokens.add(openBrace());
    tokens.add(closeBrace());

    List<String> parameterListTags = new ArrayList<>();
    parameterListTags.addAll(booleanArgTag(firstArg));
    parameterListTags.add(commaTag());
    parameterListTags.addAll(charArgTag(secondArg));
    parameterListTags.add(commaTag());
    parameterListTags.addAll(intArgTag(thirdArg));
    parameterListTags.add(commaTag());
    parameterListTags.addAll(classArgTag(fourthType, fourthArg));

    List<String> expectedTags = subroutineDecTags(
        constructorTag(),
        identifierTag(type),
        identifierTag(methodName),
        parameterListTags(parameterListTags),
        subroutineBodyTags());
    assertASTXML(tokens, expectedTags);
  }

  private List<Token> booleanArg(String identifier) {
    return Arrays.asList(
        booleanToken(),
        identifierToken(identifier)
    );
  }

  private List<String> booleanArgTag(String identifier) {
    return Arrays.asList(
        booleanTag(),
        identifierTag(identifier)
    );
  }

  private List<Token> charArg(String identifier) {
    return Arrays.asList(
        charToken(),
        identifierToken(identifier)
    );
  }

  private List<String> charArgTag(String identifier) {
    return Arrays.asList(
        charTag(),
        identifierTag(identifier)
    );
  }

  private List<Token> intArg(String identifier) {
    return Arrays.asList(
        intToken(),
        identifierToken(identifier)
    );
  }

  private List<String> intArgTag(String identifier) {
    return Arrays.asList(
        intTag(),
        identifierTag(identifier)
    );
  }

  private List<Token> classArg(String className, String varName) {
    return Arrays.asList(
        identifierToken(className),
        identifierToken(varName)
    );
  }

  private List<String> classArgTag(String className, String varName) {
    return Arrays.asList(
        identifierTag(className),
        identifierTag(varName)
    );
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
    List<String> tags = Collections.emptyList();
    return parameterListTags(tags);
  }

  private List<String> parameterListTags(List<String> tags) {
    return encloseInTag(TagNames.parameterList, tags);
  }

  private List<String> subroutineBodyTags() {
    List<String> tags = new ArrayList<>();
    tags.add(openBraceTag());
    tags.add(closeBraceTag());
    return encloseInTag(TagNames.subroutineBody, tags);
  }

}
