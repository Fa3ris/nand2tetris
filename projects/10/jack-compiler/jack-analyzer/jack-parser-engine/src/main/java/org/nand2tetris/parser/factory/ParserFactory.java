package org.nand2tetris.parser.factory;

import org.nand2tetris.parser.Parser;
import org.nand2tetris.parser.ParserEngine;
import org.nand2tetris.tokenizer.Tokenizer;

public class ParserFactory {

  public Parser create(Tokenizer tokenizer) {
    return new ParserEngine(tokenizer);
  }

}
