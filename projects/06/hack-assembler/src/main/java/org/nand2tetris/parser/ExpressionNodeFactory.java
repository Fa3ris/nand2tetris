package org.nand2tetris.parser;

import org.nand2tetris.lexer.Token;

public abstract class ExpressionNodeFactory {

  public static Node binaryExpression(Token operator, Factor f1, Factor f2) {
    switch (operator.getType()) {
      case PLUS: {
        return new Plus(f1, f2);
      }
      case MINUS: {
        return new Minus(f1, f2);
      }
      case BIT_AND: {
        return  new BitAnd(f1, f2);
      }
      case BIT_OR: {
        return new BitOr(f1, f2);
      }
    }
    throw new UnexpectedTokenException(operator);
  }

  public static Node unaryExpression(Token operator, Factor f) {
    switch (operator.getType()) {
      case NOT: {
        return new Not(f);
      }
      case MINUS: {
        return new Negate(f);
      }
    }
    throw new UnexpectedTokenException(operator);
  }
}
