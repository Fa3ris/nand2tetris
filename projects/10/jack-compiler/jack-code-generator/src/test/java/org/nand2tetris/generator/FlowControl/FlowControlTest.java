package org.nand2tetris.generator.FlowControl;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class FlowControlTest {

  @Test
  public void ifElse() throws Exception {
    String baseName = "IfElse";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void multipleIfs() throws Exception {
    String baseName = "If";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }
  
  @Test
  public void whileBlock() throws Exception {
    String baseName = "While";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
