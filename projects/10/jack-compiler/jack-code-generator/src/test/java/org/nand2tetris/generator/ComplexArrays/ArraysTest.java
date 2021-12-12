package org.nand2tetris.generator.ComplexArrays;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class ArraysTest {

  @Test
  public void main() throws Exception {
    String baseName = "Main";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
