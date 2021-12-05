package org.nand2tetris.generator.factory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.nand2tetris.generator.CodeGenerator;
import org.nand2tetris.generator.CodeGeneratorWriter;
import org.nand2tetris.generator.command.Command;

public class CodeGeneratorFactory {

  public CodeGenerator create(Path output) {
    BufferedWriter writer;
    try {
      writer = Files.newBufferedWriter(output);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("cannot create generator", e);
    }
    PrintWriter printWriter = new PrintWriter(writer);
    Command command = new Command(printWriter);
    return new CodeGeneratorWriter(command);
  }

}
