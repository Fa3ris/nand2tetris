package org.nand2tetris;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class HackAssemblerTest {

  private final static String ADD = "Add.asm";
  private final static String MAX = "Max.asm";

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void compareLines() throws Exception {

    URL addUrl = HackAssemblerTest.class.getClassLoader().getResource(ADD);
    if (addUrl == null) {
      String msg = String.format("file %s not found", ADD);
      throw new RuntimeException(msg);
    }
    Path pathToAdd = Paths.get(addUrl.toURI());
    HackAssembler assembler = new HackAssembler();
    assembler.assemble(pathToAdd);

  }

  @Test
  public void Max() throws Exception {
    URL maxUrl = HackAssemblerTest.class.getClassLoader().getResource(MAX);
    if (maxUrl == null) {
      String msg = String.format("file %s not found", ADD);
      throw new RuntimeException(msg);
    }
    Path pathToMax = Paths.get(maxUrl.toURI());
    HackAssembler assembler = new HackAssembler();
    assembler.assemble(pathToMax);
  }
}
