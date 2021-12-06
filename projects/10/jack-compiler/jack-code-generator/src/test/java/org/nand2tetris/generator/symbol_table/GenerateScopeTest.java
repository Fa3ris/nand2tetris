package org.nand2tetris.generator.symbol_table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.nand2tetris.generator.CodeGeneratorWriter;
import org.nand2tetris.generator.command.Command;
import org.nand2tetris.generator.test_utils.IOUtils;
import org.nand2tetris.generator.test_utils.ParserUtils;
import org.nand2tetris.generator.test_utils.XMLPrinter;
import org.nand2tetris.parser.ast.AST;

public class GenerateScopeTest {


  @Test
  public void field() throws Exception {

    String baseName = "Field";

    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();

    AST ast = ParserUtils.parse(inputPath);

    Path astXmlPath = IOUtils.siblingPath(inputPath, "-actual.xml");
    XMLPrinter.printXml(astXmlPath, ast.toXMLString());

    Path actualVmPath = IOUtils.siblingPath(inputPath, "-actual.vm");

    try (BufferedWriter writer = Files.newBufferedWriter(actualVmPath);
        PrintWriter printWriter = new PrintWriter(writer);
        Command command = new Command(printWriter);
        CodeGeneratorWriter generator = new CodeGeneratorWriter(command)) {
      generator.generate(ast);
    }

    List<String> actualLines = IOUtils.linesFromPath(actualVmPath);

    Path expectedVmPath = new File(getClass().getResource(baseName + ".vm").getFile()).toPath();
    List<String> expectedLines = IOUtils.linesFromPath(expectedVmPath);

    expectedLines = stripLineComments().apply(expectedLines);

    Assert.assertEquals(expectedLines, actualLines);

  }

  private Function<List<String>, List<String>> stripLineComments() {
    return list -> list.stream().map(stripLineComment()).collect(Collectors.toList());
  }

  private Function<String, String> stripLineComment() {
    return s -> {
      int commentStart = s.indexOf("//");
      if (commentStart > -1) {
        s = s.substring(0, commentStart).trim();
      }
      return s;
    };
  }
}
