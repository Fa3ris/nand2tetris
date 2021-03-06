package org.nand2tetris;

public abstract class AbstractGenerator {

  protected enum Dest {
    A("A"),
    D("D"),
    M("M"),
    AM("AM"),
    ;

    final String value;

    Dest(String value) {
      this.value = value;
    }
  }

  protected enum Comp {
    A("A"),
    D("D"),
    M("M"),
    decA("A-1"),
    DPlusM("D+M"),
    MMinusD("M-D"),
    incM("M+1"),
    decM("M-1"),
    minusM("-M"),
    Zero("0"),
    minusOne("-1"),
    DAndM("D&M"),
    DOrM("D|M"),
    notM("!M"),
    DPlusA("D+A"),
    decD("D-1"),
    DMinusA("D-A"),
    incA("A+1"),
    ;

    final String value;

    Comp(String value) {
      this.value = value;
    }
  }

  protected enum Jmp {
    JNE("JNE"),
    JLE("JLE"),
    JGE("JGE"),
    JMP("JMP"),
    JEQ("JEQ"),
    ;

    final String value;

    Jmp(String value) {
      this.value = value;
    }
  }

  protected final String GLOBAL_SCOPE = "GLOBAL";

  protected final String destComp(Dest dest, Comp comp) {
    return dest.value + "=" + comp.value;
  }

  protected final String compJmp(Comp comp, Jmp jmp) {
    return comp.value + ";" + jmp.value;
  }

  protected final String destCompJmp(Dest dest, Comp comp, Jmp jmp) {
    return dest.value + "=" + comp.value + ";" + jmp.value;
  }

  protected final String inlineComment(String s) {
    return "\t" + lineComment(s);
  }

  protected final String lineComment(String s) {
    return "// " + s;
  }

  protected final String loadSP() {
    return loadAddress("SP");
  }

  protected final String loadR13() { return loadRegister("13"); }

  protected final String loadR14() { return loadRegister("14"); }

  private String loadRegister(String val) {
    return loadAddress("R" + val);
  }

  protected final String LCL = "LCL";
  protected final String THIS = "THIS";
  protected final String THAT = "THAT";
  protected final String ARG = "ARG";

  protected final String loadLocal() {
    return loadAddress("LCL");
  }

  protected final String loadArgument() {
    return loadAddress("ARG");
  }

  protected final String loadThis() {
    return loadAddress("THIS");
  }

  protected final String loadThat() {
    return loadAddress("THAT");
  }

  protected final String loadAddress(String address) {
    return "@" + address;
  }

  protected final String defineLabel(String label) {
    return "(" + label + ")";
  }
}
