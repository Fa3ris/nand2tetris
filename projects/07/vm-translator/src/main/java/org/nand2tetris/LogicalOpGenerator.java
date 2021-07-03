package org.nand2tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * bit-wise operations
 */
public class LogicalOpGenerator extends AbstractGenerator {

  private final List<String> and;
  private final List<String> or;
  private final List<String> not;

  public LogicalOpGenerator() {
    and = templateBinOp(
        "and",
        destComp(Dest.M, Comp.DAndM) + inlineComment("*(SP - 1) = D & [*(SP - 1)]")
    );

    or = templateBinOp(
        "or",
        destComp(Dest.M, Comp.DOrM) + inlineComment("*(SP - 1) = D | [*(SP - 1)]")
    );

    not = Collections.unmodifiableList(Arrays.asList(
        lineComment("not"),
        loadSP(),
        destComp(Dest.A, Comp.decM) + inlineComment("A = SP - 1"),
        destComp(Dest.M, Comp.notM) + inlineComment("*(SP - 1) = ![*(SP - 1)]")
    ));
  }

  public List<String> and() {
    return and;
  }

  public List<String> or() {
    return or;
  }

  public List<String> not() {
    return not;
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
}
