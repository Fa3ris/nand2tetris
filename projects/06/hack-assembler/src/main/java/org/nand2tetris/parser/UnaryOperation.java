package org.nand2tetris.parser;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.nand2tetris.lexer.TokenType;

public abstract class UnaryOperation implements Node {

  protected final TokenType operator;
  @Getter
  protected final Factor factor;

  public UnaryOperation(TokenType operator, Factor factor) {
    boolean valid = false;
    switch (operator) {
      case MINUS:
        valid = validateNegate(factor);
        break;
      case NOT:
        valid = validateNot(factor);
        break;
    }
    if (!valid) {
      throw new InvalidUnaryOperationFactorException(operator, factor);
    }
    this.operator = operator;
    this.factor = factor;
  }

  private boolean validateNot(Factor factor) {
    boolean valid = false;
    /*
    !A
    !D
    !M
     */
    switch (factor.getToken().getType()) {
      case A:
      case D:
      case M:
        valid = true;
    }
    return valid;
  }

  private boolean validateNegate(Factor factor) {
    boolean valid = false;
    /*
    -1
    -A
    -D
    -M
     */
    switch (factor.getToken().getType()) {
      case A:
      case D:
      case M:
        valid = true;
        break;
      case INTEGER: {
        if ("1".equals(factor.getToken().getValue())) {
          valid = true;
        }
      }
    }
    return valid;
  }

  @Override
  public String toString() {
    return "UnaryOperation{" +
        "operator=" + operator +
        ", factor=" + factor +
        '}';
  }

  @Override
  public List<Node> getChildren() {
    return Collections.singletonList(factor);
  }
}
