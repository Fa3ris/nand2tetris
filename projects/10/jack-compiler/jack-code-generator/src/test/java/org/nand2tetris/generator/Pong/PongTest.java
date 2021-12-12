package org.nand2tetris.generator.Pong;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class PongTest {

  @Test
  public void main() throws Exception {
    String baseName = "Main";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void ball() throws Exception {
    String baseName = "Ball";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void bat() throws Exception {
    String baseName = "Bat";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void pongGame() throws Exception {
    String baseName = "PongGame";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
