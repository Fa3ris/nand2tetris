package org.nand2tetris.generator.Constructor;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class ConstructorTest {


  @Test
  public void callConstructor() throws Exception {
    String baseName = "CallConstructor";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }


  @Test
  public void compileConstructor() throws Exception {
    String baseName = "CompileConstructor";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
