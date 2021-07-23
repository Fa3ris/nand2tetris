package org.nand2tetris.command_line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import lombok.Setter;

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

  private static final String DASH = "-";
  private String shortOptPrefix = DASH;

  public void setShortOptPrefix(String shortOptPrefix) {
    if (shortOptPrefix != null && shortOptPrefix.length() == 1) {
      this.shortOptPrefix = shortOptPrefix;
    }
  }

  private static final String DASH_DASH = "--";

  private String longOptPrefix = DASH_DASH;

  public void setLongOptPrefix(String longOptPrefix) {
    if (longOptPrefix!= null && longOptPrefix.length() == 2) {
      this.longOptPrefix = longOptPrefix;
    }
  }

  private String srcFilePath;
  private boolean verbose = false;
  private CommentLevel commentLevel = CommentLevel.ALL;
  private int optimizationLevel = 0;
  private boolean help = false;

  private ListIterator<String> argsIterator;

  private CommandLine cmdLine;
  private Options options;

  private Option currentOption;

  public CommandLineParser() {}

  public CommandLineParser(Options options) {
    this.options = options;
  }

  public CommandLine parseLine(String[] line) {
    cmdLine = new CommandLine();
    argsIterator = Arrays.asList(line).listIterator();

    while (argsIterator.hasNext()) {
      String token = argsIterator.next();
      parseToken(token);
    }

    return cmdLine;
  }

  /**
   * if start by short
   *   parse short
   *      remove short
   *      options.getOption
   *      set currentOption
   *          addCurrentOption
   *            if currentOption < 1
   *                set option flag
   *            else
   *                String[] argOptions
   *               for (i <= option number)
   *                  if listerator has next
   *                    argOptions.add(next)
   *                  else
   *                    if not completed all required args
   *                    break
   * if start by long
   *   parse long
   * else consider it is args until encounter prefix or reach end
   */
  private void parseToken(String token) {
    if (token.startsWith(longOptPrefix)) {
      String longOpt = token.substring(longOptPrefix.length());
      parseLongOpt(longOpt);
    } else if (token.startsWith(shortOptPrefix)) {
      String shortOpt = token.substring(shortOptPrefix.length());
      parseShortOpt(shortOpt);
    } else {
      argsIterator.previous();
      parseArgs();
    }
  }

  private void parseShortOpt(String shortOpt) {
    if (shortOpt.length() > 1) {
      String[] chars = shortOpt.split("");
      parseFlags(chars);
    } else {
      // single flag or option
      Option option = options.get(shortOpt);
      if (option != null) {
        currentOption = option;
        fillCurrentOption();
      }
    }
  }

  private void parseFlags(String[] flags) {
    for (String flag : flags) {
      Option option = options.get(flag);
      if (option != null) {
        cmdLine.addOption(option.getOptionName());
      }
    }
  }

  private void parseLongOpt(String longOpt) {
    Option option = options.get(longOpt);
    if (option != null) {
      currentOption = option;
      fillCurrentOption();
    }
  }

  private void fillCurrentOption() {
    int argsLength = currentOption.getArgNumber();
    if (argsLength > 0) {
      String[] argOptions = new String[argsLength];
      for (int i = 0; i < argsLength; i++) {
        if (argsIterator.hasNext()) {
          String argOption = argsIterator.next();
          argOptions[i] = argOption;
        } else {
          System.err.println("no more token for parsing");
        }
      }
      cmdLine.addOption(currentOption.getOptionName(), argOptions);
    } else {
      cmdLine.addOption(currentOption.getOptionName());
    }
  }

  private void parseArgs() {
    while (argsIterator.hasNext()) {
      String token = argsIterator.next();
      boolean doNotStartByPrefix = !(token.startsWith(shortOptPrefix) || token.startsWith(longOptPrefix));
      if (doNotStartByPrefix) {
        cmdLine.addArg(token);
      } else {
        argsIterator.previous();
        break;
      }
    }
  }

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
