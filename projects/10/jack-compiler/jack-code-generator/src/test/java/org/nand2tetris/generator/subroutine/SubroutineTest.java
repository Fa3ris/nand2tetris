package org.nand2tetris.generator.subroutine;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class SubroutineTest {

  @Test
  public void function() throws Exception {
    String baseName = "Function";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
    
  }

}
