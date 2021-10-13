package org.nand2tetris.tokenizer.tester;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.nand2tetris.tokenizer.CharReader;
import org.nand2tetris.tokenizer.FileCharReader;
import org.nand2tetris.tokenizer.FileTokenizer;
import org.nand2tetris.tokenizer.Tokenizer;

public class Main {

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("missing file path");
      System.exit(1);
    }

    Path path = Paths.get(args[0]);

    if (!isJackFile(path)) {
      System.err.println("file is not a JACK file");
      System.exit(2);
    }

    String outputFileName = getBasename(path) + outputExtension;

    Path outputPath = path.getParent().resolve(outputFileName);

    try (BufferedWriter bf = Files.newBufferedWriter(outputPath)) {
      CharReader charReader = new FileCharReader(path);
      Tokenizer tokenizer = new FileTokenizer(charReader);
      TokenToTagMapper mapper = new TokenToTagMapper();

      String parentTag = "tokens";
      bf.write( String.format("<%1$s>", parentTag));
      bf.newLine();

      while (true) {
        tokenizer.advance();
        if (!tokenizer.hasToken()) {
          break;
        }
        String tag = mapper.convertToken(tokenizer.peekToken());
        bf.write(tag);
        bf.newLine();
      }
      bf.write( String.format("</%1$s>", parentTag));
    } catch (IOException e) {
      System.err.println("IO error: " + e.getMessage());
      e.printStackTrace(System.err);
    }


  }

  private static String outputExtension = "T.xml";

  private static boolean isJackFile(Path path) {
    String fileName = path.getFileName().toString();
    String ext = getFileExtension(fileName);
    return "jack".equalsIgnoreCase(ext);
  }

  private static String getFileExtension(String fileName) {
    int lastDotIndex = fileName.lastIndexOf(".");
    return fileName.substring(lastDotIndex + 1);
  }

  private static String getBasename(Path path) {
    String fileName = path.getFileName().toString();
    int lastDotIndex = fileName.lastIndexOf(".");
    return fileName.substring(0, lastDotIndex);
  }

}
