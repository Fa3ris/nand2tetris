package org.nand2tetris.toolchain;

import java.nio.file.Path;
import org.nand2tetris.generator.CodeGenerator;
import org.nand2tetris.generator.factory.CodeGeneratorFactory;
import org.nand2tetris.parser.Parser;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.factory.ParserFactory;
import org.nand2tetris.tokenizer.CharReader;
import org.nand2tetris.tokenizer.Tokenizer;
import org.nand2tetris.tokenizer.error.CharReaderErrorFormatter;
import org.nand2tetris.tokenizer.factory.CharReaderFactory;
import org.nand2tetris.tokenizer.factory.TokenizerFactory;

public class Toolchain {

  private final CharReaderFactory charReaderFactory = new CharReaderFactory();
  private final TokenizerFactory tokenizerFactory = new TokenizerFactory();
  private final ParserFactory parserFactory = new ParserFactory();
  private final CodeGeneratorFactory codeGeneratorFactory = new CodeGeneratorFactory();

  public void process(Path input, Path output) {
    System.out.printf("process%ninput %s%noutput %s%n", input.toAbsolutePath(), output.toAbsolutePath());

    CharReader charReader = charReaderFactory.create(input);
    Tokenizer tokenizer = tokenizerFactory.create(charReader);
    Parser parser = parserFactory.create(tokenizer);
    AST ast;
    try {
      ast = parser.parse();
    } catch (Exception e) {
      String charReaderMessage = CharReaderErrorFormatter.format(charReader);
      String message = String.format("%s%n%s", e.getMessage(), charReaderMessage);
      throw new RuntimeException(message, e);
    }
    try (CodeGenerator codeGenerator = codeGeneratorFactory.create(output)) {
      codeGenerator.generate(ast);
    } catch (Exception e) {
      throw new RuntimeException("cannot generate", e);
    }
  }

}
