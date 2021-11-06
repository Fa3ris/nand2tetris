package org.nand2tetris.parser;

import org.nand2tetris.tokenizer.Tokenizer;

public class ParserEngine implements Parser {

  public ParserEngine(Tokenizer tokenizer) {

  }

  public AST parse() {
    return new JackAST();
  }
}
