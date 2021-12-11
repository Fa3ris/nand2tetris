package org.nand2tetris.parser.ast;

import java.util.List;
import org.nand2tetris.tokenizer.Token;

public interface NodeVisitor {

  void visit(ClassNode node);
  void visit(ClassVarDecNode node);
  void visit(DoNode node);
  void visit(ExpressionListNode node);
  void visit(ExpressionNode node);
  void visit(IfNode node);
  void visit(LetNode node);
  void visit(ParameterArgNode node);
  void visit(ParameterListNode node);
  void visit(ReturnNode node);
  void visit(SubroutineBodyNode node);
  void visit(SubroutineDecNode node);
  void visit(TermNode node);
  void visit(VarDecNode node);
  void visit(WhileNode node);

  void visitInteger(Token integer);

  void visitVarName(Token varName);

  void visitAssignment(Token varName);

  void visitMethodCall(Token varName, Token subroutineName, ExpressionListNode expressionList);

  void visitFunctionCall(Token subroutineName, ExpressionListNode expressionList);

  void visitUnaryExpression(Token unaryOp, Node unaryTerm);

  void visitBinaryOperation(Node additionalTerm, Token binaryOp);

  void visitIfElse(Node expression, List<Node> ifStatements, List<Node> elseStatements);

  void visitIf(Node expression, List<Node> ifStatements);

  void visitKeyword(Token keywordConstant);

  void visitWhile(Node expression, List<Node> statements);
}
