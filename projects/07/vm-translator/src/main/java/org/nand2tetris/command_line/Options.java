package org.nand2tetris.command_line;

import lombok.Setter;

public class Options {

  private static final String DASH = "-";
  private static final String DASH_DASH = "--";

  @Setter
  private String shortOptPrefix = DASH;

  @Setter
  private String longOptPrefix = DASH_DASH;

  public void addOption(Option option) {

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
