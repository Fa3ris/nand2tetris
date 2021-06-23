package org.nand2tetris;

import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.nand2tetris.helper.ParserTestHelper;
import org.nand2tetris.lexer.Lexer;
import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenType;
import org.nand2tetris.parser.AST;
import org.nand2tetris.parser.CInstruction;
import org.nand2tetris.parser.Factor;
import org.nand2tetris.parser.Node;
import org.nand2tetris.parser.Parser;
import org.nand2tetris.parser.Plus;

public class LexerToParserTest {

  private Reader reader;
  private CharRingBuffer buffer;
  private Lexer lexer;
  private Parser parser;


  private final static String ADD = "Add.asm";
  private final static String MAX = "Max.asm";

  @Before
  public void setUp() throws Exception {
    parser = new Parser();
  }

  @Test
  public void parseAdd() throws Exception {
    setupLexerFromResource(ADD);
    AST ast = parser.parse(lexer);
    List<Node> instructions = ast.instructions();
    System.out.println(instructions);
    Iterator<Node> it = instructions.iterator();

    ParserTestHelper.assertIsAInstruction(it.next(), new Token(TokenType.INTEGER, "2"));

    CInstruction cInstruction = (CInstruction) it.next();
    Node dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, ParserTestHelper.D);
    Node jmp = cInstruction.getJmp();
    ParserTestHelper.assertIsFactor(jmp, Token.NULL);
    Node comp = cInstruction.getComp();
    ParserTestHelper.assertIsIdentityOperation(comp, new Factor(ParserTestHelper.A));

    ParserTestHelper.assertIsAInstruction(it.next(), new Token(TokenType.INTEGER, "3"));

    cInstruction = (CInstruction) it.next();
    dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, ParserTestHelper.D);
    jmp = cInstruction.getJmp();
    ParserTestHelper.assertIsFactor(jmp, Token.NULL);
    comp = cInstruction.getComp();
    ParserTestHelper.assertIsBinaryOp(comp, Plus.class, new Factor(ParserTestHelper.D), new Factor(ParserTestHelper.A));

    ParserTestHelper.assertIsAInstruction(it.next(), new Token(TokenType.INTEGER, "0"));

    cInstruction = (CInstruction) it.next();
    dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, ParserTestHelper.M);
    jmp = cInstruction.getJmp();
    ParserTestHelper.assertIsFactor(jmp, Token.NULL);
    comp = cInstruction.getComp();
    ParserTestHelper.assertIsIdentityOperation(comp, new Factor(ParserTestHelper.D));
  }

  @Test
  public void parseMax() throws Exception {
    setupLexerFromResource(MAX);
    AST ast = parser.parse(lexer);
    List<Node> instructions = ast.instructions();
    System.out.println(instructions);
    Iterator<Node> it = instructions.iterator();

    ParserTestHelper.assertIsAInstruction(it.next(), new Token(TokenType.IDENTIFIER, "R0"));
    it.next(); // D=M
    ParserTestHelper.assertIsAInstruction(it.next(), new Token(TokenType.IDENTIFIER, "R1"));
    it.next(); // D=D-M
    ParserTestHelper.assertIsAInstruction(it.next(), new Token(TokenType.IDENTIFIER,"OUTPUT_FIRST"));
    it.next(); // D;JGT
    it.next(); // @R1
    it.next(); // D=M
    it.next(); // @OUTPUT_D
    it.next(); // 0;JMP
    ParserTestHelper.assertIsLabelDefinition(it.next(), "OUTPUT_FIRST");
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
