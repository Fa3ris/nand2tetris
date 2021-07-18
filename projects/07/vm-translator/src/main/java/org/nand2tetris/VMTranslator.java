package org.nand2tetris;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class VMTranslator {

  private final SymbolTable symbolTable = new SymbolTable();
  private final FilePathProvider filePathProvider = new FilePathProvider();

  public void translate(Path path) throws IOException {

    filePathProvider.setInputPath(path);

    Path outputPath = filePathProvider.getOutputFilePath();
    String outPathMsg = String.format("write result to %s", outputPath);
    System.out.println(outPathMsg);
    try (BufferedWriter bw = Files.newBufferedWriter(outputPath);
        PrintWriter pw = new PrintWriter(bw)) {
      List<Path> sourceFilePaths = filePathProvider.getSourceFilePaths();
      long sysVmFileCount = sourceFilePaths.stream().filter(isSysVmFile()).count();
      if (sysVmFileCount == 1) {
        CodeGenerator bootstrapGenerator = new CodeGenerator();
        System.out.println("bootstrap");
        List<String> bootstrap = bootstrapGenerator.bootstrap();
        for (String instruction : bootstrap) {
          pw.println(instruction);
        }
      }
      for (Path sourceFilePath : sourceFilePaths) {
        String processSrcPathMsg = String.format("translate file %s", sourceFilePath);
        System.out.println(processSrcPathMsg);
        try (Reader srcReader = Files.newBufferedReader(sourceFilePath)) {
          CharReader srcBuffer = new CharReader(srcReader);
          Lexer srcLexer = new Lexer(srcBuffer, symbolTable);
          CodeGenerator codeGenerator = new CodeGenerator(srcLexer);
          codeGenerator.setBaseName(fileBaseName(sourceFilePath));
          List<String> instructions;
          while (!(instructions = codeGenerator.nextInstructions()).isEmpty()) {
            for (String instruction : instructions) {
              pw.println(instruction);
            }
          }
        }
      }
    }

  }

  private Predicate<Path> isSysVmFile() {
    return path -> "Sys.vm".equalsIgnoreCase(path.getFileName().toString());
  }

  private String fileBaseName(Path path) {
    String fileName = path.getFileName().toString();
    int lastDot = fileName.lastIndexOf(".");
    return fileName.substring(0, lastDot);
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("missing file path");
      System.exit(0);
    }

    Path path = Paths.get(args[0]);
    VMTranslator translator = new VMTranslator();
    translator.translate(path);
  }

}
