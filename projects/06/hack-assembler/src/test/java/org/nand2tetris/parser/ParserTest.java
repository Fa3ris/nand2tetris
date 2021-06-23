package org.nand2tetris.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.nand2tetris.helper.ParserTestHelper;
import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenStream;
import org.nand2tetris.lexer.TokenType;

public class ParserTest {

  private final Parser parser = new Parser();

  private final TokenStream stream = Mockito.mock(TokenStream.class);

  private Queue<Token> tokens = new LinkedList<>();

  private AST ast;

  private final Token at = new Token(TokenType.AT, "@");

  @Before
  public void setUp() throws Exception {
    Mockito.when(stream.peekToken()).then((InvocationOnMock invocation) -> tokens.peek());
    Mockito.when(stream.nextToken()).then((InvocationOnMock invocation) -> tokens.poll());
  }

  @Test
  public void parseAInstruction() throws Exception {
    List<Node> instructions = parseInstructions(at, ParserTestHelper.one, ParserTestHelper.eof);
    ParserTestHelper.assertIsAInstruction(instructions.get(0), ParserTestHelper.one);

    instructions = parseInstructions(at, ParserTestHelper.R0, ParserTestHelper.eof);
    ParserTestHelper.assertIsAInstruction(instructions.get(0), ParserTestHelper.R0);
  }

  @Test
  public void parseLabelDefinition() throws Exception {
    List<Node> instructions = parseInstructions(ParserTestHelper.openParen, ParserTestHelper.label, ParserTestHelper.closeParen, ParserTestHelper.eof);
    ParserTestHelper.assertIsLabelDefinition(instructions.get(0), ParserTestHelper.label.getValue());
  }

  @Test
  public void parseDestCompJmp() throws Exception {
    List<Node> instructions = parseInstructions(ParserTestHelper.A, ParserTestHelper.assign, ParserTestHelper.D, ParserTestHelper.plus, ParserTestHelper.A, ParserTestHelper.semicolon, ParserTestHelper.jeq, ParserTestHelper.eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, ParserTestHelper.A);

    Node jmp = cInstruction.getJmp();
    ParserTestHelper.assertIsFactor(jmp, ParserTestHelper.jeq);

    Node comp = cInstruction.getComp();
    ParserTestHelper.assertIsBinaryOp(comp, Plus.class, new Factor(ParserTestHelper.D), new Factor(ParserTestHelper.A));
  }

  @Test
  public void parseCompOnly() throws Exception {
    List<Node> instructions = parseInstructions(ParserTestHelper.A, ParserTestHelper.minus, ParserTestHelper.one, ParserTestHelper.eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, Token.NULL);

    Node jmp = cInstruction.getJmp();
    ParserTestHelper.assertIsFactor(jmp, Token.NULL);

    Node comp = cInstruction.getComp();
    ParserTestHelper.assertIsBinaryOp(comp, Minus.class, new Factor(ParserTestHelper.A), new Factor(
        ParserTestHelper.one));
  }


  @Test
  public void parseDestComp() throws Exception {
    List<Node> instructions = parseInstructions(ParserTestHelper.MD, ParserTestHelper.assign, ParserTestHelper.D, ParserTestHelper.bitAnd, ParserTestHelper.M, ParserTestHelper.eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, ParserTestHelper.MD);

    Node jmp = cInstruction.getJmp();
    ParserTestHelper.assertIsFactor(jmp, Token.NULL);

    Node comp = cInstruction.getComp();
    ParserTestHelper.assertIsBinaryOp(comp, BitAnd.class, new Factor(ParserTestHelper.D), new Factor(
        ParserTestHelper.M));
  }

  @Test
  public void parseCompJmp() throws Exception {
    List<Node> instructions = parseInstructions(ParserTestHelper.D, ParserTestHelper.bitAnd, ParserTestHelper.M, ParserTestHelper.semicolon, ParserTestHelper.jne, ParserTestHelper.eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, Token.NULL);

    Node jmp = cInstruction.getJmp();
    ParserTestHelper.assertIsFactor(jmp, ParserTestHelper.jne);

    Node comp = cInstruction.getComp();
    ParserTestHelper.assertIsBinaryOp(comp, BitAnd.class, new Factor(ParserTestHelper.D), new Factor(
        ParserTestHelper.M));
  }

