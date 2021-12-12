package org.nand2tetris.generator.StringConstant;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class StringConstantTest {

  @Test
  public void createString() throws Exception {
    String baseName = "CreateString";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
