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
import lombok.Setter;
import org.nand2tetris.command_line.CommandLine;
import org.nand2tetris.command_line.CommandLineParser;
import org.nand2tetris.command_line.Options;
import org.nand2tetris.command_line.OptionsFactory;

public class VMTranslator {

  private final SymbolTable symbolTable = new SymbolTable();
  private final FilePathProvider filePathProvider = new FilePathProvider();
  private final CodeGenerator codeGenerator = new CodeGenerator();

  @Setter
  private boolean verbose = false;
  @Setter
  private boolean comments = false;
  @Setter
  private int optimization = 0;

  public void translate(Path path) throws IOException {

    filePathProvider.setInputPath(path);

    Path outputPath = filePathProvider.getOutputFilePath();
    if (verbose) {
      String outPathMsg = String.format("write result to %s", outputPath);
      System.out.println(outPathMsg);
    }
    try (BufferedWriter bw = Files.newBufferedWriter(outputPath);
        PrintWriter pw = new PrintWriter(bw)) {
      List<Path> sourceFilePaths = filePathProvider.getSourceFilePaths();
      long sysVmFileCount = sourceFilePaths.stream().filter(isSysVmFile()).count();
      if (sysVmFileCount == 1) {
        if (verbose) {
          System.out.println("bootstrap");
        }
        List<String> bootstrap = codeGenerator.bootstrap();
        for (String instruction : bootstrap) {
          pw.println(instruction);
        }
      }
      for (Path sourceFilePath : sourceFilePaths) {
        if (verbose) {
          String processSrcPathMsg = String.format("translate file %s", sourceFilePath);
          System.out.println(processSrcPathMsg);
        }
        try (Reader srcReader = Files.newBufferedReader(sourceFilePath)) {
          CharReader srcBuffer = new CharReader(srcReader);
          Lexer srcLexer = new Lexer(srcBuffer, symbolTable);
          codeGenerator.setLexer(srcLexer);
          String fileBaseName = fileBaseName(sourceFilePath);
          codeGenerator.setBaseName(fileBaseName);
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

    Options options = new OptionsFactory().getDefaultOptions();
    CommandLineParser parser = new CommandLineParser(options);

    CommandLine commandLine = parser.parseLine(args);

    if (commandLine.getArgs().length < 1) {
      System.err.println("missing file path");
      System.exit(1);
    }

    String pathArg = commandLine.getArgs()[0];

    VMTranslator translator = new VMTranslator();
    boolean comment = commentFlag(commandLine);
    translator.setComments(comment);
    boolean verbose = verboseFlag(commandLine);
    translator.setVerbose(verbose);
    int optimization = optimizationLevel(commandLine);
    translator.setOptimization(optimization);

    if (verbose) {
      commandLine.print();
      System.out.println("file path is " + pathArg);
      System.out.println("verbose flag present: will print messages during translations");
      if (comment) {
        System.out.println("comment flag present: will print comments in file");
      }
      System.out.println("optimization level is " + optimization);
    }
    Path path = Paths.get(pathArg);
    translator.translate(path);
  }

  private static boolean commentFlag(CommandLine commandLine) {
    boolean res = commandLine.hasOption("comment");
    if (res) {

    }
    return res;
  }

  private static boolean verboseFlag(CommandLine commandLine) {
    boolean res = commandLine.hasOption("verbose");
    return res;
  }

  private static int optimizationLevel(CommandLine commandLine) {
    int res = 0;
    if (commandLine.hasOption("optimization")) {
      res = Integer.parseInt(commandLine.getOptionValue("optimization"));
    }
    return res;
  }

}
