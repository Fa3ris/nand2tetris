package org.nand2tetris.generator.utils;

import org.junit.Assert;
import org.junit.Test;

public class StringConstantUtilsTest {

  @Test
  public void asciiTest() throws Exception {
    char[] chars = StringConstantUtils.toCharsArray("How many numbers? ");
    char[] expected = new char[] {72, 111, 119, 32, 109, 97, 110, 121, 32, 110, 117, 109, 98, 101, 114, 115, 63, 32};
    Assert.assertArrayEquals(expected, chars);
  }
}