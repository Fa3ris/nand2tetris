package org.nand2tetris.parser.ast;

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

}
