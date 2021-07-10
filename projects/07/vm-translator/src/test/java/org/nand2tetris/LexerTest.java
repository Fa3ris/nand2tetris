package org.nand2tetris;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;

public class LexerTest {

  @Test
  public void scanReturnsSameToken() throws Exception {
    SymbolTable table = new SymbolTable();
    Reader r = new StringReader("push constant");
    CharReader reader = new CharReader(r);
    Lexer lexer = new Lexer(reader, table);

    Token push = new Token(TokenType.PUSH, "PUSH");
    assertEquals(push, lexer.scan());
    assertEquals(push, lexer.scan());
  }

  @Test
  public void eof() throws Exception {
    SymbolTable table = new SymbolTable();
    Reader r = new StringReader("");
    CharReader reader = new CharReader(r);
    Lexer lexer = new Lexer(reader, table);

    Token eof = new Token(TokenType.EOF, "EOF");
    assertEquals(eof, lexer.scan());
    assertEquals(eof, lexer.scan());
  }

  @Test
  public void next() throws Exception {

    SymbolTable table = new SymbolTable();
    Reader r = new StringReader("push constant 17");
    CharReader reader = new CharReader(r);
    Lexer lexer = new Lexer(reader, table);

    Token push = new Token(TokenType.PUSH, "PUSH");
    Token constant = new Token(TokenType.CONSTANT, "CONSTANT");
    Token int17 = new Token(TokenType.INTEGER, "17");
    Token eof = new Token(TokenType.EOF, "EOF");

    assertEquals(push, lexer.next());
    assertEquals(constant, lexer.next());
    assertEquals(int17, lexer.next());
    assertEquals(eof, lexer.next());
  }

  @Test
  public void commentAndWhiteSpaceIgnored() throws Exception {

    SymbolTable table = new SymbolTable();
    Reader r = new StringReader("// This file is part of www.nand2tetris.org\n"
        + "// and the book \"The Elements of Computing Systems\"\n"
        + "// by Nisan and Schocken, MIT Press.\n"
        + "// File name: projects/07/StackArithmetic/StackTest/StackTest.vm\n"
        + "\n"
        + "// Executes a sequence of arithmetic and logical operations");
    CharReader reader = new CharReader(r);
    Lexer lexer = new Lexer(reader, table);

    Token eof = new Token(TokenType.EOF, "EOF");
    assertEquals(eof, lexer.next());
    assertEquals(eof, lexer.next());

  }

  @Test
  public void simpleAdd() throws Exception {
    SymbolTable table = new SymbolTable();
    Reader r = new StringReader("// This file is part of www.nand2tetris.org\n"
        + "// and the book \"The Elements of Computing Systems\"\n"
        + "// by Nisan and Schocken, MIT Press.\n"
        + "// File name: projects/07/StackArithmetic/SimpleAdd/SimpleAdd.vm\n"
        + "\n"
        + "// Pushes and adds two constants.\n"
        + "push constant 7\n"
        + "push constant 8\n"
        + "add\n");
    CharReader reader = new CharReader(r);
    Lexer lexer = new Lexer(reader, table);

    Token push = new Token(TokenType.PUSH, "PUSH");
    Token constant = new Token(TokenType.CONSTANT, "CONSTANT");
    Token int7 = new Token(TokenType.INTEGER, "7");
    Token int8 = new Token(TokenType.INTEGER, "8");
    Token add = new Token(TokenType.ADD, "ADD");
    Token eof = new Token(TokenType.EOF, "EOF");

    assertEquals(push, lexer.next());
    assertEquals(constant, lexer.next());
    assertEquals(int7, lexer.next());
    assertEquals(push, lexer.next());
    assertEquals(constant, lexer.next());
    assertEquals(int8, lexer.next());
    assertEquals(add, lexer.next());
    assertEquals(eof, lexer.next());
  }

  @Test
  public void labelDeclaration() throws Exception {

    SymbolTable table = new SymbolTable();
    Reader r = new StringReader("label LOOP_START");
    CharReader reader = new CharReader(r);
    Lexer lexer = new Lexer(reader, table);

    Token label = new Token(TokenType.LABEL_DEFINITION, "LABEL");
    Token labelName = new Token(TokenType.IDENTIFIER, "LOOP_START");
    Token eof = new Token(TokenType.EOF, "EOF");

    assertEquals(label, lexer.next());
    assertEquals(labelName, lexer.next());
    assertEquals(eof, lexer.next());
  }

}