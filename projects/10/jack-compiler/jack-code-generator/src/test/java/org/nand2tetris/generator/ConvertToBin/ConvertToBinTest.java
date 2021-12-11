package org.nand2tetris.generator.ConvertToBin;

import static org.nand2tetris.generator.test_utils.VMAssert.assertCorrectVm;

import java.io.File;
import java.nio.file.Path;
import org.junit.Test;

public class ConvertToBinTest {
  
  
  @Test
  public void Main() throws Exception {
    String baseName = "Main";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

}
