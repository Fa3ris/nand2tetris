package org.nand2tetris.parser.integration.Square;

import java.io.File;
import org.junit.Ignore;
import org.junit.Test;
import org.nand2tetris.parser.test_utils.TestUtils;

public class SquareTest {


  @Test
  public void Main() throws Exception {
    assertEqualXML("Main");
  }

  @Test
  public void SquareGame() throws Exception {
    assertEqualXML("SquareGame");
  }

  @Test
  public void Square() throws Exception {
    assertEqualXML("Square");
  }

  private void assertEqualXML(String baseName) {
    TestUtils.assertASTXML(
        new File(getClass().getResource(baseName + ".jack").getFile()),
        new File(getClass().getResource(baseName + ".xml").getFile()));
  }
}
