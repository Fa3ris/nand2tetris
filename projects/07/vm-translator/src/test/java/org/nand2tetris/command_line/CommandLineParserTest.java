package org.nand2tetris.command_line;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandLineParserTest {

  private final CommandLineParser parser = new CommandLineParser();

  private String srcFilePathOperand = "foo.vm";

  private String[] args;

  @Test
  public void parseSrcInput() throws Exception {
    args = new String[] {srcFilePathOperand};
    parse();
    String actualPath = parser.srcInputPath();
    assertEquals(srcFilePathOperand, actualPath);
  }

  @Test
  public void parseVerboseFlag() throws Exception {
    args = new String[]{"-v"};
    parse();
    assertTrue(parser.verbose());

    args = new String[]{"--verbose"};
    parse();
    assertTrue(parser.verbose());

    args = new String[]{};
    parse();
    assertFalse(parser.verbose());
  }

  @Test
  public void parseCommentFlag() throws Exception {
    args = new String[]{};
    parse();
    assertEquals(CommentLevel.ALL, parser.commentLevel());

    String none = "none";
    String functionOnly = "function-only";
    String all = "all";

    args = new String[]{"-c", none};
    parse();
    assertEquals(CommentLevel.NONE, parser.commentLevel());

    args = new String[]{"-c", functionOnly};
    parse();
    assertEquals(CommentLevel.FUNCTION_ONLY, parser.commentLevel());

    args = new String[]{"-c", all};
    parse();
    assertEquals(CommentLevel.ALL, parser.commentLevel());

    args = new String[]{"--comment", none};
    parse();
    assertEquals(CommentLevel.NONE, parser.commentLevel());

    args = new String[]{"--comment", functionOnly};
    parse();
    assertEquals(CommentLevel.FUNCTION_ONLY, parser.commentLevel());

    args = new String[]{"--comment", all};
    parse();
    assertEquals(CommentLevel.ALL, parser.commentLevel());
  }

  @Test
  public void parseOptimizationOption() throws Exception {
    String level1 = "1";
    args = new String[]{"-o", level1};
    parse();
    assertEquals(Integer.parseInt(level1), parser.optimizationLevel());

    String level2 = "2";
    args = new String[]{"-o", level2};
    parse();
    assertEquals(Integer.parseInt(level2), parser.optimizationLevel());

    args = new String[]{"--optimization", level1};
    parse();
    assertEquals(Integer.parseInt(level1), parser.optimizationLevel());

    args = new String[]{"--optimization", level2};
    parse();
    assertEquals(Integer.parseInt(level2), parser.optimizationLevel());
  }

  @Test
  public void parseHelpOption() throws Exception {
    args = new String[]{"-h"};
    parse();
    assertTrue(parser.help());

    args = new String[]{};
    parse();
    assertFalse(parser.help());

    args = new String[]{"--help"};
    parse();
    assertTrue(parser.help());
  }

  @Test
  public void multipleOptions() throws Exception {
    args = new String[]{"-c", "none", "--optimization", "5", "test/my-folder/Main.vm"};
    parse();

    assertEquals(CommentLevel.NONE, parser.commentLevel());
    assertEquals(5, parser.optimizationLevel());
    assertEquals("test/my-folder/Main.vm", parser.srcInputPath());

    assertFalse(parser.help());
    assertFalse(parser.verbose());
  }

  private void parse() {
    parser.parse(args);
  }


  @Test
  public void argOnly() throws Exception {

    Options options = new Options();
    CommandLineParser parser = new CommandLineParser(options);

    String[] line = {"hello", "world"};
    CommandLine commandLine = parser.parseLine(line);
    assertArrayEquals(line, commandLine.getArgs());
  }

  @Test
  public void shortOption() throws Exception {

    Option option = Option.builder()
        .shortOpt("c")
        .optionName("compact")
        .build();
    Options options = new Options();
    options.addOption(option);

    CommandLineParser parser = new CommandLineParser(options);

    String[] line = new String[]{"-c"};
    CommandLine commandLine = parser.parseLine(line);
    assertTrue(commandLine.hasOption("compact"));
  }

  @Test
  public void longOption() throws Exception {

    Option option = Option.builder()
        .longOpt("verbose")
        .optionName("verbose")
        .build();
    Options options = new Options();
    options.addOption(option);

    CommandLineParser parser = new CommandLineParser(options);

    String[] line = new String[]{"--verbose"};
    CommandLine commandLine = parser.parseLine(line);
    assertTrue(commandLine.hasOption("verbose"));

  }

  @Test
  public void shortAndLongOption() throws Exception {

    Option option = Option.builder()
        .shortOpt("c")
        .optionName("compact")
        .build();
    Options options = new Options();
    options.addOption(option);

    option = Option.builder()
        .longOpt("verbose")
        .optionName("verbose")
        .build();
    options.addOption(option);

    CommandLineParser parser = new CommandLineParser(options);

    String[][] lines = {
        {"-c", "--verbose"},
        {"--verbose", "-c"}
    };

    for (String[] line : lines) {
      CommandLine commandLine = parser.parseLine(line);
      assertTrue(commandLine.hasOption("compact"));
      assertTrue(commandLine.hasOption("verbose"));
    }
  }


  @Test
  public void multipleFlags() throws Exception {

    Option option = Option.builder()
        .shortOpt("c")
        .optionName("compact")
        .build();
    Options options = new Options();
    options.addOption(option);

    option = Option.builder()
        .shortOpt("v")
        .optionName("verbose")
        .build();
    options.addOption(option);

    CommandLineParser parser = new CommandLineParser(options);

    String[][] lines = {
        {"-vc"},
        {"-cv"}
    };

    for (String[] line : lines) {
      CommandLine commandLine = parser.parseLine(line);
      assertTrue(commandLine.hasOption("compact"));
      assertTrue(commandLine.hasOption("verbose"));
    }
  }

  @Test
  public void singleArgOption() throws Exception {
    Option option = Option.builder()
        .shortOpt("c")
        .optionName("compact")
        .argNumber(1)
        .build();

    Options options = new Options();
    options.addOption(option);

    CommandLineParser parser = new CommandLineParser(options);

    String[] line = new String[]{"-c", "titi"};
    CommandLine commandLine = parser.parseLine(line);
    String actual = commandLine.getOptionValue("compact");
    assertEquals("titi", actual);

  }

  @Test
  public void twoArgsOption() throws Exception {
    Option option = Option.builder()
        .shortOpt("t")
        .optionName("table")
        .argNumber(2)
        .build();

    Options options = new Options();
    options.addOption(option);

    CommandLineParser parser = new CommandLineParser(options);

    String[] line = new String[]{"-t", "13", "22"};
    CommandLine commandLine = parser.parseLine(line);
    String[] actual = commandLine.getOptionValues("table");
    String[] expected = {"13", "22"};
    assertArrayEquals(expected, actual);
  }

  @Test
  public void allInOne() throws Exception {
    Option option = Option.builder()
        .shortOpt("c")
        .optionName("compact")
        .build();
    Options options = new Options();
    options.addOption(option);

    option = Option.builder()
        .longOpt("verbose")
        .optionName("verbose")
        .build();
    options.addOption(option);

    option = Option.builder()
        .shortOpt("l")
        .optionName("list")
        .argNumber(1)
        .build();
    options.addOption(option);

    option = Option.builder()
        .shortOpt("t")
        .optionName("table")
        .argNumber(2)
        .build();
    options.addOption(option);

    CommandLineParser parser = new CommandLineParser(options);

    String[] line;
    CommandLine commandLine;

    line = new String[]{"-t", "13", "22", "--verbose", "-c", "-l", "test", "arg1", "arg2", "arg3"};
    commandLine = parser.parseLine(line);

    String[] actualTable = commandLine.getOptionValues("table");
    String[] expectedTable = {"13", "22"};
    assertArrayEquals(expectedTable, actualTable);

    assertTrue(commandLine.hasOption("compact"));
    assertTrue(commandLine.hasOption("verbose"));

    assertTrue(commandLine.hasOption("list"));
    assertEquals("test", commandLine.getOptionValue("list"));

    String[] expectedArgs = {"arg1", "arg2", "arg3"};
    assertArrayEquals(expectedArgs, commandLine.getArgs());
  }
}