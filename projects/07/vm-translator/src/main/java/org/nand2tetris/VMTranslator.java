package org.nand2tetris;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class VMTranslator {

  public void translate(Path path) throws IOException {
    Reader reader = Files.newBufferedReader(path);
    CharReader buffer = new CharReader(reader);
    Lexer lexer = new Lexer(buffer, new SymbolTable());
    CodeGenerator generator = new CodeGenerator(lexer);
    generator.setBaseName(fileBaseName(path));

    Path outputPath = outputPath(path);
    try (BufferedWriter bw = Files.newBufferedWriter(outputPath);
        PrintWriter pw = new PrintWriter(bw)) {
      List<String> instructions;
      while (!(instructions = generator.nextInstructions()).isEmpty()) {
        for (String instruction : instructions) {
          pw.println(instruction);
        }
      }
    }

  }

  private Path outputPath(Path path) {
    String outputFile = fileBaseName(path) + ".asm";
    Path outPath = path.getParent().resolve(outputFile);
    System.out.println(outPath.toAbsolutePath());
    return outPath;
  }

  private String fileBaseName(Path path) {
    String fileName = path.getFileName().toString();
    int lastDot = fileName.lastIndexOf(".");
    return fileName.substring(0, lastDot);
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("missing file path");
      System.exit(0);
    }

    Path path = Paths.get(args[0]);
    VMTranslator translator = new VMTranslator();
    translator.translate(path);

  }

}
