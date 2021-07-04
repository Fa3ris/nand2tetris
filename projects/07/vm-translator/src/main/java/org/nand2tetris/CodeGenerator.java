package org.nand2tetris;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

  private final Lexer lexer;

  private final StackOpGenerator stackOpGenerator = new StackOpGenerator();

  private final ArithmeticOpGenerator arithmeticOpGenerator = new ArithmeticOpGenerator();

  private final RelationalOpGenerator relationalOpGenerator = new RelationalOpGenerator();

  private final LogicalOpGenerator logicalOpGenerator = new LogicalOpGenerator();

  public CodeGenerator(Lexer lexer) {
    this.lexer = lexer;
  }

  public List<String> nextInstructions() throws IOException {
    Token next = lexer.next();

    switch (next.getType()) {
      case PUSH:
        return push();
      case POP:
        return pop();

      case ADD:
        return arithmeticOpGenerator.add();
      case SUB:
        return arithmeticOpGenerator.sub();
      case NEG:
        return arithmeticOpGenerator.neg();

      case EQ:
        return relationalOpGenerator.eq();
      case LT:
        return relationalOpGenerator.lt();
      case GT:
        return relationalOpGenerator.gt();

      case AND:
        return logicalOpGenerator.and();
      case OR:
        return logicalOpGenerator.or();
      case NOT:
        return logicalOpGenerator.not();

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
      case LOCAL:
      case ARGUMENT:
      case THIS:
      case THAT:
      case TEMP:
      case POINTER:
      case STATIC:
        Token value = lexer.next();
        return stackOpGenerator.push(segment, value);
    }
    throw new IllegalStateException("invalid segment for push instruction " + segment);
  }

  private List<String> pop() throws IOException {
    Token segment = lexer.next();
    switch (segment.getType()) {
      case LOCAL:
      case ARGUMENT:
      case THIS:
      case THAT:
      case TEMP:
      case POINTER:
      case STATIC:
        Token value = lexer.next();
        return stackOpGenerator.pop(segment, value);

    }
    throw new IllegalStateException("invalid segment for pop instruction " + segment);
  }

}