package org.nand2tetris.tokenizer.factory;

import org.nand2tetris.tokenizer.CharReader;
import org.nand2tetris.tokenizer.FileTokenizer;
import org.nand2tetris.tokenizer.Tokenizer;

public class TokenizerFactory {

  public Tokenizer create(CharReader charReader) {
    return new FileTokenizer(charReader);
  }

}
