package org.nand2tetris.generator.Square;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class SquareTest {

  @Test
  public void main() throws Exception {
    String baseName = "Main";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void square() throws Exception {
    String baseName = "Square";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void squareGame() throws Exception {
    String baseName = "SquareGame";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
