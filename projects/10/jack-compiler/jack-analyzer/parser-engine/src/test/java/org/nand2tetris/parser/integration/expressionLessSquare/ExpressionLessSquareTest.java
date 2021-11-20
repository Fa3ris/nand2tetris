package org.nand2tetris.parser.integration.expressionLessSquare;

import java.io.File;
import org.junit.Test;
import org.nand2tetris.parser.test_utils.TestUtils;

public class ExpressionLessSquareTest {


  @Test
  public void Main() throws Exception {
    String baseName = "Main";
    TestUtils.assertASTXML(
        new File(getClass().getResource(baseName + ".jack").getFile()),
        new File(getClass().getResource(baseName + ".xml").getFile()));
  }

}
