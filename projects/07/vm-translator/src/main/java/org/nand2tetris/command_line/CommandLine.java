package org.nand2tetris.command_line;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommandLine {

  public final Map<String, String[]> optionsFound = new HashMap<>();
  private final List<String> args = new LinkedList<>();

  public void addOption(String optionName, String... optionArgs) {
    optionsFound.put(optionName, optionArgs);
  }

  public void addOption(String optionName) {
    addOption(optionName, "true");
  }

  public boolean hasOption(String optionName) {
    boolean res = optionsFound.containsKey(optionName);
    return res;
  }

  public String getOptionValue(String optionName) {
    String[] args = optionsFound.get(optionName);
    if (args.length > 0) {
      return args[0];
    } else {
      String msg = String.format("%s does not have arguments %s", optionName, optionsFound);
      throw new IllegalArgumentException(msg);
    }
  }

  public String[] getOptionValues(String optionName) {
    String[] args = optionsFound.get(optionName);
    return args;
  }

  public String[] getArgs() {
    return args.toArray(new String[0]);
  }

  public void addArg(String arg) {
    args.add(arg);
  }

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



  public void setDefaults(Properties defaults) {

  }
}
