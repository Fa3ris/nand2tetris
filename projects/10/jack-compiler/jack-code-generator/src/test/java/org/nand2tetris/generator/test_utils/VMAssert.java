package org.nand2tetris.generator.test_utils;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.nand2tetris.generator.CodeGeneratorWriter;
import org.nand2tetris.generator.command.Command;
import org.nand2tetris.parser.ast.AST;

public class VMAssert {

  public static void assertCorrectVm(Path inputPath) throws Exception {
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

  public static Function<List<String>, List<String>> stripLineComments() {
    return list -> list.stream().map(stripLineComment().andThen(String::trim)).filter(s -> !s.isEmpty()).collect(
        Collectors.toList());
  }

  public static Function<String, String> stripLineComment() {
    return s -> {
      Matcher matcher = beforeDoubleSlash.matcher(s);
      if (matcher.find()) {
        s = matcher.group(1);
      }
      return s;
    };
  }

  public static final Pattern beforeDoubleSlash = Pattern.compile("(.*?)//");

}
