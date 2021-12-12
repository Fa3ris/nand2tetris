package org.nand2tetris.generator.Array;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class ArrayTest {

  @Test
  public void arrayHandling() throws Exception {
    String baseName = "ArrayHandling";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }
}
