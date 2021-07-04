package org.nand2tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StackOpGenerator extends AbstractGenerator {

  public List<String> push(Token segment, Token value) {
    switch (segment.getType()) {
      case CONSTANT:
        return pushConstant(value);
      case LOCAL:
      case ARGUMENT:
      case THIS:
      case THAT:
        return pushSegment(segment, value);
      case TEMP:
    }
    return Collections.emptyList();
  }

  private List<String> pushSegment(Token segmentToken, Token value) {
    String label = label(segmentToken);
    String offset = value.getLexeme();
    String segment = segmentToken.getLexeme();

    return Arrays.asList(
        lineComment(String.format("push %s %s", segment, offset).toLowerCase()),
        loadAddress(label),
        destComp(Dest.D, Comp.M) + inlineComment("D = " + label),
        loadAddress(offset),
        destComp(Dest.A, Comp.DPlusA) + inlineComment(String.format("A = %s + %s", label, offset)),
        destComp(Dest.D, Comp.M) + inlineComment(String.format("D = *(%s + %s)", label, offset)),
        loadSP(),
        destComp(Dest.AM, Comp.incM) + inlineComment("A = ++SP"),
        destComp(Dest.A, Comp.decA) + inlineComment("A = SP - 1"),
        destComp(Dest.M, Comp.D) + inlineComment(String.format("*(SP - 1) = *(%s + %s)", label, offset))
    );
  }

  private String label(Token segment) {
    String label;
    switch (segment.getType()) {
      case LOCAL:
        label = "LCL";
        break;
      case ARGUMENT:
        label = "ARG";
        break;
      case THIS:
      case THAT:
        label = segment.getLexeme();
        break;
      default:
        throw new IllegalArgumentException("cannot resolve as memory segment " + segment);
    }
    return label;
  }

  public List<String> pop(Token segment, Token value) {
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
