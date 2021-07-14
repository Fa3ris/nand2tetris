package org.nand2tetris;

import java.util.Arrays;
import java.util.List;

public class FunctionOpGenerator extends AbstractGenerator {

  public List<String> declareFunction(Token functionNameToken, Token nVarsToken) {
    final String functionName = functionNameToken.getLexeme();
    final String nVars = nVarsToken.getLexeme();

    final String initLoopSuffix = "_INIT_LOOP";
    final String initEndSuffix = "_INIT_END";

    final String loopLabel = functionName + initLoopSuffix;
    final String endLabel = functionName + initEndSuffix;

    return Arrays.asList(
        lineComment(String.format("function %s %s", functionName, nVars)),
        loadAddress(nVars),
        destComp(Dest.D, Comp.A) + inlineComment("D = " + nVars),
        defineLabel(loopLabel),
        loadAddress(endLabel),
        compJmp(Comp.D, Jmp.JEQ) + inlineComment("end if pushed all local vars"),
        loadSP(),
        destComp(Dest.AM, Comp.incM) + inlineComment("A = ++SP"),
        destComp(Dest.A, Comp.decA) + inlineComment("A = SP - 1"),
        destComp(Dest.M, Comp.Zero) + inlineComment("push constant 0"),
        destComp(Dest.D, Comp.decD),
        loadAddress(loopLabel),
        compJmp(Comp.Zero, Jmp.JMP) + inlineComment("loop"),
        defineLabel(endLabel)
    );
  }
}
