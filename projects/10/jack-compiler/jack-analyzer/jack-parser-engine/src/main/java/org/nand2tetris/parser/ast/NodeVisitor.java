package org.nand2tetris.parser.ast;

import org.nand2tetris.tokenizer.Token;

public interface NodeVisitor {

  void visit(ClassNode node);
  void visit(ClassVarDecNode node);
  void visit(DoNode node);
  /**
   * @deprecated
   */
  void visit(ExpressionListNode node);
  /**
   * @deprecated
   */
  void visit(ExpressionNode node);
  void visit(IfNode node);
  void visit(LetNode node);
  void visit(ParameterArgNode node);
  /**
   * @deprecated
   */
  void visit(ParameterListNode node);
  void visit(ReturnNode node);
  void visit(SubroutineBodyNode node);
  void visit(SubroutineDecNode node);
  void visit(VarDecNode node);
  void visit(WhileNode node);

  void visitInteger(Token integer);

  void visitVarName(Token varName);

  void visitMethodOrFunctionCall(Token varName, Token subroutineName, ExpressionListNode expressionList);

  void visitThisMethodCall(Token subroutineName, ExpressionListNode expressionList);

  void visitUnaryExpression(Token unaryOp, Node unaryTerm);

  void visitBinaryOperation(Node additionalTerm, Token binaryOp);

  void visitKeyword(Token keywordConstant);
}
