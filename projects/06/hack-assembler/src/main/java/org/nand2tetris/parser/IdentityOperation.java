package org.nand2tetris.parser;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

public class IdentityOperation implements Node {

  @Getter
  private final Factor factor;

  public IdentityOperation(Factor factor) {
    boolean validFactor = this.validFactor(factor);
    if (!validFactor) {
      throw new UnexpectedTokenException(factor.getToken());
    }
    this.factor = factor;
  }

  @Override
  public List<Node> getChildren() {
    return Collections.singletonList(factor);
  }

  private boolean validFactor(Factor factor) {
    /*
    A
    D
    M
    0
    1
     */
    boolean valid = false;
    switch (factor.getToken().getType()) {
      case A:
      case D:
      case M:
        valid = true;
        break;
      case INTEGER: {
          switch (factor.getToken().getValue()) {
            case "0":
            case "1":
              valid = true;
          }
      }
    }
    return valid;
  }

  @Override
  public String toString() {
    return "IdentityOperation{" +
        "factor=" + factor +
        '}';
  }
}
