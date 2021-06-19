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
import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenStream;
import org.nand2tetris.lexer.TokenType;

public class ParserTest {

  private final Parser parser = new Parser();

  private final TokenStream stream = Mockito.mock(TokenStream.class);

  private Queue<Token> tokens = new LinkedList<>();

  private AST ast;

  private final Token at = new Token(TokenType.AT, "@");

  private final Token A = new Token(TokenType.A, "A");
  private final Token D = new Token(TokenType.D, "D");
  private final Token M = new Token(TokenType.M, "M");

  private final Token zero = new Token(TokenType.INTEGER, "0");
  private final Token one = new Token(TokenType.INTEGER, "1");
  private final Token R0 = new Token(TokenType.IDENTIFIER, "R0");

  private final Token openParen = new Token(TokenType.OPEN_PAREN, "(");
  private final Token label = new Token(TokenType.IDENTIFIER, "FOO");
  private final Token closeParen = new Token(TokenType.CLOSE_PAREN, ")");

  private final Token MD = new Token(TokenType.MD, "MD");
  private final Token AM = new Token(TokenType.AM, "AM");
  private final Token AD = new Token(TokenType.AD, "AD");
  private final Token AMD = new Token(TokenType.AMD, "AMD");

  private final Token assign = new Token(TokenType.ASSIGN, "=");

  private final Token plus = new Token(TokenType.PLUS, "+");
  private final Token minus = new Token(TokenType.MINUS, "-");

  private final Token bitAnd = new Token(TokenType.BIT_AND, "&");
  private final Token bitOr = new Token(TokenType.BIT_OR, "|");

  private final Token not = new Token(TokenType.NOT, "!");

  private final Token semicolon = new Token(TokenType.SEMI_COLON, ";");

  private final Token jeq = new Token(TokenType.JEQ, "JEQ");
  private final Token jne = new Token(TokenType.JNE, "JNE");
  private final Token jgt = new Token(TokenType.JGT, "JGT");
  private final Token jge = new Token(TokenType.JGE, "JGE");
  private final Token jlt = new Token(TokenType.JLT, "JLT");
  private final Token jle = new Token(TokenType.JLE, "JLE");
  private final Token jmp = new Token(TokenType.JMP, "JMP");

  private final Token eof = new Token(TokenType.EOF, "EOF");

  @Before
  public void setUp() throws Exception {
    Mockito.when(stream.peekToken()).then((InvocationOnMock invocation) -> tokens.peek());
    Mockito.when(stream.nextToken()).then((InvocationOnMock invocation) -> tokens.poll());
  }

  @Test
  public void parseAInstruction() throws Exception {
    List<Node> instructions = parseInstructions(at, one, eof);
    assertIsAInstruction(instructions.get(0), one);

    instructions = parseInstructions(at, R0, eof);
    assertIsAInstruction(instructions.get(0), R0);
  }

  @Test
  public void parseLabelDefinition() throws Exception {
    List<Node> instructions = parseInstructions(openParen, label, closeParen, eof);
    assertIsLabelDefinition(instructions.get(0), label.getValue());
  }

