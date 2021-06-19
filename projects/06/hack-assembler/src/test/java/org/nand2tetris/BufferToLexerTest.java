package org.nand2tetris;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.nand2tetris.lexer.Lexer;
import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenType;

public class BufferToLexerTest {

  private CharRingBuffer buffer;

  private Lexer lexer;

  private Reader reader;

  private Token token;

  private boolean printToken = true;

  private final static String ADD = "Add.asm";

  @Test
  public void ADD() throws Exception {

    setupLexerFromResource(ADD);

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

  private void setupLexerFromResource(String name) throws Exception {
    URL resourceUrl = HackAssemblerTest.class.getClassLoader().getResource(name);
    if (resourceUrl == null) {
      String msg = String.format("file %s not found", name);
      throw new RuntimeException(msg);
    }
    reader = Files.newBufferedReader(Paths.get(resourceUrl.toURI()));
    buffer = new CharRingBuffer(reader);
    lexer = new Lexer(buffer);
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

}
