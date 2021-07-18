package org.nand2tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BranchingOpGenerator extends AbstractGenerator {

  private String scope = GLOBAL_SCOPE;

  public void setScope(String scope) {
    this.scope = scope;
  }

  public void resetScope() {
    scope = GLOBAL_SCOPE;
  }

  public List<String> label(Token labelToken) {
    String label = labelToken.getLexeme();
    return Collections.unmodifiableList(Arrays.asList(
        lineComment("label " + label),
        defineLabel(scopedLabel(label))
    ));
  }

  public List<String> goTo(Token labelToken) {
    String label = labelToken.getLexeme();
    return Collections.unmodifiableList(Arrays.asList(
        lineComment("goto " + label),
        loadAddress(scopedLabel(label)),
        compJmp(Comp.Zero, Jmp.JMP) + inlineComment("jump to " + label)
    ));
  }

  public List<String> ifGoto(Token labelToken) {
    String label = labelToken.getLexeme();
    return Collections.unmodifiableList(Arrays.asList(
        lineComment("if-goto " + label),
        loadSP(),
        destComp(Dest.AM, Comp.decM) + inlineComment("A = --SP"),
        destComp(Dest.D, Comp.M) + inlineComment("D = *SP"),
        loadAddress(scopedLabel(label)),
        compJmp(Comp.D, Jmp.JNE) + inlineComment("if D then jump to " + label)
    ));
  }

  private String scopedLabel(String label) {
    return String.format("%s$%s", scope, label);
  }
}
