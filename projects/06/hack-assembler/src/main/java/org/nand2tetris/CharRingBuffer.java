package org.nand2tetris;

import java.io.IOException;
import java.io.Reader;

public class CharRingBuffer {

  private int size = 0;
  private final int capacity;
  private final char[] chars;
  // where to read
  private int head = 0;
  // where to write
  private int tail = 0;

  private boolean eof = false;
  private Reader reader;

  private static final int defaultCapacity = 8192;

  public CharRingBuffer(Reader reader, int capacity) {
    this.reader = reader;
    this.capacity = capacity;
    chars = new char[capacity];
    fillBuffer();
  }

  public CharRingBuffer(Reader reader) {
    this(reader, defaultCapacity);
  }

  public int size() {
    return size;
  }

  public boolean isFull() {
    return size >= capacity;
  }

  public boolean isEmpty() {
    return size <= 0;
  }

  private void fillBuffer() {
    int nCharRead;
    try {
      nCharRead = reader.read(chars);
    } catch (IOException e) {
      throw new FileReadingException(e);
    }
    if (nCharRead < 0) {
      size = 0;
    } else {
      size = nCharRead;
    }
    boolean bufferNotFilled = nCharRead < capacity;
    if (bufferNotFilled) {
      eof = true;
    }
  }

  public char read() {
    char cRead = read0();
    if (!eof) {
      write0();
    }
    return cRead;
  }

  public char peek() {
    return readChar();
  }

  private char read0() {
    char cRead = readChar();
    size--;
    head++;
    boolean headOverflow = head == capacity;
    if (headOverflow) {
      head = 0;
    }
    return cRead;
  }

  private char readChar() {
    if (isEmpty()) {
      throw new IllegalStateException("queue is empty: " + size);
    }
    return chars[head];
  }

  private int charFromReader() {
    try {
      return reader.read();
    } catch (IOException e) {
      throw new FileReadingException(e);
    }
  }

  private void write0() {
    if (isFull()) {
      throw new IllegalStateException("queue is full: " + size);
    }
    int charRead = charFromReader();
    boolean streamEnd = charRead < 0;
    if (streamEnd) {
      eof = true;
    } else {
      chars[tail] = (char) charRead;
      size++;
      tail++;
      boolean tailOverflow = tail == capacity;
      if (tailOverflow) {
        tail = 0;
      }
    }
  }

}
