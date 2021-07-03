package org.nand2tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RelationalOpGenerator extends AbstractGenerator {

  private int eqCounter = -1;
  private int gtCounter = -1;
  private int ltCounter = -1;

  public List<String> eq() {
    eqCounter++;
    return template(
        "eq " + eqCounter,
        "EQ_END_" + eqCounter,
        compJmp(Comp.D, Jmp.JNE) + inlineComment("if D != 0, it is really false, finish"));
  }

  public List<String> gt() {
    gtCounter++;
    return template(
        "gt " + gtCounter,
        "GT_END_" + gtCounter,
        compJmp(Comp.D, Jmp.JLE) + inlineComment("if D <= 0, it is really false, finish"));
  }

  public List<String> lt() {
    ltCounter++;
    return template(
        "lt " + ltCounter,
        "LT_END_" + ltCounter,
        compJmp(Comp.D, Jmp.JGE ) + inlineComment("if D >= 0, it is really false, finish"));
  }

  private List<String> template(String title, String label, String jumpCondition) {
    return Collections.unmodifiableList(Arrays.asList(
        lineComment(title),
        loadSP(),
        destComp(Dest.AM, Comp.decM) + inlineComment("A = --SP"),
        destComp(Dest.D, Comp.M) + inlineComment("D = *SP"),
        destComp(Dest.A, Comp.decA) + inlineComment("A = SP - 1"),
        destComp(Dest.D, Comp.MMinusD) + inlineComment("D = *(SP - 1) - D"),
        destComp(Dest.M, Comp.Zero) + inlineComment("*(SP - 1) = false, assume it is false"),
        loadAddress(label),
        jumpCondition,
        loadSP(),
        destComp(Dest.A, Comp.decM),
        destComp(Dest.M, Comp.minusOne) + inlineComment("else set true"),
        defineLabel(label)
    ));
  }
}
