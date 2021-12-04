package org.nand2tetris.parser.integration.Array;

import java.io.File;
import org.junit.Test;
import org.nand2tetris.parser.test_utils.TestUtils;

public class ArrayTest {


  @Test
  public void Main() throws Exception {
    assertEqualXML("Main");
  }

  private void assertEqualXML(String baseName) {
    TestUtils.assertASTXML(
        new File(getClass().getResource(baseName + ".jack").getFile()),
        new File(getClass().getResource(baseName + ".xml").getFile()));
  }
}
