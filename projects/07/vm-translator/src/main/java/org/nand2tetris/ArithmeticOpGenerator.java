package org.nand2tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArithmeticOpGenerator extends AbstractGenerator {

  private final List<String> add;
  private final List<String> sub;
  private final List<String> neg;

  public ArithmeticOpGenerator() {
    add = templateBinOp(
        "add",
        destComp(Dest.M, Comp.DPlusM) + inlineComment("*(SP - 1) = D + [*(SP - 1)]")
    );

    sub = templateBinOp("sub",
        destComp(Dest.M, Comp.MMinusD) + inlineComment("*(SP - 1) = *(SP - 1) - D"));

    neg = Collections.unmodifiableList(Arrays.asList(
        lineComment("neg"),
        loadSP(),
        destComp(Dest.A, Comp.decM) + inlineComment("A = SP - 1"),
        destComp(Dest.M, Comp.minusM) + inlineComment("*(SP - 1) = -[*(SP - 1)]")
    ));
  }

  private List<String> templateBinOp(String title, String op) {
    return Collections.unmodifiableList(Arrays.asList(
        lineComment(title),
        loadSP(),
        destComp(Dest.AM, Comp.decM) + inlineComment("A = --SP"),
        destComp(Dest.D, Comp.M) + inlineComment("D = *SP"),
        destComp(Dest.A, Comp.decA) + inlineComment("A = SP - 1"),
        op
    ));
  }

  public List<String> add() {
    return add;
  }

  public List<String> sub() {
    return sub;
  }

  public List<String> neg() { return neg; }

}
