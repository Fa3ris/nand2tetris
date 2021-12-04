package org.nand2tetris.parser.stubs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.Tokenizer;

public class TokenizerStub implements Tokenizer {

  private final Queue<Token> queue;

  public TokenizerStub(List<Token> tokens) {
    this.queue = new LinkedList<>();
    if (tokens == null || tokens.isEmpty()) {
      return;
    }
    queue.add(null); // for 1st advance
    queue.addAll(tokens);
  }

  @Override
  public void advance() {
    queue.remove();
  }

  @Override
  public Token peekToken() {
    return queue.element();
  }

  @Override
  public boolean hasToken() {
    return !queue.isEmpty();
  }
}
