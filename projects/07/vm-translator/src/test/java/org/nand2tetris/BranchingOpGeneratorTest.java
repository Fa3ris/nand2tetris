package org.nand2tetris;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class BranchingOpGeneratorTest {
  
  private final BranchingOpGenerator opGenerator = new BranchingOpGenerator();
  
  @Test
  public void label_globalScope() throws Exception {
    Token label = new Token(TokenType.IDENTIFIER, "FOO");
    List<String> instructions = opGenerator.label(label);
    assertEquals("(GLOBAL$FOO)", instructions.get(1));
  }

  @Test
  public void goto_globalScope() throws Exception {
    Token label = new Token(TokenType.IDENTIFIER, "FOO");
    List<String> instructions = opGenerator.goTo(label);
    assertEquals("@GLOBAL$FOO", instructions.get(1));
  }

  @Test
  public void if_goto_globalScope() throws Exception {
    Token label = new Token(TokenType.IDENTIFIER, "FOO");
    List<String> instructions = opGenerator.ifGoto(label);
    assertEquals("@GLOBAL$FOO", instructions.get(4));
  }

  @Test
  public void label_functionScope() throws Exception {
    String functionName = "Bar.main";
    opGenerator.setScope(functionName);
    Token label = new Token(TokenType.IDENTIFIER, "HELLO");
    List<String> instructions = opGenerator.label(label);
    assertEquals("(Bar.main$HELLO)", instructions.get(1));
  }

  @Test
  public void goto_functionScope() throws Exception {
    String functionName = "Bar.main";
    opGenerator.setScope(functionName);
    Token label = new Token(TokenType.IDENTIFIER, "HELLO");
    List<String> instructions = opGenerator.goTo(label);
    assertEquals("@Bar.main$HELLO", instructions.get(1));
  }

  @Test
  public void if_goto_functionScope() throws Exception {
    String functionName = "Bar.main";
    opGenerator.setScope(functionName);
    Token label = new Token(TokenType.IDENTIFIER, "HELLO");
    List<String> instructions = opGenerator.ifGoto(label);
    assertEquals("@Bar.main$HELLO", instructions.get(4));
  }


  @Test
  public void label_resetScope() throws Exception {
    String functionName = "Bar.main";
    opGenerator.setScope(functionName);
    Token label = new Token(TokenType.IDENTIFIER, "HELLO");
    List<String> instructions = opGenerator.label(label);
    assertEquals("(Bar.main$HELLO)", instructions.get(1));
    opGenerator.resetScope();
    List<String> instructions1 = opGenerator.label(label);
    assertEquals("(GLOBAL$HELLO)", instructions1.get(1));
  }

  @Test
  public void goto_resetScope() throws Exception {
    String functionName = "Bar.main";
    opGenerator.setScope(functionName);
    Token label = new Token(TokenType.IDENTIFIER, "HELLO");
    List<String> instructions = opGenerator.goTo(label);
    assertEquals("@Bar.main$HELLO", instructions.get(1));
    opGenerator.resetScope();
    List<String> instructions1 = opGenerator.goTo(label);
    assertEquals("@GLOBAL$HELLO", instructions1.get(1));
  }

  @Test
  public void if_goto_resetScope() throws Exception {
    String functionName = "Bar.main";
    opGenerator.setScope(functionName);
    Token label = new Token(TokenType.IDENTIFIER, "HELLO");
    List<String> instructions = opGenerator.ifGoto(label);
    assertEquals("@Bar.main$HELLO", instructions.get(4));
    opGenerator.resetScope();
    List<String> instructions1 = opGenerator.ifGoto(label);
    assertEquals("@GLOBAL$HELLO", instructions1.get(4));
  }

}