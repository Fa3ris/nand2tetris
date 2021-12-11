package org.nand2tetris.generator;

import static org.nand2tetris.generator.Scope.FIELD;
import static org.nand2tetris.generator.Scope.STATIC;

import java.util.Arrays;
import java.util.List;
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
  private int flowControlCounter;

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
    // reset
    System.out.printf("\treset symbol table %n%s%n", symbolTable.description());
    symbolTable.resetSubroutine();
    resetFlowCounter();
    routineType = node.getRoutineType();
    routineName = node.getRoutineName();

    if ("method".equals(routineType)) {
      System.out.printf("\tdefine 'this' in symbol table of type %s%n", className);
      symbolTable.define("this", Type.resolve(className), Scope.ARGUMENT);
    }

  }

  private void resetFlowCounter() {
    flowControlCounter = 0;
  }

  private int nextFlowControlCounter() {
    return ++flowControlCounter;
  }

  @Override
  public void visit(ParameterListNode node) {
    System.out.println("visit ParameterListNode " + node + className + " " + routineType + " " + routineName);
  }

  @Override
  public void visit(SubroutineBodyNode node) {
    System.out.println("visit SubroutineBodyNode " + String.format("%s.%s", className, routineName) + " " + symbolTable.getLocalCount());
    System.out.printf("\tsymbol table state %n%s%n", symbolTable.description());
    command.function(String.format("%s.%s", className, routineName), symbolTable.getLocalCount());

    boolean isMethodBody = symbolTable.get("this") != null;
    if (isMethodBody) {
      System.out.println("\tbind 'this'");
      command.bindThis();
      return;
    }

    if ("new".equals(routineName)) {
      System.out.printf("allocate %s slots for instance of %s%n", symbolTable.getFieldCount(), className);
      command.push(Segment.CONST, symbolTable.getFieldCount());
      command.call("Memory.alloc", 1);
      command.pop(Segment.POINTER, 0);
    }

  }



  @Override
  public void visit(DoNode node) {
    System.out.println("visit DoNode " + node);
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
  }

  @Override
  public void visit(ExpressionNode node) {
    System.out.println("visit ExpressionNode " + node);
  }

  @Override
  public void visit(IfNode node) {
    System.out.println("visit IfNode " + node);
  }

  @Override
  public void visitKeyword(Token keywordConstant) {
    switch (keywordConstant.getLexeme()) {
      case "true":
        command.pushTrue();
        break;
      case "false":
        command.pushFalse();
        break;
      case "this":
        command.pushThis();
        break;
      default:
        throw new UnsupportedOperationException(keywordConstant.toString());
    }

  }

  @Override
  public void visitIfElse(Node expression, List<Node> ifStatements, List<Node> elseStatements) {
    System.out.println("visit IfElse " + expression + " " + ifStatements + " " + elseStatements);
    expression.accept(this);
    command.operation(Operation.NOT);
    int counter = nextFlowControlCounter();
    command.ifGoTo(String.format("%s.%s.%s.%s", className, routineName, "elseStart", counter));
    for (Node ifStatement : ifStatements) {
      ifStatement.accept(this);
    }
    command.goTo(String.format("%s.%s.%s.%s", className, routineName, "ifEnd", counter));
    command.label(String.format("%s.%s.%s.%s", className, routineName, "elseStart", counter));
    for (Node elseStatement : elseStatements) {
      elseStatement.accept(this);
    }
    command.label(String.format("%s.%s.%s.%s", className, routineName, "ifEnd", counter));
  }


  @Override
  public void visitIf(Node expression, List<Node> ifStatements) {
    System.out.println("visit If " + expression + " " + ifStatements);
    expression.accept(this);
    command.operation(Operation.NOT);
    int counter = nextFlowControlCounter();
    command.ifGoTo(String.format("%s.%s.%s.%s", className, routineName, "ifEnd", counter));
    for (Node ifStatement : ifStatements) {
      ifStatement.accept(this);
    }
    command.label(String.format("%s.%s.%s.%s", className, routineName, "ifEnd", counter));

  }

  @Override
  public void visitWhile(Node expression, List<Node> statements) {
    System.out.println("visit While " + expression + " " + statements);
    int counter = nextFlowControlCounter();
    command.label(String.format("%s.%s.%s.%s", className, routineName, "whileStart", counter));
    expression.accept(this);
    command.operation(Operation.NOT);
    command.ifGoTo(String.format("%s.%s.%s.%s", className, routineName, "whileEnd", counter));
    for (Node statement : statements) {
      statement.accept(this);
    }
    command.goTo(String.format("%s.%s.%s.%s", className, routineName, "whileStart", counter));
    command.label(String.format("%s.%s.%s.%s", className, routineName, "whileEnd", counter));
  }

  @Override
  public void visit(WhileNode node) {

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
  public void visitMethodCall(Token varName, Token subroutineName,
      ExpressionListNode expressionList) {
    System.out.println("visit MethodCall " + varName + " " + subroutineName + " " + expressionList);
    TableEntry entry = symbolTable.get(varName.getLexeme());
    boolean isInstanceMethodCall = entry != null;
    if (isInstanceMethodCall) {
      callInstanceMethod(entry, subroutineName, expressionList);
    } else {
      callClassFunction(varName, subroutineName, expressionList);
    }
  }

  private void callClassFunction(Token varName, Token subroutineName,
      ExpressionListNode expressionList) {
    pushArguments(expressionList);
    command.call(String.format("%s.%s", varName.getLexeme(), subroutineName.getLexeme()),
        expressionList.expressionsTotal());
  }

  private void callInstanceMethod(TableEntry entry, Token subroutineName,
      ExpressionListNode expressionList) {
    command.push(entry);
    pushArguments(expressionList);
    command.call(String.format("%s.%s", entry.getType().name(), subroutineName.getLexeme()),
        1 + expressionList.expressionsTotal()); // 'this' + arguments
  }

  private void pushArguments(ExpressionListNode expressionList) {
    expressionList.accept(this);
  }

  @Override
  public void visitFunctionCall(Token subroutineName, ExpressionListNode expressionList) {
    System.out.println("visit implicit InstanceMethodCall " + subroutineName.getLexeme() + " " + expressionList);
    command.pushThis();
    pushArguments(expressionList);
    command.call(String.format("%s.%s", className, subroutineName.getLexeme()), 1 + expressionList.expressionsTotal());
  }


  @Override
  public void visitInteger(Token integer) {
    System.out.println("visit Integer " + integer.getLexeme());
    command.push(Segment.CONST, Integer.parseInt(integer.getLexeme()));
  }

  @Override
  public void visitBinaryOperation(Node additionalTerm, Token binaryOp) {
    System.out.println("visit BinaryOperation " + binaryOp + " " + additionalTerm);
    additionalTerm.accept(this);
    binaryOp(binaryOp);
  }

  private void binaryOp(Token binaryOp) {
    System.out.println("bin op is " + binaryOp.getLexeme());
    switch (binaryOp.getLexeme()) {
      case Symbol.PLUS:
        command.operation(Operation.ADD);
        break;
      case Symbol.STAR:
        command.call("Math.multiply", 2);
        break;
      case Symbol.MINUS:
        command.operation(Operation.SUB);
        break;
      case Symbol.EQ:
        command.operation(Operation.EQ);
        break;
      case Symbol.LT:
        command.operation(Operation.LT);
        break;
      case Symbol.GT:
        command.operation(Operation.GT);
        break;
      case Symbol.AND:
        command.operation(Operation.AND);
        break;
      case Symbol.OR:
        command.operation(Operation.OR);
      default:
        throw new UnsupportedOperationException(binaryOp.toString());
    }
  }

  @Override
  public void visitUnaryExpression(Token unaryOp, Node unaryTerm) {
    System.out.println("visit UnaryExpression " + unaryOp + " " + unaryTerm);
    unaryTerm.accept(this);
    unaryOp(unaryOp);
  }

  private void unaryOp(Token unaryOp) {
    System.out.println("unaryOp is " + unaryOp);
    switch (unaryOp.getLexeme()) {
      case Symbol.MINUS:
        command.operation(Operation.NEG);
        break;
      case Symbol.NOT:
        command.operation(Operation.NOT);
        break;
      default:
        throw new UnsupportedOperationException(unaryOp.toString());
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
    command.push(entry);
  }

  @Override
  public void visitAssignment(Token varName) {
    System.out.println("visit Assignment " + varName.getLexeme());

    TableEntry entry = symbolTable.get(varName.getLexeme());
    if (entry == null) {
      throw new IllegalStateException(String.format("unresolved assignment %s", varName));
    }

    System.out.printf("\tpop to var %s defined as [%s]%n", varName.getLexeme(), entry.description());
    command.pop(entry);
  }
}
