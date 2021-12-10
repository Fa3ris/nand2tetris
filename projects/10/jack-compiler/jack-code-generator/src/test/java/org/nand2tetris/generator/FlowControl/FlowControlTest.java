package org.nand2tetris.generator.FlowControl;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Ignore;
import org.junit.Test;

public class FlowControlTest {

  @Test
  public void ifElse() throws Exception {
    String baseName = "IfElse";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  @Ignore
  public void multipleIfs() throws Exception {
    String baseName = "If";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
