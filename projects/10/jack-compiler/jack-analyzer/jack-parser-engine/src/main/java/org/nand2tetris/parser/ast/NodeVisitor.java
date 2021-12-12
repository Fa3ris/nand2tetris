package org.nand2tetris.parser.ast;

import org.nand2tetris.tokenizer.Token;

public interface NodeVisitor {

  void visit(ClassNode node);
  void visit(ClassVarDecNode node);

  void visit(DoNode node);
  void visit(IfNode node);
  void visit(LetNode node);
  void visit(ReturnNode node);
  void visit(WhileNode node);

  void visit(SubroutineDecNode node);
  void visit(ParameterArgNode node);
  void visit(SubroutineBodyNode node);
  void visit(VarDecNode node);

  void visitInteger(Token integer);
  void visitKeyword(Token keywordConstant);

  void visitVarName(Token varName);

  void visitMethodOrFunctionCall(Token varName, Token subroutineName, ExpressionListNode expressionList);

  void visitThisMethodCall(Token subroutineName, ExpressionListNode expressionList);

  void visitUnaryExpression(Token unaryOp, Node unaryTerm);

  void visitBinaryOperation(Node additionalTerm, Token binaryOp);

  void visitStringConstant(Token stringConstant);

  void visitIndexExpression(Token varName, Node indexExpression);
}