  @Test
  public void parseDestCompJmp() throws Exception {
    List<Node> instructions = parseInstructions(A, assign, D, plus, A, semicolon, jeq, eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    assertIsFactor(dest, A);

    Node jmp = cInstruction.getJmp();
    assertIsFactor(jmp, jeq);

    Node comp = cInstruction.getComp();
    assertIsBinaryOp(comp, Plus.class, new Factor(D), new Factor(A));
  }

  @Test
  public void parseCompOnly() throws Exception {
    List<Node> instructions = parseInstructions(A, minus, one, eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    assertIsFactor(dest, Token.NULL);

    Node jmp = cInstruction.getJmp();
    assertIsFactor(jmp, Token.NULL);

    Node comp = cInstruction.getComp();
    assertIsBinaryOp(comp, Minus.class, new Factor(A), new Factor(one));
  }


  @Test
  public void parseDestComp() throws Exception {
    List<Node> instructions = parseInstructions(MD, assign, D, bitAnd, M, eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    assertIsFactor(dest, MD);

    Node jmp = cInstruction.getJmp();
    assertIsFactor(jmp, Token.NULL);

    Node comp = cInstruction.getComp();
    assertIsBinaryOp(comp, BitAnd.class, new Factor(D), new Factor(M));
  }

  @Test
  public void parseCompJmp() throws Exception {
    List<Node> instructions = parseInstructions(D, bitAnd, M, semicolon, jne, eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    assertIsFactor(dest, Token.NULL);

    Node jmp = cInstruction.getJmp();
    assertIsFactor(jmp, jne);

    Node comp = cInstruction.getComp();
    assertIsBinaryOp(comp, BitAnd.class, new Factor(D), new Factor(M));
  }

  @Test
  public void parseNotExpression() throws Exception {
    Token[] tokens = new Token[]{A, D, M};

    for (Token token : tokens) {
      List<Node> instructions = parseInstructions(not, token, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      assertIsFactor(dest, Token.NULL);

      Node jmp = cInstruction.getJmp();
      assertIsFactor(jmp, Token.NULL);

      Node comp = cInstruction.getComp();
      assertIsUnary(comp, Not.class, new Factor(token));

    }
  }

  @Test
  public void parseNegateExpression() throws Exception {
    Token[] tokens = new Token[]{A, D, M, one};
    for (Token token : tokens) {

      List<Node> instructions = parseInstructions(minus, token, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      assertIsFactor(dest, Token.NULL);

      Node jmp = cInstruction.getJmp();
      assertIsFactor(jmp, Token.NULL);

      Node comp = cInstruction.getComp();
      assertIsUnary(comp, Negate.class, new Factor(token));
    }
  }

  @Test
  public void parsePossibleDest() throws Exception {
    Token[] tokens = new Token[]{A, D, M, AM, AD, MD, AMD};

    for (Token token : tokens) {

      List<Node> instructions = parseInstructions(token, assign, D, bitAnd, M, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      assertIsFactor(dest, token);
    }

    List<Node> instructions = parseInstructions(D, bitAnd, M, eof);

    assertTrue(instructions.get(0) instanceof CInstruction);
    CInstruction cInstruction = (CInstruction) instructions.get(0);

    Node dest = cInstruction.getDest();
    assertIsFactor(dest, Token.NULL);

  }

  @Test
  public void parsePossibleJmp() throws Exception {
    Token[] tokens = new Token[]{jgt, jeq, jge, jlt, jne, jle, jmp};
    for (Token token : tokens) {
      List<Node> instructions = parseInstructions(D, bitAnd, M, semicolon, token, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node jmp = cInstruction.getJmp();
      assertIsFactor(jmp, token);
    }
  }

  @Test
  public void parseConstantExpression() throws Exception {
    Token[] constants = new Token[]{zero, one, A, D, M};
    for (Token constant : constants) {
      List<Node> instructions = parseInstructions(constant, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node dest = cInstruction.getDest();
      assertIsFactor(dest, Token.NULL);

      Node jmp = cInstruction.getJmp();
      assertIsFactor(jmp, Token.NULL);

      Node comp = cInstruction.getComp();
      assertIsIdentityOperation(comp, new Factor(constant));
    }

  }

  @Test
  public void parseMinus() throws Exception {

    for (Token factor2 : new Token[]{one, A, M}) {
      List<Node> instructions = parseInstructions(D, minus, factor2, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      assertIsBinaryOp(comp, Minus.class, new Factor(D), new Factor(factor2));
    }

    for (Token factor1 : new Token[]{A, M}) {

      for (Token factor2 : new Token[]{one, D}) {
        List<Node> instructions = parseInstructions(factor1, minus, factor2, eof);

        assertTrue(instructions.get(0) instanceof CInstruction);
        CInstruction cInstruction = (CInstruction) instructions.get(0);

        Node comp = cInstruction.getComp();
        assertIsBinaryOp(comp, Minus.class, new Factor(factor1), new Factor(factor2));
      }
    }
  }

  @Test
  public void parsePlus() throws Exception {
    for (Token factor1 : new Token[]{D, A, M}) {
      List<Node> instructions = parseInstructions(factor1, plus, one, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      assertIsBinaryOp(comp, Plus.class, new Factor(factor1), new Factor(one));
    }

    for (Token factor2 : new Token[]{A, M}) {
      List<Node> instructions = parseInstructions(D, plus, factor2, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      assertIsBinaryOp(comp, Plus.class, new Factor(D), new Factor(factor2));
    }
  }

  @Test
  public void parseLogical() throws Exception {

    for (Token factor2 : new Token[]{A, M}) {
      List<Node> instructions = parseInstructions(D, bitAnd, factor2, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      assertIsBinaryOp(comp, BitAnd.class, new Factor(D), new Factor(factor2));
    }

    for (Token factor2 : new Token[]{A, M}) {
      List<Node> instructions = parseInstructions(D, bitOr, factor2, eof);

      assertTrue(instructions.get(0) instanceof CInstruction);
      CInstruction cInstruction = (CInstruction) instructions.get(0);

      Node comp = cInstruction.getComp();
      assertIsBinaryOp(comp, BitOr.class, new Factor(D), new Factor(factor2));
    }
  }


  @Test
  public void stopWhenEOF() throws Exception {
    List<Node> children = parseInstructions(eof);
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

  private void assertIsAInstruction(Node node, Token expectedToken) {
    assertTrue(node instanceof AInstruction);
    AInstruction aInstruction = (AInstruction) node;
    assertEquals(expectedToken, aInstruction.getAddress());
  }

  private void assertIsLabelDefinition(Node node, String expectedLabel) {
    assertTrue(node instanceof LabelDefinition);
    LabelDefinition LabelDef = (LabelDefinition) node;
    assertEquals(expectedLabel, LabelDef.getLabel());
  }

  private void assertIsBinaryOp(
      Node node,
      Class<? extends BinaryOperation> binaryClass,
      Factor expectedF1, Factor expectedF2) {
    assertTrue(binaryClass.isInstance(node));
    BinaryOperation binaryOperation = (BinaryOperation) node;
    assertEquals(expectedF1, binaryOperation.getFactor1());
    assertEquals(expectedF2, binaryOperation.getFactor2());
  }

  private void assertIsUnary(Node node, Class<? extends UnaryOperation> unaryClass,
      Factor expectedFactor) {
    assertTrue(unaryClass.isInstance(node));
    UnaryOperation binaryOperation = (UnaryOperation) node;
    assertEquals(expectedFactor, binaryOperation.getFactor());
  }

  private void assertIsIdentityOperation(Node node, Factor expectedFactor) {
    assertTrue(node instanceof IdentityOperation);
    IdentityOperation operation = (IdentityOperation) node;
    assertEquals(expectedFactor, operation.getFactor());
  }

  private void assertIsFactor(Node node, Token expectedToken) {
    assertTrue(node instanceof Factor);
    Factor factor = (Factor) node;
    assertEquals(expectedToken, factor.getToken());
  }
}
