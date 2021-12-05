package org.nand2tetris.generator;

import org.nand2tetris.parser.ast.AST;

public interface CodeGenerator extends AutoCloseable {

  void generate(AST ast);
}
