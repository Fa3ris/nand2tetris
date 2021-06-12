package org.nand2tetris;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HackAssembler {

  public static void main(String[] args) throws IOException {

    if (args.length == 0) {
      System.out.println("missing file path");
      System.exit(0);
    }

    Path path = Paths.get(args[0]);
    List<String> lines = Files.readAllLines(path);

    for (String line : lines) {
      System.out.println(line);
    }
  }
}
