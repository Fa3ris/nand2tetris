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
    String test = "// This file is part of www.nand2tetris.org";
    setupLexer(test);
    assertNextTokenOfType(TokenType.EOF);
  }

  @Test
  public void keyword() throws Exception {
    String test = "A";
    setupLexer(test);
    assertNextTokenOfType(TokenType.A);
    assertNextTokenOfType(TokenType.EOF);

    test = "JGT";
    setupLexer(test);
    assertNextTokenOfType(TokenType.JGT);
    assertNextTokenOfType(TokenType.EOF);
  }

  @Test
  public void PLUS() throws Exception {
    String test = "+";
    setupLexer(test);
    assertNextTokenOfType(TokenType.PLUS);
  }

  @Test
  public void whiteSpaceIgnored() throws Exception {

    String[] whiteSpaces = new String[] {" ", "    ",  "\n", "\r" , "\t"};

    for (String ws: whiteSpaces) {
      setupLexer(ws);
      assertNextTokenOfType(TokenType.EOF);
    }
  }

  @Test
  public void identifier() throws Exception {
    String test = "foo";
    setupLexer(test);
    assertNextTokenOfType(TokenType.IDENTIFIER);
  }

  private void assertNextTokenOfType(TokenType expectedType) {
    Token token = lexer.nextToken();
    System.out.println(token);
    assertEquals(expectedType, token.getType());
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