package org.nand2tetris.parser;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.nand2tetris.lexer.TokenType;


@Getter
public abstract class BinaryOperation implements Node {

  protected final TokenType operator;
  protected final Factor factor1;
  protected final Factor factor2;

  public BinaryOperation(TokenType operator, Factor factor1, Factor factor2) {
    boolean valid = false;
    switch (operator) {
      case BIT_AND:
      case BIT_OR: {
        valid = validLogicalFactors(factor1, factor2);
        break;
      }
      case PLUS: {
        valid = validPlus(factor1, factor2);
        break;
      }
      case MINUS: {
        valid = validMinus(factor1, factor2);
        break;
      }
    }
    if (!valid) {
      throw new InvalidBinaryOperationFactorException(operator, factor1, factor2);
    }
    this.operator = operator;
    this.factor1 = factor1;
    this.factor2 = factor2;
  }

  private boolean validPlus(Factor f1, Factor f2) {
    TokenType type1 = f1.getToken().getType();
    TokenType type2 = f2.getToken().getType();

    boolean valid = false;
    if (type1 == TokenType.D) {
      /*
      D+A
      D+M
      D+1
       */
      switch (type2) {
        case A:
        case M:
          valid = true;
          break;
        case INTEGER: {
          if ("1".equals(f2.getToken().getValue())) {
            valid = true;
          }
        }
      }
    }

    if (type1 == TokenType.A || type1 == TokenType.M) {
      /*
      A+1
      M+1
       */
      boolean f2IsConstantOne = type2 == TokenType.INTEGER
          && "1".equals(f2.getToken().getValue());
      if (f2IsConstantOne) {
          valid = true;
      }
    }

    return valid;
  }

  private boolean validMinus(Factor f1, Factor f2) {
    TokenType type1 = f1.getToken().getType();
    TokenType type2 = f2.getToken().getType();

    boolean valid = false;
    if (type1 == TokenType.D) {
      /*
      D-A
      D-M
      D-1
       */
      switch (type2) {
        case A:
        case M:
          valid = true;
          break;
        case INTEGER: {
          if ("1".equals(f2.getToken().getValue())) {
            valid = true;
            break;
          }
        }
      }
    }

    if (type1 == TokenType.A || type1 == TokenType.M) {
      /*
      A-D
      A-1

      M-D
      M-1
       */
      switch (type2) {
        case D:
          valid = true;
          break;
        case INTEGER:
          if ("1".equals(f2.getToken().getValue())) {
            valid = true;
          }
      }
    }
    return valid;
  }

  private boolean validLogicalFactors(Factor factor1, Factor factor2) {
    /*
    D|A
    D|M
    D&A
    D&M
     */
    boolean factor1IsD = TokenType.D == factor1.getToken().getType();
    TokenType factor2Type = factor2.getToken().getType();
    boolean factor2IsAOrM = TokenType.A == factor2Type || TokenType.M == factor2Type;
    return factor1IsD && factor2IsAOrM;
  }

  @Override
  public List<Node> getChildren() {
    return Arrays.asList(factor1, factor2);
  }

  @Override
  public String toString() {
    return "BinaryOperation{" +
        "operator=" + operator +
        ", factor1=" + factor1 +
        ", factor2=" + factor2 +
        '}';
  }
}
