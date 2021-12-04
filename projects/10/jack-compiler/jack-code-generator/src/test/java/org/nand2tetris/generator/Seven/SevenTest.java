package org.nand2tetris.generator.Seven;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.nand2tetris.generator.CodeGeneratorWriter;
import org.nand2tetris.generator.test_utils.IOUtils;
import org.nand2tetris.generator.test_utils.ParserUtils;
import org.nand2tetris.generator.test_utils.XMLPrinter;
import org.nand2tetris.parser.ast.AST;

public class SevenTest {

  @Test
  public void Main() throws Exception {

    String baseName = "Main";

    Path inputPath = new File(getClass().getResource(baseName + ".jack").getFile()).toPath();

    AST ast = ParserUtils.parse(inputPath);

    Path astXmlPath = IOUtils.siblingPath(inputPath, "-actual.xml");
    XMLPrinter.printXml(astXmlPath, ast.toXMLString());

    Path actualVmPath = IOUtils.siblingPath(inputPath, "-actual.vm");

    try (BufferedWriter writer = Files.newBufferedWriter(actualVmPath)) {
      CodeGeneratorWriter generator = new CodeGeneratorWriter(writer);
      generator.generate(ast);
    }

    List<String> actualLines = IOUtils.linesFromPath(actualVmPath);

    Path expectedVmPath = new File(getClass().getResource(baseName + ".vm").getFile()).toPath();
    List<String> expectedLines = IOUtils.linesFromPath(expectedVmPath);

    Assert.assertEquals(expectedLines, actualLines);
  }

}