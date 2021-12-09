package org.nand2tetris.parser.ast;

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
  void visit(Token token);

  void visitInteger(Token integer);

  void visitOperator(Token operator);

  void visitVarName(Token varName);

  void visitAssignment(Token varName);

  void visitUnaryOperator(Token unaryOp);

  void visitMethodCall(Token varName, Token subroutineName, ExpressionListNode expressionList);

  void visitFunctionCall(Token subroutineName, ExpressionListNode expressionList);
}
