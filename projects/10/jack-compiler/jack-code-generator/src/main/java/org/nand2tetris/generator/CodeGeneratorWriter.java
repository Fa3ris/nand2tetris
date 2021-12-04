package org.nand2tetris.generator;

import java.io.PrintWriter;
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

public class CodeGeneratorWriter implements CodeGenerator, NodeVisitor, AutoCloseable {

  private final Command command;

  public CodeGeneratorWriter(PrintWriter writer) {
    command = new Command(writer);
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

  }

  @Override
  public void visit(ClassVarDecNode node) {

  }

  @Override
  public void visit(DoNode node) {

  }

  @Override
  public void visit(ExpressionListNode node) {

  }

  @Override
  public void visit(ExpressionNode node) {

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
  public void visit(ParameterListNode node) {

  }

  @Override
  public void visit(ReturnNode node) {

  }

  @Override
  public void visit(SubroutineBodyNode node) {

  }

  @Override
  public void visit(SubroutineDecNode node) {

  }

  @Override
  public void visit(TermNode node) {

  }

  @Override
  public void visit(VarDecNode node) {

  }

  @Override
  public void visit(WhileNode node) {

  }


}
