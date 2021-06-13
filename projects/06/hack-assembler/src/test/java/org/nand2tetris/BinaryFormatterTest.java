package org.nand2tetris;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryFormatterTest {

  @Test
  public void leadingZeros() throws Exception {
    // special case of 0
    assertEquals(32, BinaryFormatter.numberOfLeadingZeros(0));

    assertEquals(31, BinaryFormatter.numberOfLeadingZeros(1));

    assertEquals(28, BinaryFormatter.numberOfLeadingZeros(0b1010));

    assertEquals(16, BinaryFormatter.numberOfLeadingZeros(0b0000_0000_0000_0000_1000_0000_0000_0000));

    assertEquals(0, BinaryFormatter.numberOfLeadingZeros(0b1111_1111_1111_1111_1111_1111_1111_1111));
  }

  @Test
  public void toBinaryChars() throws Exception {
    // special case of 0
    assertEquals("0", new String(BinaryFormatter.toBinaryChars(0b0)));

    assertEquals("1", new String(BinaryFormatter.toBinaryChars(0b1)));

    // discard leading zero
    assertEquals("101", new String(BinaryFormatter.toBinaryChars(0b00101)));

    assertEquals("10101", new String(BinaryFormatter.toBinaryChars(0b10101)));
    assertEquals("11111", new String(BinaryFormatter.toBinaryChars(0b11111)));
  }

  @Test
  public void toBinaryString() throws Exception {
    // zero
    assertEquals("0000000", BinaryFormatter.toBinaryString(0b0, 7));
    assertEquals("0000000000", BinaryFormatter.toBinaryString(0b0, 10));

    // one
    assertEquals("0000000001", BinaryFormatter.toBinaryString(0b1, 10));

    assertEquals("00101", BinaryFormatter.toBinaryString(0b0101, 5));

    // no padding necessary
    assertEquals("10101", BinaryFormatter.toBinaryString(0b10101, 5));

  }

  @Test
  public void toBinaryStringNoTruncation() throws Exception {
    assertEquals("11101", BinaryFormatter.toBinaryString(0b11101, 3));
    assertEquals("11111", BinaryFormatter.toBinaryString(0b11111, 2));
  }
}