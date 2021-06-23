package org.nand2tetris.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.nand2tetris.lexer.Token;
import org.nand2tetris.lexer.TokenType;
import org.nand2tetris.parser.AInstruction;
import org.nand2tetris.parser.BinaryOperation;
import org.nand2tetris.parser.Factor;
import org.nand2tetris.parser.IdentityOperation;
import org.nand2tetris.parser.LabelDefinition;
import org.nand2tetris.parser.Node;
import org.nand2tetris.parser.UnaryOperation;

public class ParserTestHelper {

  public static final Token A = new Token(TokenType.A, "A");
  public static final Token D = new Token(TokenType.D, "D");
  public static final Token M = new Token(TokenType.M, "M");
  public static final Token zero = new Token(TokenType.INTEGER, "0");
  public static final Token one = new Token(TokenType.INTEGER, "1");
  public static final Token R0 = new Token(TokenType.IDENTIFIER, "R0");
  public static final Token openParen = new Token(TokenType.OPEN_PAREN, "(");
  public static final Token label = new Token(TokenType.IDENTIFIER, "FOO");
  public static final Token closeParen = new Token(TokenType.CLOSE_PAREN, ")");
  public static final Token MD = new Token(TokenType.MD, "MD");
  public static final Token AM = new Token(TokenType.AM, "AM");
  public static final Token AD = new Token(TokenType.AD, "AD");
  public static final Token AMD = new Token(TokenType.AMD, "AMD");
  public static final Token assign = new Token(TokenType.ASSIGN, "=");
  public static final Token plus = new Token(TokenType.PLUS, "+");
  public static final Token minus = new Token(TokenType.MINUS, "-");
  public static final Token bitAnd = new Token(TokenType.BIT_AND, "&");
  public static final Token bitOr = new Token(TokenType.BIT_OR, "|");
  public static final Token not = new Token(TokenType.NOT, "!");
  public static final Token semicolon = new Token(TokenType.SEMI_COLON, ";");
  public static final Token jeq = new Token(TokenType.JEQ, "JEQ");
  public static final Token jne = new Token(TokenType.JNE, "JNE");
  public static final Token jgt = new Token(TokenType.JGT, "JGT");
  public static final Token jge = new Token(TokenType.JGE, "JGE");
  public static final Token jlt = new Token(TokenType.JLT, "JLT");
  public static final Token jle = new Token(TokenType.JLE, "JLE");
  public static final Token jmp = new Token(TokenType.JMP, "JMP");
  public static final Token eof = new Token(TokenType.EOF, "EOF");

  public static void assertIsAInstruction(Node node, Token expectedToken) {
    assertTrue(node instanceof AInstruction);
    AInstruction aInstruction = (AInstruction) node;
    assertEquals(expectedToken, aInstruction.getAddress());
  }

  public static void assertIsLabelDefinition(Node node, String expectedLabel) {
    assertTrue(node instanceof LabelDefinition);
    LabelDefinition LabelDef = (LabelDefinition) node;
    assertEquals(expectedLabel, LabelDef.getLabel());
  }

  public static  void assertIsBinaryOp(
      Node node,
      Class<? extends BinaryOperation> binaryClass,
      Factor expectedF1, Factor expectedF2) {
    assertTrue(binaryClass.isInstance(node));
    BinaryOperation binaryOperation = (BinaryOperation) node;
    assertEquals(expectedF1, binaryOperation.getFactor1());
    assertEquals(expectedF2, binaryOperation.getFactor2());
  }

  public static void assertIsUnary(Node node, Class<? extends UnaryOperation> unaryClass,
      Factor expectedFactor) {
    assertTrue(unaryClass.isInstance(node));
    UnaryOperation binaryOperation = (UnaryOperation) node;
    assertEquals(expectedFactor, binaryOperation.getFactor());
  }

  public static void assertIsIdentityOperation(Node node, Factor expectedFactor) {
    assertTrue(node instanceof IdentityOperation);
    IdentityOperation operation = (IdentityOperation) node;
    assertEquals(expectedFactor, operation.getFactor());
  }

  public static void assertIsFactor(Node node, Token expectedToken) {
    assertTrue(node instanceof Factor);
    Factor factor = (Factor) node;
    assertEquals(expectedToken, factor.getToken());
  }
}
