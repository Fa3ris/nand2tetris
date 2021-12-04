package org.nand2tetris.generator;

import java.io.Writer;
import org.nand2tetris.parser.ast.AST;

public class CodeGeneratorWriter implements CodeGenerator {

  private final Writer writer;

  public CodeGeneratorWriter(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void generate(AST ast) {

  }
}
