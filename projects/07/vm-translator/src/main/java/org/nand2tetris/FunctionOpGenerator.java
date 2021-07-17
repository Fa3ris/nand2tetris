package org.nand2tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionOpGenerator extends AbstractGenerator {

  private final String[] restorationOrder = new String[] {THAT, THIS, ARG, LCL};
  private final String[] saveOrder = new String[] {LCL, ARG, THIS, THAT};

  private String function = GLOBAL_SCOPE;
  private int call = 0;

  public List<String> declareFunction(Token functionNameToken, Token nVarsToken) {
    call = 0; // reset calls
    final String functionName = functionNameToken.getLexeme();
    function = functionName;
    final String nVars = nVarsToken.getLexeme();

    final String initLoopSuffix = "_INIT_LOOP";
    final String initEndSuffix = "_INIT_END";

    final String loopLabel = functionName + initLoopSuffix;
    final String endLabel = functionName + initEndSuffix;

    return Arrays.asList(
        lineComment(String.format("function %s %s", functionName, nVars)),
        defineLabel(functionName),
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

  public List<String> returnCommand() {
    function = GLOBAL_SCOPE;
    List<String> instructions = new ArrayList<>(Arrays.asList(
        lineComment("return"),
        lineComment("save endFrame"),
        loadLocal(),
        destComp(Dest.D, Comp.M) + inlineComment("D = LCL"),
        loadR13(),
        destComp(Dest.M, Comp.D) + inlineComment("endFrame = LCL"),
        lineComment("save return address"),
        loadAddress("5"),
        destComp(Dest.A, Comp.DMinusA) + inlineComment("A = LCL - 5"),
        destComp(Dest.D, Comp.M) + inlineComment("D = *(LCL - 5)"),
        loadR14(),
        destComp(Dest.M, Comp.D) + inlineComment("retAddr = *(LCL - 5)"),
        lineComment("put return value at ARG[0]"),
        loadSP(),
        destComp(Dest.AM, Comp.decM) + inlineComment("A = --SP"),
        destComp(Dest.D, Comp.M) + inlineComment("D = *SP"),
        loadArgument(),
        destComp(Dest.A, Comp.M) + inlineComment("A = ARG"),
        destComp(Dest.M, Comp.D) + inlineComment("*ARG = *SP"),
        lineComment("restore SP"),
        destComp(Dest.D, Comp.incA) + inlineComment("D = ARG + 1"),
        loadSP(),
        destComp(Dest.M, Comp.D) + inlineComment("SP = ARG + 1")
    ));

    for (String pointerLabel: restorationOrder) {
      instructions.addAll(restorePointer(pointerLabel));
    }

    instructions.addAll(
        Arrays.asList(
            lineComment("goto return address"),
            loadR14(),
            destComp(Dest.A, Comp.M) + inlineComment("A = retAddr"),
            compJmp(Comp.Zero, Jmp.JMP) + inlineComment( " goto retAddr")
        )
    );
    return instructions;
  }

  private List<String> restorePointer(String pointerLabel) {
    return Arrays.asList(
        lineComment("restore " + pointerLabel),
        loadR13(),
        destComp(Dest.AM, Comp.decM) + inlineComment("A = --endFrame"),
        destComp(Dest.D, Comp.M) + inlineComment("D = *endFrame"),
        loadAddress(pointerLabel),
        destComp(Dest.M, Comp.D) + inlineComment(pointerLabel + " = *endFrame")
    );
  }

  public List<String> callFunction(Token functionName, Token nArgs) {
    String returnAddressLabel = String.format("%s$ret.%s", function, call++);
    String nArgsLexeme = nArgs.getLexeme();
    String functionNameLabel = functionName.getLexeme();
    List<String> instructions = new ArrayList<>(Arrays.asList(
        lineComment(String.format("call %s %s", functionNameLabel, nArgsLexeme)),
        lineComment("save " + returnAddressLabel),
        loadAddress(returnAddressLabel),
        destComp(Dest.D, Comp.A),
        loadSP(),
        destComp(Dest.AM, Comp.incM) + inlineComment("A = ++SP"),
        destComp(Dest.A, Comp.decA) + inlineComment("A = SP - 1"),
        destComp(Dest.M, Comp.D) + inlineComment(String.format("*(SP - 1) = %s", returnAddressLabel))
    ));

    for (String pointerLabel: saveOrder) {
      instructions.addAll(saveAddress(pointerLabel));
    }

    int frameOffset = 5 + Integer.parseInt(nArgsLexeme);
    instructions.addAll(Arrays.asList(
        loadSP(),
        destComp(Dest.D, Comp.M) + inlineComment("D = SP"),
        loadLocal(),
        destComp(Dest.M, Comp.D) + inlineComment("LCL = SP"),
        loadAddress(String.valueOf(frameOffset)),
        destComp(Dest.D, Comp.DMinusA) + inlineComment(String.format("D = SP - (5 + %s)", nArgsLexeme)),
        loadArgument(),
        destComp(Dest.M, Comp.D) + inlineComment(String.format("ARG = SP - (5 + %s)", nArgsLexeme)),
        loadAddress(functionNameLabel),
        compJmp(Comp.Zero, Jmp.JMP) + inlineComment("goto " + functionNameLabel),
        defineLabel(returnAddressLabel)
    ));

    return instructions;
  }

  private List<String> saveAddress(String address) {
    return Arrays.asList(
        lineComment("save " + address),
        loadAddress(address),
        destComp(Dest.D, Comp.M),
        loadSP(),
        destComp(Dest.AM, Comp.incM) + inlineComment("A = ++SP"),
        destComp(Dest.A, Comp.decA) + inlineComment("A = SP - 1"),
        destComp(Dest.M, Comp.D) + inlineComment(String.format("*(SP - 1) = %s", address))
    );
  }
}
