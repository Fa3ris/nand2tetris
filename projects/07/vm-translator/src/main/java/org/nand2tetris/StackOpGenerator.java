package org.nand2tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StackOpGenerator extends AbstractGenerator {

  public List<String> push(Token segment, Token value) {
    switch (segment.getType()) {
      case CONSTANT:
        return pushConstant(value);
    }
    return Collections.emptyList();
  }

  private List<String> pushConstant(Token value) {
    String i = value.getLexeme();

    return Arrays.asList(
        lineComment("push constant " + i),
        loadAddress(i),
        destComp(Dest.D, Comp.A) + inlineComment("D = " + i),
        loadSP(),
        destComp(Dest.AM, Comp.incM) + inlineComment("A = ++SP"),
        destComp(Dest.A, Comp.decA) + inlineComment("A = SP - 1"),
        destComp(Dest.M, Comp.D) + inlineComment("*(SP - 1) = " + i)
    );
  }
}
