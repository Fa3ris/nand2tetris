package org.nand2tetris.toolchain;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("missing file path");
      System.exit(1);
    }

    final Path inputPath = Paths.get(args[0]).toAbsolutePath();

    System.out.println("input path is " + inputPath.toAbsolutePath());

    if (Files.isDirectory(inputPath)) {
      System.out.println("compile directory");
      try (Stream<Path> pathStream = Files.list(inputPath)) {
        pathStream
            .filter(isJackFile())
            .forEach(processJackFile());
      }
    }

    if (Files.isRegularFile(inputPath) && isJackFile().test(inputPath)) {
      processJackFile().accept(inputPath);
    }
  }

  private static Consumer<Path> processJackFile() {
    return inputPath -> new Toolchain().process(inputPath, outputVMPath(inputPath));
  }

  /**
   * .vm path from .jack path in same directory
   */
  private static Path outputVMPath(Path inputPath) {
    String fileName = inputPath.getFileName().toString();
    String baseName = fileName.substring(0, fileName.lastIndexOf("."));
    String outputFileName = baseName + ".vm";
    return inputPath.getParent().resolve(outputFileName);
  }


  private static Predicate<Path> isJackFile() {
    return path -> {
      String fileName = path.getFileName().toString();
      String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
      return "jack".equalsIgnoreCase(ext);
    };
  }

}
