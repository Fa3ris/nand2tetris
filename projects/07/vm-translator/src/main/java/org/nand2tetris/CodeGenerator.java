package org.nand2tetris;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

  private final Lexer lexer;

  private StackOpGenerator stackOpGenerator = new StackOpGenerator();

  private ArithmeticOpGenerator arithmeticOpGenerator = new ArithmeticOpGenerator();

  public CodeGenerator(Lexer lexer) {
    this.lexer = lexer;
  }

  public List<String> nextInstructions() throws IOException {
    Token next = lexer.next();

    switch (next.getType()) {
      case PUSH:
        return push();
      case POP:
        return stackOp();

      case ADD:
        return arithmeticOpGenerator.add();
      case SUB:
        return arithmeticOpGenerator.sub();
      case NEG:
        return arithmeticOpGenerator.neg();

      case EQ:
      case LT:
      case GT:
        return relationalOp();

      case AND:
      case OR:
      case NOT:
        return logicalOp();

      case EOF:
        return Collections.emptyList();

      default:
        throw new IllegalStateException("invalid token as operand instruction " + next);
    }
  }

  private List<String> push() throws IOException {
    Token segment = lexer.next();
    switch (segment.getType()) {
      case CONSTANT:
        Token value = lexer.next();
        return stackOpGenerator.push(segment, value);
    }
    return Collections.emptyList();
  }

  private List<String> logicalOp() {
    return Collections.emptyList();
  }

  private List<String> relationalOp() {
    return Collections.emptyList();
  }

  private List<String> stackOp() {
    return Collections.emptyList();
  }

}
