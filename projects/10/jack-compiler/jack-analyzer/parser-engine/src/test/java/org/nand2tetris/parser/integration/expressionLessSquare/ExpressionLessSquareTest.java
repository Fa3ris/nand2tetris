package org.nand2tetris.parser.integration.expressionLessSquare;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import org.junit.Test;
import org.nand2tetris.parser.Parser;
import org.nand2tetris.parser.ParserEngine;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.tokenizer.CharReader;
import org.nand2tetris.tokenizer.FileCharReader;
import org.nand2tetris.tokenizer.FileTokenizer;
import org.nand2tetris.tokenizer.Tokenizer;

public class ExpressionLessSquareTest {

  private CharReader charReader;
  private Tokenizer tokenizer;
  private Parser parser;

  @Test
  public void Main() throws Exception {
    URL url = getClass().getResource("Main.jack");
    Path path = new File(url.getFile()).toPath();

    charReader = new FileCharReader(path);
    tokenizer = new FileTokenizer(charReader);
    parser = new ParserEngine(tokenizer);

    AST ast = parser.parse();
    System.out.println(ast);
  }

}
