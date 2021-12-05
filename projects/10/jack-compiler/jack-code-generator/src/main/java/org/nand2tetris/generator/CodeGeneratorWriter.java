package org.nand2tetris.generator;

import org.nand2tetris.generator.command.Command;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.ast.ClassNode;
import org.nand2tetris.parser.ast.ClassVarDecNode;
import org.nand2tetris.parser.ast.DoNode;
import org.nand2tetris.parser.ast.ExpressionListNode;
import org.nand2tetris.parser.ast.ExpressionNode;
import org.nand2tetris.parser.ast.IfNode;
import org.nand2tetris.parser.ast.LetNode;
import org.nand2tetris.parser.ast.Node;
import org.nand2tetris.parser.ast.NodeVisitor;
import org.nand2tetris.parser.ast.ParameterArgNode;
import org.nand2tetris.parser.ast.ParameterListNode;
import org.nand2tetris.parser.ast.ReturnNode;
import org.nand2tetris.parser.ast.SubroutineBodyNode;
import org.nand2tetris.parser.ast.SubroutineDecNode;
import org.nand2tetris.parser.ast.TermNode;
import org.nand2tetris.parser.ast.VarDecNode;
import org.nand2tetris.parser.ast.WhileNode;
import org.nand2tetris.tokenizer.Symbol;
import org.nand2tetris.tokenizer.Token;

public class CodeGeneratorWriter implements CodeGenerator, NodeVisitor {

  private final Command command;

  private String className;

  private String routineType;
  private String routineName;

  private int argumentCount;
  private int localVarCount;

  private int expressionListCount;
  public CodeGeneratorWriter(Command command) {
    this.command = command;
  }

  @Override
  public void generate(AST ast) {
    Node node = ast.getRoot();
    node.accept(this);
  }

  @Override
  public void close() throws Exception {
    this.command.close();
  }

  @Override
  public void visit(ClassNode node) {
    System.out.println("visit ClassNode " + node.getClassName());
    className = node.getClassName();
  }

  @Override
  public void visit(ClassVarDecNode node) {

  }

  @Override
  public void visit(SubroutineDecNode node) {
    System.out.println("visit SubroutineDecNode " + className + " " + node.getRoutineType() + " " + node.getRoutineName());
    routineType = node.getRoutineType();
    routineName = node.getRoutineName();

    // reset;
    argumentCount = 0;
    localVarCount = 0;
  }

  @Override
  public void visit(ParameterListNode node) {
    // TODO register arguments in symbol table
    System.out.println("visit ParameterListNode " + className + " " + routineType + " " + routineName);

  }

  @Override
  public void visit(SubroutineBodyNode node) {
    System.out.println("visit SubroutineBodyNode " + String.format("%s.%s", className, routineName) + " " + localVarCount);
    command.function(String.format("%s.%s", className, routineName), localVarCount);

  }



  @Override
  public void visit(DoNode node) {

    String identifier = node.getIdentifier().orElse("");

    String routineName = node.getSubRoutineName();

    StringBuilder sb = new StringBuilder();
    if (!identifier.isEmpty()) {
      sb.append(identifier).append(".");
    }

    sb.append(routineName);

    System.out.printf("visit DoNode %s %s%n", sb, expressionListCount);

    command.call(sb.toString(), expressionListCount);
    // do not care about result
    command.pop(Segment.TMP, 0);
  }

  @Override
  public void visit(ReturnNode node) {
    String particle = node.hasExpression() ? "with" : "without";
    System.out.println("visit ReturnNode " + particle + " expression");
    if (!node.hasExpression()) {
      command.push(Segment.CONST, 0);
    }
    command._return();
  }

  @Override
  public void visit(ExpressionListNode node) {
    System.out.println("visit ExpressionListNode total = " + node.expressionsTotal());
    expressionListCount = node.expressionsTotal();
    // TODO compute expressions and put in stack and store number of expressions

  }

  @Override
  public void visit(ExpressionNode node) {
    System.out.println("visit ExpressionNode " + node);
  }

  @Override
  public void visit(IfNode node) {

  }

  @Override
  public void visit(LetNode node) {

  }

  @Override
  public void visit(ParameterArgNode node) {

  }

  @Override
  public void visit(TermNode node) {
    System.out.println("visit TermNode " + node);
  }

  @Override
  public void visit(VarDecNode node) {
    // TODO increment varCount

  }

  @Override
  public void visit(WhileNode node) {

  }

  @Override
  public void visit(Token token) {
    System.out.println("visit Token " + token);
  }

  @Override
  public void visitInteger(String integer) {
    System.out.println("visit Integer " + integer);
    command.push(Segment.CONST, Integer.parseInt(integer));
  }

  @Override
  public void visitOperator(String operator) {
    System.out.println("visit Operator " + operator);

    switch (operator) {
      case Symbol.PLUS:
        command.operation(Operation.ADD);
        break;
        case Symbol.STAR:
          command.call("Math.multiply", 2);
          break;
    }

  }
}
