package org.nand2tetris;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class CodeGeneratorTest {

  private List<String> instructions = new ArrayList<>();

  private boolean removeLineComments = false;

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

  @Test
  public void pushSegment_size() throws Exception {
    String[] segments = new String[]{"local", "argument", "this", "that"};
    for (String segment : segments) {
      generate(String.format("push %s 10", segment));
      assertInstructionsSize(9);
      instructions.clear();
    }
  }

  @Test
  public void pushSegment_Label() throws Exception {
    Map<String, String> segmentToLabel = new HashMap<>();
    segmentToLabel.put("local", "LCL");
    segmentToLabel.put("argument", "ARG");
    segmentToLabel.put("this", "THIS");
    segmentToLabel.put("that", "THAT");

    for (Entry<String, String> entry: segmentToLabel.entrySet()) {
      generate(String.format("push %s 10", entry.getKey()));
      assertNthInstructionIs(1, "@" + entry.getValue());
      instructions.clear();
    }
  }

  @Test
  public void pushSegment_offset() throws Exception {
    String value = "22";
    generate(String.format("push local %s", value));
    assertNthInstructionIs(3, "@" + value);
  }

  @Test
  public void pushSegment() throws Exception {
    String value = "99";
    generate(String.format("push local %s", value));
    assertNthInstructionIs(1, "@LCL");
    assertNthInstructionIs(2, "D=M");
    assertNthInstructionIs(3, "@" + value);
    assertNthInstructionIs(4, "A=D+A");
    assertNthInstructionIs(5, "D=M");
    assertNthInstructionIs(6, "@SP");
    assertNthInstructionIs(7, "AM=M+1");
    assertNthInstructionIs(8, "A=A-1");
    assertNthInstructionIs(9, "M=D");
  }

  @Test
  public void popSegment_size() throws Exception {
    String[] segments = new String[]{"local", "argument", "this", "that"};
    for (String segment : segments) {
      generate(String.format("pop %s 86", segment));
      assertInstructionsSize(12);
      instructions.clear();
    }
  }

  @Test
  public void popSegment_offset() throws Exception {
    String value = "22";
    generate(String.format("pop local %s", value));
    assertNthInstructionIs(3, "@" + value);
  }

  @Test
  public void popSegment_Label() throws Exception {
    Map<String, String> segmentToLabel = new HashMap<>();
    segmentToLabel.put("local", "LCL");
    segmentToLabel.put("argument", "ARG");
    segmentToLabel.put("this", "THIS");
    segmentToLabel.put("that", "THAT");

    for (Entry<String, String> entry: segmentToLabel.entrySet()) {
      generate(String.format("pop %s 10", entry.getKey()));
      assertNthInstructionIs(1, "@" + entry.getValue());
      instructions.clear();
    }
  }

  @Test
  public void popSegment() throws Exception {
    String value = "99";
    generate(String.format("pop local %s", value));
    assertNthInstructionIs(1, "@LCL");
    assertNthInstructionIs(2, "D=M");
    assertNthInstructionIs(3, "@" + value);
    assertNthInstructionIs(4, "D=D+A");
    assertNthInstructionIs(5, "@R13");
    assertNthInstructionIs(6, "M=D");
    assertNthInstructionIs(7, "@SP");
    assertNthInstructionIs(8, "AM=M-1");
    assertNthInstructionIs(9, "D=M");
    assertNthInstructionIs(10, "@R13");
    assertNthInstructionIs(11, "A=M");
    assertNthInstructionIs(12, "M=D");
  }

  @Test
  public void pushTemp_size() throws Exception {
    generate("push temp 3");
    assertInstructionsSize(6);
  }

  @Test
  public void pushTemp() throws Exception {
    int value = 4;
    generate(String.format("push temp %s", value));
    int expectedAddress = 5 + value;
    assertNthInstructionIs(1, "@" + expectedAddress);
    assertNthInstructionIs(2, "D=M");
    assertNthInstructionIs(3, "@SP");
    assertNthInstructionIs(4, "AM=M+1");
    assertNthInstructionIs(5, "A=A-1");
    assertNthInstructionIs(6, "M=D");
  }

  @Test
  public void popTemp_size() throws Exception {
    generate("pop temp 3");
    assertInstructionsSize(5);
  }

  @Test
  public void popTemp() throws Exception {
    int value = 4;
    generate(String.format("pop temp %s", value));
    int expectedAddress = 5 + value;
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "@" + expectedAddress);
    assertNthInstructionIs(5, "M=D");
  }

  @Test
  public void pushPointer_size() throws Exception {
    generate("push pointer 0");
    assertInstructionsSize(6);
  }

  @Test
  public void pushPointer_label() throws Exception {
    Map<String, String> segmentToLabel = new HashMap<>();
    segmentToLabel.put("0", "THIS");
    segmentToLabel.put("1", "THAT");

    for (Entry<String, String> entry: segmentToLabel.entrySet()) {
      generate(String.format("push pointer %s", entry.getKey()));
      assertNthInstructionIs(1, "@" + entry.getValue());
      instructions.clear();
    }
  }

  @Test
  public void pushPointer() throws Exception {
    generate("push pointer 0");
    assertNthInstructionIs(1, "@THIS");
    assertNthInstructionIs(2, "D=M");
    assertNthInstructionIs(3, "@SP");
    assertNthInstructionIs(4, "AM=M+1");
    assertNthInstructionIs(5, "A=A-1");
    assertNthInstructionIs(6, "M=D");
  }

  @Test
  public void popPointer_size() throws Exception {
    generate("pop pointer 0");
    assertInstructionsSize(5);
  }

  @Test
  public void popPointer_label() throws Exception {
    Map<String, String> segmentToLabel = new HashMap<>();
    segmentToLabel.put("0", "THIS");
    segmentToLabel.put("1", "THAT");

    for (Entry<String, String> entry: segmentToLabel.entrySet()) {
      generate(String.format("pop pointer %s", entry.getKey()));
      assertNthInstructionIs(4, "@" + entry.getValue());
      instructions.clear();
    }
  }

  @Test
  public void popPointer() throws Exception {
    generate("pop pointer 0");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "@THIS");
    assertNthInstructionIs(5, "M=D");
  }

  @Test
  public void pushStatic_size() throws Exception {
    generate("push static 0");
    assertInstructionsSize(6);
  }

  @Test
  public void pushStatic_label() throws Exception {
    String fileName = "Foo";
    String value = "0";
    generate("push static " + value, fileName);
    assertNthInstructionIs(1, String.format("@%s.%s", fileName, value));
  }

  @Test
  public void pushStatic() throws Exception {
    generate("push static 1", "Bar");
    assertNthInstructionIs(1, "@Bar.1");
    assertNthInstructionIs(2, "D=M");
    assertNthInstructionIs(3, "@SP");
    assertNthInstructionIs(4, "AM=M+1");
    assertNthInstructionIs(5, "A=A-1");
    assertNthInstructionIs(6, "M=D");
  }

  @Test
  public void popStatic_size() throws Exception {
    generate("pop static 0");
    assertInstructionsSize(5);
  }

  @Test
  public void popStatic_label() throws Exception {
    String fileName = "Foo";
    String value = "0";
    generate("pop static " + value, fileName);
    assertNthInstructionIs(4, String.format("@%s.%s", fileName, value));
  }

  @Test
  public void popStatic() throws Exception {
    generate("pop static 10", "Baz");
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "@Baz.10");
    assertNthInstructionIs(5, "M=D");
  }

  @Test
  public void label_size() throws Exception {
    generate("label FOO");
    assertInstructionsSize(1);
  }

  @Test
  public void label() throws Exception {
    String label = "BAR";
    generate("label " + label);
    assertNthInstructionIs(1, "(GLOBAL$" + label + ")");
  }

  @Test
  public void goto_size() throws Exception {
    generate("goto MAIN_LOOP_START");
    assertInstructionsSize(2);
  }

  @Test
  public void goTo() throws Exception {
    String label = "BAR";
    generate("goto " + label);
    assertNthInstructionIs(1, "@GLOBAL$" + label);
    assertNthInstructionIs(2, "0;JMP");
  }

  @Test
  public void if_goto_size() throws Exception {
    generate("if-goto LOOP_START");
    assertInstructionsSize(5);
  }

  @Test
  public void if_goto() throws Exception {
    String label = "BAR";
    generate("if-goto " + label);
    assertNthInstructionIs(1, "@SP");
    assertNthInstructionIs(2, "AM=M-1");
    assertNthInstructionIs(3, "D=M");
    assertNthInstructionIs(4, "@GLOBAL$" + label);
    assertNthInstructionIs(5, "D;JNE");
  }

  @Test
  public void function_size() throws Exception {
    String functionName = "SimpleFunction.test";
    String nVars = "2";
    generate(String.format("function %s %s", functionName, nVars));
    assertInstructionsSize(14);
  }

  @Test
  public void function_instructions() throws Exception {
    removeLineComments = true;
    String functionName = "SimpleFunction.foo";
    String nVars = "5";
    generate(String.format("function %s %s", functionName, nVars));
    assertNthInstructionIs(0, String.format("(%s)", functionName));
    assertNthInstructionIs(1, "@" + nVars);
    assertNthInstructionIs(2, "D=A");
    assertNthInstructionIs(3, "(SimpleFunction.foo_INIT_LOOP)");
    assertNthInstructionIs(4, "@SimpleFunction.foo_INIT_END");
    assertNthInstructionIs(5, "D;JEQ");
    assertNthInstructionIs(6, "@SP");
    assertNthInstructionIs(7, "AM=M+1");
    assertNthInstructionIs(8, "A=A-1");
    assertNthInstructionIs(9, "M=0");
    assertNthInstructionIs(10, "D=D-1");
    assertNthInstructionIs(11, "@SimpleFunction.foo_INIT_LOOP");
    assertNthInstructionIs(12, "0;JMP");
    assertNthInstructionIs(13, "(SimpleFunction.foo_INIT_END)");
  }

  @Test
  public void return_size() throws Exception {
    generate("return");
    assertInstructionsSize(41);
  }

  @Test
  public void return_instructions() throws Exception {
    removeLineComments = true;
    generate("return");
    int i = -1;
    // save endFramce
    assertNthInstructionIs(++i, "@LCL");
    assertNthInstructionIs(++i, "D=M");
    assertNthInstructionIs(++i, "@R13");
    assertNthInstructionIs(++i, "M=D");
    // save ret addr
    assertNthInstructionIs(++i, "@5");
    assertNthInstructionIs(++i, "A=D-A");
    assertNthInstructionIs(++i, "D=M");
    assertNthInstructionIs(++i, "@R14");
    assertNthInstructionIs(++i, "M=D");
    // *ARG = pop()
    assertNthInstructionIs(++i, "@SP");
    assertNthInstructionIs(++i, "AM=M-1");
    assertNthInstructionIs(++i, "D=M");
    assertNthInstructionIs(++i, "@ARG");
    assertNthInstructionIs(++i, "A=M");
    assertNthInstructionIs(++i, "M=D");
    // SP = ARG + 1
    assertNthInstructionIs(++i, "D=A+1");
    assertNthInstructionIs(++i, "@SP");
    assertNthInstructionIs(++i, "M=D");
    // restore segment pointers
    for (String label: new String[] {"THAT", "THIS", "ARG", "LCL"}) {
        assertNthInstructionIs(++i, "@R13");
        assertNthInstructionIs(++i, "AM=M-1");
        assertNthInstructionIs(++i, "D=M");
        assertNthInstructionIs(++i, "@" + label);
        assertNthInstructionIs(++i, "M=D");
    }
    // goto ret address
    assertNthInstructionIs(++i, "@R14");
    assertNthInstructionIs(++i, "A=M");
    assertNthInstructionIs(++i, "0;JMP");
  }

  @Test
  public void call_size() throws Exception {
    removeLineComments = true;
    generate("call Sys.add12 1");
    assertInstructionsSize(41);
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
    CodeGenerator generator = generator(new StringReader(command));
    generateInstructions(generator);
  }

  private void generate(String command, String staticFileName) throws Exception {
    CodeGenerator generator = generator(new StringReader(command));
    generator.setBaseName(staticFileName);
    generateInstructions(generator);
  }

  private CodeGenerator generator(Reader reader) throws Exception {
    CharReader buffer = new CharReader(reader);
    Lexer lexer = new Lexer(buffer, new SymbolTable());
    CodeGenerator generator = new CodeGenerator(lexer);
    return generator;
  }

  private void generateInstructions(CodeGenerator generator) throws Exception {
    List<String> newInstruct;
    while (!(newInstruct = generator.nextInstructions()).isEmpty()) {
      instructions.addAll(newInstruct);
    }
    if (removeLineComments) {
      removeInstructionsLineComment();
    }
    printInstructions();
  }

  private void removeInstructionsLineComment() {
    instructions = instructions.stream()
        .filter(value -> !value.startsWith("//")) // do not count line comments
        .collect(Collectors.toList());
  }
}