package org.nand2tetris;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePathProvider {

  private Path outputFilePath;

  private List<Path> sourceFilePaths = Collections.emptyList();

  public void setInputPath(Path inputPath) throws IOException {
    String ASM_EXT = ".asm";

    if (Files.isDirectory(inputPath)) {
      outputFilePath = inputPath.resolve(inputPath.getFileName() + ASM_EXT);
      try (Stream<Path> pathStream = Files.list(inputPath)) {
        sourceFilePaths = pathStream.filter(isVmPath()).collect(Collectors.toList());
      }
    }

    if (Files.isRegularFile(inputPath)) {
      sourceFilePaths = Collections.singletonList(inputPath);
      String fileName = inputPath.getFileName().toString();
      int lastDotIndex = fileName.lastIndexOf(".");
      String baseName =  fileName.substring(0, lastDotIndex);
      String outputFileName = baseName + ASM_EXT;
      outputFilePath = inputPath.getParent().resolve(outputFileName);
    }
  }

  private Predicate<Path> isVmPath() {
    return path -> {
      String fileName = path.getFileName().toString();
      int lastDotIndex = fileName.lastIndexOf(".");
      String ext =  fileName.substring(lastDotIndex + 1);
      return "vm".equalsIgnoreCase(ext);
    };
  }

  public Path getOutputFilePath() {
    return outputFilePath;
  }

  public List<Path> getSourceFilePaths() {
    return sourceFilePaths;
  }
}
