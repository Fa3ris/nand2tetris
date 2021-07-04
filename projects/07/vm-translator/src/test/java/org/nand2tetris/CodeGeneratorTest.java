package org.nand2tetris;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class CodeGeneratorTest {

  private List<String> instructions = new ArrayList<>();

  @Test
  public void pushConstant_size() throws Exception {
    generate("push constant 22");
    assertInstructionsSize(6);
  }

  @Test
  public void pushConstant() throws Exception {
    String value = "10";
    generate("push constant " + value);
    assertNthInstructionIs(1, "@" + value);
    assertNthInstructionIs(2, "D=A");
    assertNthInstructionIs(3, "@SP");
    assertNthInstructionIs(4, "AM=M+1");
    assertNthInstructionIs(5, "A=A-1");
    assertNthInstructionIs(6, "M=D");
  }

  @Test
  public void add_size() throws Exception {
    generate("add");
    assertInstructionsSize(5);
  }

  @Test
  public void add() throws Exception {
    generate("add");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "A=A-1");
    assertNthInstructionIs(5, "M=D+M");
  }

  @Test
  public void sub_size() throws Exception {
    generate("sub");
    assertInstructionsSize(5);
  }

  @Test
  public void sub() throws Exception {
    generate("sub");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "A=A-1");
    assertNthInstructionIs(5, "M=M-D");
  }

  @Test
  public void neg_size() throws Exception {
    generate("neg");
    assertInstructionsSize(3);
  }

  @Test
  public void neg() throws Exception {
    generate("neg");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "A=M-1");
    assertNthInstructionIs(3, "M=-M");
  }

  @Test
  public void and_size() throws Exception {
    generate("and");
    assertInstructionsSize(5);
  }

  @Test
  public void and() throws Exception {
    generate("and");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "A=A-1");
    assertNthInstructionIs(5, "M=D&M");
  }

  @Test
  public void or_size() throws Exception {
    generate("or");
    assertInstructionsSize(5);
  }

  @Test
  public void or() throws Exception {
    generate("or");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "A=A-1");
    assertNthInstructionIs(5, "M=D|M");
  }

  @Test
  public void not_size() throws Exception {
    generate("not");
    assertInstructionsSize(3);
  }

  @Test
  public void not() throws Exception {
    generate("not");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "A=M-1");
    assertNthInstructionIs(3, "M=!M");
  }

  @Test
  public void eq_size() throws Exception {
    generate("eq");
    assertInstructionsSize(12);
  }

  @Test
  public void eq() throws Exception {
    generate("eq");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "A=A-1");
    assertNthInstructionIs(5, "D=M-D");
    assertNthInstructionIs(6, "M=0");
    assertNthInstructionIs(7, "@EQ_END_0");
    assertNthInstructionIs(8, "D;JNE");
    assertNthInstructionIs(9, "@SP");
    assertNthInstructionIs(10, "A=M-1");
    assertNthInstructionIs(11, "M=-1");
    assertNthInstructionIs(12, "(EQ_END_0)");
  }

  @Test
  public void eq_eq() throws Exception {
    generate("eq eq");

    assertNthInstructionIs(7, "@EQ_END_0");
    assertNthInstructionIs(12, "(EQ_END_0)");

    assertNthInstructionIs(20, "@EQ_END_1");
    assertNthInstructionIs(25, "(EQ_END_1)");
  }

  @Test
  public void lt_size() throws Exception {
    generate("lt");
    assertInstructionsSize(12);
  }

  @Test
  public void lt() throws Exception {
    generate("lt");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "A=A-1");
    assertNthInstructionIs(5, "D=M-D");
    assertNthInstructionIs(6, "M=0");
    assertNthInstructionIs(7, "@LT_END_0");
    assertNthInstructionIs(8, "D;JGE");
    assertNthInstructionIs(9, "@SP");
    assertNthInstructionIs(10, "A=M-1");
    assertNthInstructionIs(11, "M=-1");
    assertNthInstructionIs(12, "(LT_END_0)");
  }

  @Test
  public void lt_lt() throws Exception {
    generate("lt lt");

    assertNthInstructionIs(7, "@LT_END_0");
    assertNthInstructionIs(12, "(LT_END_0)");

    assertNthInstructionIs(20, "@LT_END_1");
    assertNthInstructionIs(25, "(LT_END_1)");
  }

  @Test
  public void gt_size() throws Exception {
    generate("gt");
    assertInstructionsSize(12);
  }

  @Test
  public void gt() throws Exception {
    generate("gt");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "A=A-1");
    assertNthInstructionIs(5, "D=M-D");
    assertNthInstructionIs(6, "M=0");
    assertNthInstructionIs(7, "@GT_END_0");
    assertNthInstructionIs(8, "D;JLE");
    assertNthInstructionIs(9, "@SP");
    assertNthInstructionIs(10, "A=M-1");
    assertNthInstructionIs(11, "M=-1");
    assertNthInstructionIs(12, "(GT_END_0)");
  }

  @Test
  public void gt_gt() throws Exception {
    generate("gt gt");

    assertNthInstructionIs(7, "@GT_END_0");
    assertNthInstructionIs(12, "(GT_END_0)");

    assertNthInstructionIs(20, "@GT_END_1");
    assertNthInstructionIs(25, "(GT_END_1)");
  }

  private void printInstructions() {
    for (String instruction : instructions) {
      System.out.println(instruction);
    }
  }

  private void assertNthInstructionIs(int index, String expectedInstruction) {
    Assert.assertTrue(instructions.get(index).startsWith(expectedInstruction));
  }

  private void assertInstructionsSize(int expectedSize) {
    List<String> withoutLineComment = instructions.stream()
        .filter(value -> !value.startsWith("//")) // do not count line comments
        .collect(Collectors.toList());
    Assert.assertEquals(expectedSize, withoutLineComment.size());
  }

  private void generate(String command) throws Exception {
    generate(new StringReader(command));
  }

  private void generate(Reader reader) throws Exception {
    CharReader buffer = new CharReader(reader);
    Lexer lexer = new Lexer(buffer, new SymbolTable());
    CodeGenerator generator = new CodeGenerator(lexer);
    List<String> newInstruct;
    while (!(newInstruct = generator.nextInstructions()).isEmpty()) {
      instructions.addAll(newInstruct);
    }
    printInstructions();
  }

}