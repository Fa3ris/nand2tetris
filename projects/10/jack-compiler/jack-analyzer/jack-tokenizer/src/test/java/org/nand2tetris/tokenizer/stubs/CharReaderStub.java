package org.nand2tetris.tokenizer.stubs;


import java.util.LinkedList;
import java.util.Queue;
import org.nand2tetris.tokenizer.CharReader;

public class CharReaderStub implements CharReader {

  Queue<Character> queue;

  public CharReaderStub(String stream) {
    queue = new LinkedList<>();
    if (stream == null || stream.isEmpty()) {
      return;
    }
    queue.add('\0'); // for 1st advance
    for (int i = 0; i < stream.length(); i++) {
      queue.add(stream.charAt(i));
    }
  }

  @Override
  public void advance() {
    queue.remove();
  }

  @Override
  public char peekChar() {
    return queue.element();
  }

  @Override
  public boolean isEOF() {
    return queue.isEmpty();
  }
}
