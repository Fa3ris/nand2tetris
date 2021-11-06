package org.nand2tetris.parser.stubs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.nand2tetris.tokenizer.Token;
import org.nand2tetris.tokenizer.Tokenizer;

public class TokenizerStub implements Tokenizer {

  private Queue<Token> queue;

  public TokenizerStub(List<Token> tokens) {
    this.queue = new LinkedList<>(tokens);
  }

  @Override
  public void advance() {

  }

  @Override
  public Token peekToken() {
    return null;
  }

  @Override
  public boolean hasToken() {
    return false;
  }
}
