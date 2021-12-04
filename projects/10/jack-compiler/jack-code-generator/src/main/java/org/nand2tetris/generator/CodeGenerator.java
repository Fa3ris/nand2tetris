package org.nand2tetris.generator;

import org.nand2tetris.parser.ast.AST;

public interface CodeGenerator {

  void generate(AST ast);
}
