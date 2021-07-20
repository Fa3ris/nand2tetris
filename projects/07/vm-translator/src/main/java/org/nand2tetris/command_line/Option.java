package org.nand2tetris.command_line;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Option {

  private static final String DASH = "-";
  private static final String DASH_DASH = "--";

  private String optionName;

  private List<String> args = new LinkedList<>();

  @Setter
  private String shortOptPrefix = DASH;
  private String shortOpt;

  @Setter
  private String longOptPrefix = DASH_DASH;
  private String longOpt;

  @Getter
  private String description;

  private int argNumber = -1;

  public Option(String shortOpt, String longOpt, String description, int argNumber) {
    if (shortOpt == null || shortOpt.isEmpty()) {
        throw new IllegalArgumentException(
            String.format("invalid shortOpt {%s}", shortOpt)
        );
    }
    if (argNumber < 0) {
      throw new IllegalArgumentException(
          String.format("invalid argNumber {%s}", argNumber)
      );
    }
    this.shortOpt = shortOpt;
    this.longOpt = longOpt;
    this.description = description;
    this.argNumber = argNumber;
  }

  public Option(String shortOpt, String description, int argNumber) {
    this(shortOpt, null, description, argNumber);
  }

  public Option(String shortOpt, String longOpt, String description) {
    this(shortOpt, longOpt, description, 0);
  }

  public Option(String shortOpt, String description) {
    this(shortOpt, description, 0);
  }

  public boolean matchesShort(String token) {
    return shortOpt.equals(token);
  }

  public boolean matchesLong(String token) {
    if (longOpt == null) {
      return false;
    }
    return longOpt.equals(token);
  }

  public boolean matchesToken(String token) {
    boolean matches;
    matches = shortOpt.equals(token);
    if (!matches && longOpt != null) {
      matches = longOpt.equals(token);
    }
    return matches;
  }

  public boolean hasArgs() {
    return argNumber > 0;
  }

  public int argLength() {
    return argNumber;
  }

  public boolean expectsArg() {
    return  args.size() < argNumber;
  }

  public void addArg(String token) {
    if (args.size() >= argNumber) {
      throw new IllegalArgumentException(
          String.format("too many arguments, expects only %s arg", argNumber)
      );
    }
    args.add(token);
  }

  public static final class Builder {
    /**
     * option name
     * shortOp
     * longOp
     * description
     * argNumber
     *
     */
  }
}
