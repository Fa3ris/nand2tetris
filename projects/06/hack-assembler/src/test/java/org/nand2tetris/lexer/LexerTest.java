package org.nand2tetris.lexer;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.nand2tetris.CharBuffer;

public class LexerTest {

  private CharBuffer buffer = Mockito.mock(CharBuffer.class);

  private Queue<Character> queue;

  private Lexer lexer;

  private Token token;

  private boolean printToken = true;

  @Before
  public void setupMock() throws Exception {
    Mockito.when(buffer.peek()).then((InvocationOnMock invocation) -> queue.peek());
    Mockito.when(buffer.read()).then((InvocationOnMock invocation) -> queue.poll());
    Mockito.when(buffer.isEmpty()).then((InvocationOnMock invocation) -> queue.isEmpty());
    Mockito.when(buffer.toString()).then((InvocationOnMock invocation) -> queue.toString());
  }

  @Test
  public void eof() throws Exception {
    String test = "";
    setupLexer(test);
    assertNextTokenOfType(TokenType.EOF);
  }

  @Test
  public void lineCommentIgnored() throws Exception {
    String oneLine = "// This file is part of www.nand2tetris.org";

    String twoLines = "// This file is part of www.nand2tetris.org\n"
        + "// and the book \"The Elements of Computing Systems\"";

    String[] comments = new String[] {oneLine, twoLines};
    for (String comment : comments) {
      setupLexer(comment);
      assertNextTokenOfType(TokenType.EOF);

    }
  }

  @Test
  public void keyword() throws Exception {
    String test = "A";
    setupLexer(test);
    assertNextTokenOfType(TokenType.A);
    assertTokenValue(test);
    assertNextTokenOfType(TokenType.EOF);

    test = "JGT";
    setupLexer(test);
    assertNextTokenOfType(TokenType.JGT);
    assertTokenValue(test);
    assertNextTokenOfType(TokenType.EOF);
  }

  @Test
  public void operator() throws Exception {
    String test = "+";
    setupLexer(test);
    assertNextTokenOfType(TokenType.PLUS);
    assertTokenValue(test);

    test = "|";
    setupLexer(test);
    assertNextTokenOfType(TokenType.BIT_OR);
    assertTokenValue(test);

    test = "=";
    setupLexer(test);
    assertNextTokenOfType(TokenType.ASSIGN);
    assertTokenValue(test);
  }

  @Test
  public void whiteSpaceIgnored() throws Exception {

    String[] whiteSpaces = new String[] {" ", "    ",  "\n", "\r" , "\t"};

    for (String ws: whiteSpaces) {
      setupLexer(ws);
      assertNextTokenOfType(TokenType.EOF);
      assertTokenValue("EOF");
    }
  }

  @Test
  public void identifier() throws Exception {

    String[] identifiers = new String[] {"foo", "BAR", "   BAR   ", "_toto", "_123", "_bar123"};

    for (String ws: identifiers) {
      setupLexer(ws);
      assertNextTokenOfType(TokenType.IDENTIFIER);
      assertTokenValue(ws.trim());
    }
  }

  @Test
  public void integer() throws Exception {

    String[] integers = new String[] {"123", "000112321313"};

    for (String integer: integers) {
      setupLexer(integer);
      assertNextTokenOfType(TokenType.INTEGER);
      assertTokenValue(integer);
    }

  }

  @Test
  public void labelDeclaration() throws Exception {
    String test = "(LOOP)";
    setupLexer(test);
    assertNextTokenOfType(TokenType.OPEN_PAREN);
    assertNextTokenOfTypeAndValue(TokenType.IDENTIFIER, "LOOP");
    assertNextTokenOfType(TokenType.CLOSE_PAREN);
  }

  @Test
  public void compound() throws Exception {
    String test = "// This file is part of www.nand2tetris.org\n"
        + "// and the book \"The Elements of Computing Systems\"\n"
        + "// by Nisan and Schocken, MIT Press.\n"
        + "// File name: projects/06/add/Add.asm\n"
        + "\n"
        + "// Computes R0 = 2 + 3  (R0 refers to RAM[0])\n"
        + "\n"
        + "@2\n"
        + "D=A\n"
        + "@3\n"
        + "D=D+A\n"
        + "@0\n"
        + "M=D";

    setupLexer(test);

    assertNextTokenOfType(TokenType.AT);
    assertNextTokenOfTypeAndValue(TokenType.INTEGER, "2");

    assertNextTokenOfType(TokenType.D);
    assertNextTokenOfType(TokenType.ASSIGN);
    assertNextTokenOfType(TokenType.A);

    assertNextTokenOfType(TokenType.AT);
    assertNextTokenOfTypeAndValue(TokenType.INTEGER, "3");

    assertNextTokenOfType(TokenType.D);
    assertNextTokenOfType(TokenType.ASSIGN);
    assertNextTokenOfType(TokenType.D);
    assertNextTokenOfType(TokenType.PLUS);
    assertNextTokenOfType(TokenType.A);

    assertNextTokenOfType(TokenType.AT);
    assertNextTokenOfTypeAndValue(TokenType.INTEGER, "0");

    assertNextTokenOfType(TokenType.M);
    assertNextTokenOfType(TokenType.ASSIGN);
    assertNextTokenOfType(TokenType.D);

    assertNextTokenOfType(TokenType.EOF);
  }

  private void nextToken() {
    token = lexer.nextToken();
    if (printToken) {
      System.out.println(token);
    }
  }

  private void assertNextTokenOfTypeAndValue(TokenType expectedType, String expectedValue) {
    nextToken();
    assertTokenOfType(expectedType);
    assertTokenValue(expectedValue);
  }

  private void assertTokenOfType(TokenType expectedType) {
    assertEquals(expectedType, token.getType());
  }

  private void assertTokenValue(String expectedValue) {
    assertEquals(expectedValue, token.getValue());
  }

  private void assertNextTokenOfType(TokenType expectedType) {
    nextToken();
    assertTokenOfType(expectedType);
  }

  private void setupLexer(String str) {
    setupQueueBuffer(str);
    lexer = new Lexer(buffer);
  }

  private void setupQueueBuffer(String str) {
    List<Character> charList = toCharList(str);
    queue = new LinkedList<>(charList);
  }

  private List<Character> toCharList(String str) {
    return str.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
  }

}