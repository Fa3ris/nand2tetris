package org.nand2tetris.parser.resolver;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenType;
import org.nand2tetris.parser.AInstruction;
import org.nand2tetris.parser.AST;
import org.nand2tetris.parser.CInstruction;
import org.nand2tetris.parser.LabelDefinition;
import org.nand2tetris.parser.Node;
import org.nand2tetris.parser.Program;
import org.nand2tetris.parser.SymbolTable;

public class ResolverTest {

  private final Resolver resolver = new Resolver();
  private AST ast;

  private final CInstruction fakeC =  new CInstruction(null, null, null);

  @Test
  public void resolveForwardLabel() throws Exception {
    String label = "FOO";
    List<Node> statements = Arrays.asList(
        new AInstruction(new Token(TokenType.IDENTIFIER, label)), // @FOO
        fakeC,
        new LabelDefinition(new Token(TokenType.IDENTIFIER, label)) // (FOO)
    );

    Node program = new Program(statements);
    ast = new AST(program);
    System.out.println(ast.instructions());

    SymbolTable table = resolver.resolveSymbols(ast);
    assertEquals(4, (int) table.lookup(label));
  }

  @Test
  public void resolveLabel() throws Exception {
    String label = "FOO";
    List<Node> statements = Arrays.asList(
        new LabelDefinition(new Token(TokenType.IDENTIFIER, label)), // (FOO)
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label)) // @FOO
    );

    Node program = new Program(statements);
    ast = new AST(program);
    System.out.println(ast.instructions());

    SymbolTable table = resolver.resolveSymbols(ast);
    assertEquals(2, (int) table.lookup(label));
  }

  @Test
  public void resolveSameLabel() throws Exception {
    String label = "FOO";
    List<Node> statements = Arrays.asList(
        new LabelDefinition(new Token(TokenType.IDENTIFIER, label)), // (FOO)
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label)), // @FOO
        new AInstruction(new Token(TokenType.IDENTIFIER, label)), // @FOO
        fakeC,
        fakeC,
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label)) // @FOO
    );

    Node program = new Program(statements);
    ast = new AST(program);
    System.out.println(ast.instructions());

    SymbolTable table = resolver.resolveSymbols(ast);
    assertEquals(2, (int) table.lookup(label));
  }

  @Test
  public void resolveVariable() throws Exception {
    String label = "FOO";
    List<Node> statements = Arrays.asList(
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label)) // @FOO
    );

    Node program = new Program(statements);
    ast = new AST(program);
    System.out.println(ast.instructions());

    SymbolTable table = resolver.resolveSymbols(ast);
    assertEquals(16, (int) table.lookup(label));
  }

  @Test
  public void resolveVariables() throws Exception {
    String label = "FOO";
    String label2 = "BAR";
    List<Node> statements = Arrays.asList(
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label)), // @FOO
        new AInstruction(new Token(TokenType.IDENTIFIER, label2)) // @BAR
    );

    Node program = new Program(statements);
    ast = new AST(program);
    System.out.println(ast.instructions());

    SymbolTable table = resolver.resolveSymbols(ast);
    assertEquals(16, (int) table.lookup(label));
    assertEquals(17, (int) table.lookup(label2));
  }

  @Test
  public void resolveSameVar() throws Exception {
    String label = "FOO";
    List<Node> statements = Arrays.asList(
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label)), // @FOO
        fakeC,
        fakeC,
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label)) // @FOO
    );

    Node program = new Program(statements);
    ast = new AST(program);
    System.out.println(ast.instructions());

    SymbolTable table = resolver.resolveSymbols(ast);
    assertEquals(16, (int) table.lookup(label));
  }

  @Test
  public void resolveVarAndLabels() throws Exception {
    String label = "FOO";
    String label2 = "BAR";
    List<Node> statements = Arrays.asList(
        new AInstruction(new Token(TokenType.IDENTIFIER, label)), // @FOO
        fakeC,
        new AInstruction(new Token(TokenType.IDENTIFIER, label2)), // @BAR
        new LabelDefinition(new Token(TokenType.IDENTIFIER, label)), // (FOO)
        fakeC
    );

    Node program = new Program(statements);
    ast = new AST(program);
    System.out.println(ast.instructions());

    SymbolTable table = resolver.resolveSymbols(ast);
    assertEquals(5, (int) table.lookup(label));
    assertEquals(16, (int) table.lookup(label2));
  }

}