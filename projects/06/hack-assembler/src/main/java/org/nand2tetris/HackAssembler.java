package org.nand2tetris;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.nand2tetris.code.CodeGenerator;
import org.nand2tetris.lexer.Lexer;
import org.nand2tetris.lexer.TokenStream;
import org.nand2tetris.parser.AST;
import org.nand2tetris.parser.Parser;
import org.nand2tetris.parser.SymbolTable;
import org.nand2tetris.parser.resolver.Resolver;

public class HackAssembler {

  private final org.nand2tetris.parser.Parser parser = new Parser();
  private final Resolver resolver = new Resolver();

  public void assemble(Path path) throws IOException {
    Reader reader = Files.newBufferedReader(path);
    CharBuffer buffer = new CharRingBuffer(reader);
    TokenStream lexer = new Lexer(buffer);
    AST ast = parser.parse(lexer);
    SymbolTable table = resolver.resolveSymbols(ast);
    CodeGenerator generator = new CodeGenerator(ast, table);

    Path outputPath = outputPath(path);
    try (BufferedWriter bw = Files.newBufferedWriter(outputPath);
        PrintWriter pw = new PrintWriter(bw)) {
      while (generator.hasNextCode()) {
        pw.println(generator.nextCode());
      }
    }
  }

  private Path outputPath(Path path) {
    String fileName = path.getFileName().toString();
    int lastDot = fileName.lastIndexOf(".");
    String outputFile = fileName.substring(0, lastDot) + ".hack";
    System.out.println(outputFile);
    return path.getParent().resolve(outputFile);
  }

  public static void main(String[] args) throws IOException {

    if (args.length == 0) {
      System.out.println("missing file path");
      System.exit(0);
    }

    Path path = Paths.get(args[0]);
    HackAssembler hackAssembler = new HackAssembler();
    hackAssembler.assemble(path);
  }
}
