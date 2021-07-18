package org.nand2tetris.command_line;

import java.util.Arrays;
import java.util.ListIterator;

/**
 * utility_name[-a][-b][-c option_argument]
 *     [-d|-e][-f[option_argument]][operand...]
 *
 * [-a] optional == flag
 * [-c option_argument] optional with argument value
 * [-d|-e] optional exclusive OR
 * [-f[option_argument]] optional: -f -f123 -fBlabla
 * [operand...] 0 or n
 *
 *
 * Maybe [-g=option_argument] ?
 *
 * potential flag
 *
 * -v, --verbose    display messages while translating
 *
 * -c, --comment [none|function-only|all] display comments in output ASM file
 *
 * -o, --optimization [0|1] level of optimization: reduce number of instructions
 *
 * -h, --help
 *
 * filePath     path to src file or src directory
 *
 * can combine help with others ? - do not care here
 */
public class CommandLineParser {

  private String srcFilePath;
  private boolean verbose = false;
  private CommentLevel commentLevel = CommentLevel.ALL;
  private int optimizationLevel = 0;
  private boolean help = false;

  private ListIterator<String> argsIterator;

  public void parse(String[] args) {
    reset();
    argsIterator = Arrays.asList(args).listIterator();
    while (argsIterator.hasNext()) {
      String nextArg = argsIterator.next();

      if (nextArg.startsWith("-")) {
        String option = nextArg.substring(1);
        parseOption(option);
      } else {
        parseSrcFilePath(nextArg);
      }
    }
  }

  private void reset() {
    verbose = false;
    commentLevel = CommentLevel.ALL;
    srcFilePath = null;
    optimizationLevel = 0;
    help = false;
  }

  private void parseOption(String option) {
    switch (option) {
      case "v":
      case "-verbose":
        verbose = true;
        break;
      case "h":
      case "-help":
        help = true;
        break;
      case "c":
      case "-comment":
        String commentLevelValue = argsIterator.next();
        CommentLevel level = CommentLevel.getFromValue(commentLevelValue);
        commentLevel = level;
        break;
      case "o":
      case "-optimization":
        String optimizationLevelLexeme = argsIterator.next();
        int optLevel = Integer.parseInt(optimizationLevelLexeme);
        optimizationLevel = optLevel;
        break;
    }
  }

  private void parseSrcFilePath(String nextArg) {
    srcFilePath = nextArg;
  }

  public String srcInputPath() {
    return srcFilePath;
  }

  public boolean verbose() {
    return verbose;
  }

  public CommentLevel commentLevel() {
    return commentLevel;
  }

  public int optimizationLevel() {
    return optimizationLevel;
  }

  public boolean help() {
    return help;
  }
}
