package org.nand2tetris.tokenizer.factory;

import java.nio.file.Path;
import org.nand2tetris.tokenizer.CharReader;
import org.nand2tetris.tokenizer.CounterCharReader;
import org.nand2tetris.tokenizer.FileCharReader;

public class CharReaderFactory {

  public CharReader create(Path input) {
    return new CounterCharReader(new FileCharReader(input));
  }

}
