package org.nand2tetris;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;

public class CharReaderTest {

  @Test
  public void eof() throws Exception {
    Reader r = new StringReader("");
    CharReader charReader = new CharReader(r);

    assertTrue(charReader.eof());
  }

  @Test
  public void peek() throws Exception {
    Reader r = new StringReader("ab");
    CharReader charReader = new CharReader(r);

    assertFalse(charReader.eof());
    assertEquals("a", String.valueOf(charReader.peek()));
    assertEquals("a", String.valueOf(charReader.peek()));
    assertFalse(charReader.eof());
  }


  @Test
  public void read() throws Exception {
    Reader r = new StringReader("ab");
    CharReader charReader = new CharReader(r);

    assertFalse(charReader.eof());
    assertEquals("a", String.valueOf(charReader.read()));
    assertEquals("b", String.valueOf(charReader.read()));
    assertTrue(charReader.eof());
  }
}