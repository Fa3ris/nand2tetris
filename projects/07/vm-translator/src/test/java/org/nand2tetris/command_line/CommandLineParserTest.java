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
}