package org.nand2tetris.command_line;

import java.util.Properties;

public class CommandLine {

  public boolean hasOption(OptionName optionName) {
    return false;
  }

  public boolean hasFlag(OptionName optionName) {
    return false;
  }

  public String getOptionValue(OptionName optionName) {
    return null;
  }

  public String[] getOptionValues(OptionName optionName) {
    return null;
  }

  public String[] getArgs() {
    return null;
  }

  public void setDefaults(Properties defaults) {

  }
}
