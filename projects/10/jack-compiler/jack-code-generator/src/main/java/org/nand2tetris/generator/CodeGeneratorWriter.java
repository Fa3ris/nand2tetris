package org.nand2tetris.generator;

import static org.nand2tetris.generator.Scope.FIELD;
import static org.nand2tetris.generator.Scope.STATIC;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.generator.command.Command;
import org.nand2tetris.generator.symbol_table.SymbolTable;
import org.nand2tetris.generator.symbol_table.TableEntry;
import org.nand2tetris.generator.utils.StringConstantUtils;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.ast.ClassNode;
import org.nand2tetris.parser.ast.ClassVarDecNode;
import org.nand2tetris.parser.ast.DoNode;
import org.nand2tetris.parser.ast.ExpressionListNode;
import org.nand2tetris.parser.ast.IfNode;
import org.nand2tetris.parser.ast.LetNode;
import org.nand2tetris.parser.ast.Node;
import org.nand2tetris.parser.ast.NodeVisitor;
import org.nand2tetris.parser.ast.ParameterArgNode;
import org.nand2tetris.parser.ast.ReturnNode;
import org.nand2tetris.parser.ast.SubroutineBodyNode;
import org.nand2tetris.parser.ast.SubroutineDecNode;
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
    // reset
    System.out.printf("\treset symbol table %n%s%n", symbolTable.description());
    symbolTable.resetSubroutine();
    flowControlCounter = 0;

    routineType = node.getRoutineType();
    routineName = node.getRoutineName();
    System.out.println("visit SubroutineDecNode " + className + " " + routineType + " " + routineName);

    if (isMethodSubroutine()) {
      System.out.printf("\tdefine 'this' in symbol table of type %s%n", className);
      symbolTable.define("this", Type.resolve(className), Scope.ARGUMENT);
    }
  }

  private boolean isMethodSubroutine() {
    return "method".equals(routineType);
  }

  private boolean isConstructorSubroutine() {
    return "constructor".equals(routineType);
  }

  @Override
  public void visit(ParameterArgNode node) {
    System.out.println("visit ParameterArgNode " + node);
    Type type = Type.resolve(node.getType());
    System.out.printf("\tregister argument var %s of type %s%n",  node.getName(), type.name());
    symbolTable.define(node.getName(), type, Scope.ARGUMENT);
  }

  @Override
  public void visit(SubroutineBodyNode node) {
    System.out.println("visit SubroutineBodyNode " + String.format("%s.%s", className, routineName) + " " + symbolTable.getLocalCount());
    System.out.printf("\tsymbol table state %n%s%n", symbolTable.description());
    command.function(String.format("%s.%s", className, routineName), symbolTable.getLocalCount());
    if (isMethodSubroutine()) {
      System.out.println("\tbind 'this'");
      command.bindThis();
      return;
    }
    if (isConstructorSubroutine()) {
      allocateHeapSpace();
    }
  }

  private void allocateHeapSpace() {
    System.out.printf("allocate %s slots for instance of %s%n", symbolTable.getFieldCount(), className);
    command.push(Segment.CONST, symbolTable.getFieldCount());
    command.call("Memory.alloc", 1);
    command.pop(Segment.POINTER, 0);
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
  public void visit(DoNode node) {
    System.out.println("visit DoNode " + node);
    command.pop(Segment.TMP, 0);
  }

  @Override
  public void visit(ReturnNode node) {
    System.out.println("visit ReturnNode " + node);
    if (!node.hasExpression()) {
      command.push(Segment.CONST, 0);
    }
    command._return();
  }

  @Override
  public void visitKeyword(Token keywordConstant) {
    switch (keywordConstant.getLexeme()) {
      case "true":
        command.pushTrue();
        break;
      case "false":
      case "null":
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
  public void visit(IfNode node) {
    System.out.println("visit IfNode " + node);
    if (node.isElseBlockPresent()) {
      visitIfElse(node.getExpression(), node.getIfStatements(), node.getElseStatements());
    } else {
      visitIf(node.getExpression(), node.getIfStatements());
    }
  }

  private void visitIfElse(Node expression, List<Node> ifStatements, List<Node> elseStatements) {
    System.out.println("visit IfElse " + expression + " " + ifStatements + " " + elseStatements);
    expression.accept(this);
    command.operation(Operation.NOT);
    int counter = nextFlowControlCounter();
    command.ifGoTo(routineLabel("elseStart", counter));
    for (Node ifStatement : ifStatements) {
      ifStatement.accept(this);
    }
    command.goTo(routineLabel("ifEnd", counter));
    command.label(routineLabel("elseStart", counter));
    for (Node elseStatement : elseStatements) {
      elseStatement.accept(this);
    }
    command.label(routineLabel("ifEnd", counter));
  }

  private String routineLabel(String suffix, int counter) {
    return String.format("%s.%s.%s.%s", className, routineName, suffix, counter);
  }

  private void visitIf(Node expression, List<Node> ifStatements) {
    System.out.println("visit If " + expression + " " + ifStatements);
    expression.accept(this);
    command.operation(Operation.NOT);
    int counter = nextFlowControlCounter();
    command.ifGoTo(routineLabel("ifEnd", counter));
    for (Node ifStatement : ifStatements) {
      ifStatement.accept(this);
    }
    command.label(routineLabel("ifEnd", counter));
  }

  @Override
  public void visit(WhileNode node) {
    Node expression = node.getExpression();
    List<Node> statements = node.getStatements();
    System.out.println("visit While " + expression + " " + statements);
    int counter = nextFlowControlCounter();
    command.label(routineLabel("whileStart", counter));
    expression.accept(this);
    command.operation(Operation.NOT);
    command.ifGoTo(routineLabel("whileEnd", counter));
    for (Node statement : statements) {
      statement.accept(this);
    }
    command.goTo(routineLabel("whileStart", counter));
    command.label(routineLabel("whileEnd", counter));
  }

  @Override
  public void visit(LetNode node) {
    System.out.println("visit LetNode " + node);
    if (node.getLeftExpression().isPresent()) {
      visitVarName(node.getVarName());
      node.getLeftExpression().get().accept(this);
      command.operation(Operation.ADD);
      node.getRightExpression().accept(this);
      command.pop(Segment.TMP, 0); // save right expression
      command.pop(Segment.POINTER, 1); // set THAT to varName + left expression
      command.push(Segment.TMP, 0);
      command.pop(Segment.THAT, 0); // (varName + left expression)* = right expression
    } else {
      node.getRightExpression().accept(this);
      assignToVarName(node.getVarName());
    }
  }

  private void assignToVarName(Token varName) {
    TableEntry entry = symbolTable.get(varName.getLexeme());
    if (entry == null) {
      throw new IllegalStateException(String.format("unresolved assignment %s", varName));
    }
    System.out.print("assign to varName " + varName.getLexeme());
    System.out.printf(" -> pop to var %s defined as [%s]%n", varName.getLexeme(), entry.description());
    command.pop(entry);
  }

  @Override
  public void visitMethodOrFunctionCall(Token varName, Token subroutineName,
      ExpressionListNode expressionList) {
    System.out.println("visit visitMethodOrFunctionCall " + varName.getLexeme() + "." + subroutineName.getLexeme() + "(" + expressionList + ")");
    TableEntry entry = symbolTable.get(varName.getLexeme());
    boolean isInstanceMethodCall = entry != null;
    if (isInstanceMethodCall) {
      System.out.println("it is InstanceMethod");
      callInstanceMethod(entry, subroutineName, expressionList);
    } else {
      System.out.println("it is ClassFunction");
      callClassFunction(varName, subroutineName, expressionList);
    }
  }

  private void callInstanceMethod(TableEntry entry, Token subroutineName,
      ExpressionListNode expressionList) {
    command.push(entry);
    pushArguments(expressionList);
    command.call(String.format("%s.%s", entry.getType().name(), subroutineName.getLexeme()),
        1 + expressionList.expressionsTotal()); // 'this' + arguments
  }

  private void callClassFunction(Token varName, Token subroutineName,
      ExpressionListNode expressionList) {
    pushArguments(expressionList);
    command.call(String.format("%s.%s", varName.getLexeme(), subroutineName.getLexeme()),
        expressionList.expressionsTotal());
  }

  private void pushArguments(ExpressionListNode expressionList) {
    expressionList.accept(this);
  }

  private int nextFlowControlCounter() {
    return ++flowControlCounter;
  }

  @Override
  public void visitThisMethodCall(Token subroutineName, ExpressionListNode expressionList) {
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
    System.out.println("visit BinaryOperation " + binaryOp.getLexeme() + " " + additionalTerm);
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
      case Symbol.SLASH:
        command.call("Math.divide", 2);
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
        break;
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
  public void visitStringConstant(Token stringConstant) {
    System.out.println("visit StringConstant " + stringConstant.getLexeme());
    char[] chars = StringConstantUtils.toCharsArray(stringConstant.getLexeme());
    command.push(Segment.CONST, chars.length);
    command.call("String.new", 1);
    for (char aChar : chars) {
      command.push(Segment.CONST, aChar);
      command.call("String.appendChar", 2);
    }
  }

  @Override
  public void visitIndexExpression(Token varName, Node indexExpression) {
    System.out.println("visit IndexExpression " + varName.getLexeme() + " " + indexExpression);
    visitVarName(varName);
    indexExpression.accept(this);
    command.operation(Operation.ADD);
    command.pop(Segment.POINTER, 1);
    command.push(Segment.THAT, 0);
  }
}
