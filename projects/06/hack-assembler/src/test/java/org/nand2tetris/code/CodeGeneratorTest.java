package org.nand2tetris.code;

import static org.junit.Assert.*;

import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.nand2tetris.CharRingBuffer;
import org.nand2tetris.HackAssemblerTest;
import org.nand2tetris.lexer.Lexer;
import org.nand2tetris.parser.AST;
import org.nand2tetris.parser.Parser;
import org.nand2tetris.parser.SymbolTable;
import org.nand2tetris.parser.resolver.Resolver;

public class CodeGeneratorTest {

  private Reader reader;
  private CharRingBuffer buffer;
  private Lexer lexer;
  private Parser parser = new Parser();
  private CodeGenerator generator;

  private Resolver resolver = new Resolver();

  private final static String ADD = "Add.asm";

  @Test
  public void codeAdd() throws Exception {
    setupLexerFromResource(ADD);
    AST ast = parser.parse(lexer);
    SymbolTable table = resolver.resolveSymbols(ast);
    generator = new CodeGenerator(ast, table);
    assertTrue(generator.hasNextCode());
    assertEquals("0000000000000010",generator.nextCode() );
    assertEquals("1110110000010000",generator.nextCode() );
    assertEquals("0000000000000011",generator.nextCode() );
    assertEquals("1110000010010000",generator.nextCode() );
    assertEquals("0000000000000000", generator.nextCode());
    assertEquals("1110001100001000",generator.nextCode() );
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

}