  @Test
  public void parseNotExpression() throws Exception {
    Token[] tokens = new Token[]{ParserTestHelper.A, ParserTestHelper.D, ParserTestHelper.M};

    for (Token token : tokens) {
      List<Node> instructions = parseInstructions(ParserTestHelper.not, token, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      ParserTestHelper.assertIsFactor(dest, Token.NULL);

      Node jmp = cInstruction.getJmp();
      ParserTestHelper.assertIsFactor(jmp, Token.NULL);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsUnary(comp, Not.class, new Factor(token));

    }
  }

  @Test
  public void parseNegateExpression() throws Exception {
    Token[] tokens = new Token[]{ParserTestHelper.A, ParserTestHelper.D, ParserTestHelper.M, ParserTestHelper.one};
    for (Token token : tokens) {

      List<Node> instructions = parseInstructions(
          ParserTestHelper.minus, token, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      ParserTestHelper.assertIsFactor(dest, Token.NULL);

      Node jmp = cInstruction.getJmp();
      ParserTestHelper.assertIsFactor(jmp, Token.NULL);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsUnary(comp, Negate.class, new Factor(token));
    }
  }

  @Test
  public void parsePossibleDest() throws Exception {
    Token[] tokens = new Token[]{ParserTestHelper.A, ParserTestHelper.D, ParserTestHelper.M, ParserTestHelper.AM, ParserTestHelper.AD, ParserTestHelper.MD, ParserTestHelper.AMD};

    for (Token token : tokens) {

      List<Node> instructions = parseInstructions(token, ParserTestHelper.assign, ParserTestHelper.D, ParserTestHelper.bitAnd, ParserTestHelper.M, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      ParserTestHelper.assertIsFactor(dest, token);
    }

    List<Node> instructions = parseInstructions(ParserTestHelper.D, ParserTestHelper.bitAnd, ParserTestHelper.M, ParserTestHelper.eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    ParserTestHelper.assertIsFactor(dest, Token.NULL);

  }

  @Test
  public void parsePossibleJmp() throws Exception {
    Token[] tokens = new Token[]{ParserTestHelper.jgt, ParserTestHelper.jeq, ParserTestHelper.jge, ParserTestHelper.jlt, ParserTestHelper.jne, ParserTestHelper.jle, ParserTestHelper.jmp};
    for (Token token : tokens) {
      List<Node> instructions = parseInstructions(ParserTestHelper.D, ParserTestHelper.bitAnd, ParserTestHelper.M, ParserTestHelper.semicolon, token, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node jmp = cInstruction.getJmp();
      ParserTestHelper.assertIsFactor(jmp, token);
    }
  }

  @Test
  public void parseConstantExpression() throws Exception {
    Token[] constants = new Token[]{ParserTestHelper.zero, ParserTestHelper.one, ParserTestHelper.A, ParserTestHelper.D, ParserTestHelper.M};
    for (Token constant : constants) {
      List<Node> instructions = parseInstructions(constant, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      ParserTestHelper.assertIsFactor(dest, Token.NULL);

      Node jmp = cInstruction.getJmp();
      ParserTestHelper.assertIsFactor(jmp, Token.NULL);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsIdentityOperation(comp, new Factor(constant));
    }

  }

  @Test
  public void parseMinus() throws Exception {

    for (Token factor2 : new Token[]{ParserTestHelper.one, ParserTestHelper.A, ParserTestHelper.M}) {
      List<Node> instructions = parseInstructions(ParserTestHelper.D, ParserTestHelper.minus, factor2, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsBinaryOp(comp, Minus.class, new Factor(ParserTestHelper.D), new Factor(factor2));
    }

    for (Token factor1 : new Token[]{ParserTestHelper.A, ParserTestHelper.M}) {

      for (Token factor2 : new Token[]{ParserTestHelper.one, ParserTestHelper.D}) {
        List<Node> instructions = parseInstructions(factor1, ParserTestHelper.minus, factor2, ParserTestHelper.eof);

        assertTrue(instructions.get(0) instanceof CInstruction);
        CInstruction cInstruction = (CInstruction) instructions.get(0);

        Node comp = cInstruction.getComp();
        ParserTestHelper.assertIsBinaryOp(comp, Minus.class, new Factor(factor1), new Factor(factor2));
      }
    }
  }

  @Test
  public void parsePlus() throws Exception {
    for (Token factor1 : new Token[]{ParserTestHelper.D, ParserTestHelper.A, ParserTestHelper.M}) {
      List<Node> instructions = parseInstructions(factor1, ParserTestHelper.plus, ParserTestHelper.one, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsBinaryOp(comp, Plus.class, new Factor(factor1), new Factor(
          ParserTestHelper.one));
    }

    for (Token factor2 : new Token[]{ParserTestHelper.A, ParserTestHelper.M}) {
      List<Node> instructions = parseInstructions(ParserTestHelper.D, ParserTestHelper.plus, factor2, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsBinaryOp(comp, Plus.class, new Factor(ParserTestHelper.D), new Factor(factor2));
    }
  }

  @Test
  public void parseLogical() throws Exception {

    for (Token factor2 : new Token[]{ParserTestHelper.A, ParserTestHelper.M}) {
      List<Node> instructions = parseInstructions(ParserTestHelper.D, ParserTestHelper.bitAnd, factor2, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsBinaryOp(comp, BitAnd.class, new Factor(ParserTestHelper.D), new Factor(factor2));
    }

    for (Token factor2 : new Token[]{ParserTestHelper.A, ParserTestHelper.M}) {
      List<Node> instructions = parseInstructions(ParserTestHelper.D, ParserTestHelper.bitOr, factor2, ParserTestHelper.eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      ParserTestHelper.assertIsBinaryOp(comp, BitOr.class, new Factor(ParserTestHelper.D), new Factor(factor2));
    }
  }


  @Test
  public void stopWhenEOF() throws Exception {
    List<Node> children = parseInstructions(ParserTestHelper.eof);
    System.out.println(children);
    assertTrue(children.isEmpty());
  }

  private void printRoot() {
    System.out.println(ast.getProgram());
  }

  private List<Node> parseInstructions(Token... tokenList) {
    tokens = new LinkedList<>(Arrays.asList(tokenList));
    ast = parser.parse(stream);
    List<Node> instructions = ast.instructions();
    System.out.println(instructions);
    return instructions;
  }

}
