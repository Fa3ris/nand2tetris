package org.nand2tetris.generator.test_utils;

import java.nio.file.Path;
import org.nand2tetris.parser.Parser;
import org.nand2tetris.parser.ParserEngine;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.tokenizer.CharReader;
import org.nand2tetris.tokenizer.CounterCharReader;
import org.nand2tetris.tokenizer.FileCharReader;
import org.nand2tetris.tokenizer.FileTokenizer;
import org.nand2tetris.tokenizer.Tokenizer;

public class ParserUtils {

  public static AST parse(Path path) {
    CharReader charReader = new FileCharReader(path);
    CounterCharReader counterCharReader = new CounterCharReader(charReader);
    Tokenizer tokenizer = new FileTokenizer(counterCharReader);
    Parser parser = new ParserEngine(tokenizer);

    AST ast;
    try {
      ast = parser.parse();
    } catch (Exception e) {
      String msg = String.format("parse error at line %s column %s\n%s",
          counterCharReader.getLineNumber() + 1,
          counterCharReader.getColumnNumber() + 1,
          counterCharReader.getLineContent());
      throw new RuntimeException(msg, e);
    }
    return ast;
  }

}
