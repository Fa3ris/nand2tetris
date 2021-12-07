package org.nand2tetris.generator;

import static org.nand2tetris.generator.Scope.FIELD;
import static org.nand2tetris.generator.Scope.STATIC;

import java.util.Arrays;
import org.nand2tetris.generator.command.Command;
import org.nand2tetris.generator.symbol_table.SymbolTable;
import org.nand2tetris.generator.symbol_table.TableEntry;
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

  private int expressionListCount;
  public CodeGeneratorWriter(Command command) {
    this.command = command;
  }

  private SymbolTable symbolTable;

  @Override
  public void generate(AST ast) {
    symbolTable = new SymbolTable();
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
    System.out.println("visit ClassVarDecNode " + node);
    Scope scope = Scope.toScope(node.getScope());

    Type type = Type.resolve(node.getType());

    if (scope != FIELD && scope != STATIC) {
      throw new IllegalStateException(String.format("expected scope %s to be of type %s", scope,
          Arrays.toString(new Scope[]{FIELD, STATIC})));
    }

    for (String name : node.getNames()) {
      System.out.printf("\tregister class var %s of type %s and scope %s %n", name, type.name(), scope);
      symbolTable.define(name, type, scope);
    }

  }

  @Override
  public void visit(SubroutineDecNode node) {
    System.out.println("visit SubroutineDecNode " + className + " " + node.getRoutineType() + " " + node.getRoutineName());
    routineType = node.getRoutineType();
    routineName = node.getRoutineName();

    // reset
    System.out.printf("\treset symbol table %n%s%n", symbolTable.description());
    symbolTable.resetSubroutine();
  }

  @Override
  public void visit(ParameterListNode node) {
    System.out.println("visit ParameterListNode " + className + " " + routineType + " " + routineName);


  }

  @Override
  public void visit(SubroutineBodyNode node) {
    System.out.println("visit SubroutineBodyNode " + String.format("%s.%s", className, routineName) + " " + symbolTable.getLocalCount());
    System.out.printf("\tsymbol table state %n%s%n", symbolTable.description());
    command.function(String.format("%s.%s", className, routineName), symbolTable.getLocalCount());

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
    System.out.println("visit LetNode " + node);
  }

  @Override
  public void visit(ParameterArgNode node) {
    System.out.println("visit ParameterArgNode " + node);

    Type type = Type.resolve(node.getType());

    String name = node.getName();
    System.out.printf("\tregister argument var %s of type %s%n", name, type.name());
    symbolTable.define(name, type, Scope.ARGUMENT);
  }

  @Override
  public void visit(TermNode node) {
    System.out.println("visit TermNode " + node);
  }

  @Override
  public void visit(VarDecNode node) {
    System.out.println("visit VarDecNode " + node);

    Type type = Type.resolve(node.getType());

    for (String name : node.getNames()) {
      System.out.printf("\tregister local var %s of type %s%n", name, type.name());
      symbolTable.define(name, type, Scope.LOCAL);
    }

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
      case Symbol.MINUS:
        command.operation(Operation.SUB);
        break;
    }

  }

  @Override
  public void visitVarName(Token varName) {
    System.out.println("visit VarName " + varName.getLexeme());

    TableEntry entry = symbolTable.get(varName.getLexeme());
    if (entry == null) {
      throw new IllegalStateException(String.format("unresolved varName %s", varName));
    }

    System.out.printf("\tpush from var %s defined as [%s]%n", varName.getLexeme(), entry.description());
    command.push(Segment.resolve(entry.getScope()), entry.getIndex());
  }

  @Override
  public void visitAssignment(Token varName) {
    System.out.println("visit Assignment " + varName.getLexeme());

    TableEntry entry = symbolTable.get(varName.getLexeme());
    if (entry == null) {
      throw new IllegalStateException(String.format("unresolved assignment %s", varName));
    }

    System.out.printf("\tpop to var %s defined as [%s]%n", varName.getLexeme(), entry.description());
    command.pop(Segment.resolve(entry.getScope()), entry.getIndex());

  }
}
