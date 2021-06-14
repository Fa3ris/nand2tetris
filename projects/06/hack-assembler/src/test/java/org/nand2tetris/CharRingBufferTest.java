package org.nand2tetris;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Reader;
import java.io.StringReader;
import org.junit.Test;

public class CharRingBufferTest {

  private CharRingBuffer buffer;

  private final int CAPACITY = 4;

  private Reader reader;

  @Test
  public void peek() throws Exception {
    reader = new StringReader("ab");
    buffer = new CharRingBuffer(reader, CAPACITY);
    for (int i = 0; i < 10; i++) {
      charEquals('a', buffer.peek());
    }
  }

  @Test
  public void read() throws Exception {
    reader = new StringReader("abcdefgh");
    buffer = new CharRingBuffer(reader, CAPACITY);
    charEquals('a', buffer.read());
    charEquals('b', buffer.read());
    charEquals('c', buffer.read());
    charEquals('d', buffer.read());
    charEquals('e', buffer.read());
    charEquals('f', buffer.read());
    charEquals('g', buffer.read());
    charEquals('h', buffer.read());
  }

  @Test
  public void size() throws Exception {
    String str = "abcdefgh";
    reader = new StringReader(str);
    buffer = new CharRingBuffer(reader, CAPACITY);

    assertEquals(CAPACITY, buffer.size());

    int loop = str.length() - CAPACITY;
    for (int i = 0; i < loop; i++) {
      buffer.read();
      assertEquals(CAPACITY, buffer.size());
    }

    int expectedSize = CAPACITY - 1;
    while (expectedSize >= 0) {
      buffer.read();
      assertEquals(expectedSize, buffer.size());
      expectedSize--;
    }
  }

  @Test
  public void empty() throws Exception {
    String str = "abcdefgh";
    reader = new StringReader(str);
    buffer = new CharRingBuffer(reader, CAPACITY);

    for (int i = 0; i < str.length(); i++) {
      assertFalse(buffer.isEmpty());
      buffer.read();
    }
    assertTrue(buffer.isEmpty());
  }

  @Test
  public void full() throws Exception {
    String str = "abcdefgh";
    reader = new StringReader(str);
    buffer = new CharRingBuffer(reader, CAPACITY);

    int loop = str.length() - CAPACITY;
    for (int i = 0; i < loop; i++) {
      buffer.read();
      assertTrue(buffer.isFull());
    }

    int expectedSize = CAPACITY - 1;
    while (expectedSize >= 0) {
      buffer.read();
      assertFalse(buffer.isFull());
      expectedSize--;
    }
  }

  @Test(expected = IllegalStateException.class)
  public void readException() throws Exception {
    String str = "";
    reader = new StringReader(str);
    buffer = new CharRingBuffer(reader, CAPACITY);
    buffer.read();
  }

  @Test(expected = IllegalStateException.class)
  public void peekException() throws Exception {
    String str = "";
    reader = new StringReader(str);
    buffer = new CharRingBuffer(reader, CAPACITY);
    buffer.peek();
  }

  @Test
  public void sizeEmpty() throws Exception {
    String str = "";
    reader = new StringReader(str);
    buffer = new CharRingBuffer(reader, CAPACITY);
    assertTrue(buffer.isEmpty());
    assertEquals(0, buffer.size());
  }

  private void charEquals(char expected, char actual) {
    assertEquals(String.valueOf(expected), String.valueOf(actual));
  }

}