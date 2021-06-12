package org.nand2tetris;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Test;

public class HackAssemblerTest {

  private final static String ADD = "Add.asm";

  @Test
  public void compareLines() throws Exception {
    URL addUrl = HackAssemblerTest.class.getClassLoader().getResource(ADD);
    if (addUrl == null) {
      String msg = String.format("file %s not found", ADD);
      throw new RuntimeException(msg);
    }
    Path pathToAdd = Paths.get(addUrl.toURI());
    List<String> lines = Files.readAllLines(pathToAdd);

    assertEquals(13, lines.size());

    String firstLine = "// This file is part of www.nand2tetris.org";
    String actualFirst = lines.get(0);
    assertEquals(firstLine, actualFirst);

    String lastLine = "M=D";
    String actualLast = lines.get(lines.size() - 1);
    assertEquals(lastLine, actualLast);
  }
}
