package org.nand2tetris.generator.symbol_table;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    assertCorrectVm(inputPath);
  }

  @Test
  public void staticTest() throws Exception {
    String baseName = "Static";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void varTest() throws Exception {
    String baseName = "Var";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }

  @Test
  public void argumentTest() throws Exception {
    String baseName = "Argument";
    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();
    assertCorrectVm(inputPath);
  }


  private void assertCorrectVm(Path inputPath) throws Exception {
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

    Path expectedVmPath = IOUtils.siblingPath(inputPath, ".vm");
    List<String> expectedLines = IOUtils.linesFromPath(expectedVmPath);

    expectedLines = stripLineComments().apply(expectedLines);

    Assert.assertEquals(expectedLines, actualLines);
  }

  private Function<List<String>, List<String>> stripLineComments() {
    return list -> list.stream().map(stripLineComment()).filter(s -> !s.isEmpty()).collect(Collectors.toList());
  }

  private Function<String, String> stripLineComment() {
    return s -> {
      Matcher matcher = beforeDoubleSlash.matcher(s);
      if (matcher.find()) {
        s = matcher.group(1).trim();
      }
      return s;
    };
  }

  private final Pattern beforeDoubleSlash = Pattern.compile("(.*?)//");
}
