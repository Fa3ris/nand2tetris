package org.nand2tetris.generator.test_utils;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class IOUtils {

  public static List<String> linesFromPath(Path path) throws Exception {
    try (BufferedReader br = Files.newBufferedReader(path)) {
      return br.lines().collect(Collectors.toList());
    }
  }

  public static Path siblingPath(Path path, String suffix) {
    String fileName = path.getFileName().toString();
    String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
    String resultFileName = baseName + suffix;
    return path.getParent().resolve(resultFileName);
  }

}
