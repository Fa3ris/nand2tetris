package org.nand2tetris.command_line;

import java.util.HashMap;
import java.util.Map;

public class Options {

  private final Map<String, Option> optionMap = new HashMap<>();

  public void addOption(Option option) {
    String shortOpt = option.getShortOpt();
    if (shortOpt != null && !shortOpt.isEmpty()) {
      optionMap.put(shortOpt, option);
    }
    String longOpt = option.getLongOpt();
    if (longOpt != null && !longOpt.isEmpty()) {
      optionMap.put(longOpt, option);
    }
  }

  public Option get(String opt) {
    return optionMap.get(opt);
  }

  public Option getShort(String shortOpt) {
    return null;
  }

  public Option getLong(String longOpt) {
    return null;
  }

  private boolean isAlreadyPresent(OptionName optionName) {
    return false;
  }


}
