package org.nand2tetris.command_line;

import java.util.LinkedList;
import java.util.List;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Option {

  private String optionName;
  private final List<String> args = new LinkedList<>();

  private final String shortOpt;
  private final String longOpt;
  private final String description;

  private final int argNumber;

  public String getOptionName() {
    return optionName;
  }

  public void setOptionName(String optionName) {
    this.optionName = optionName;
  }

  public String getShortOpt() {
    return shortOpt;
  }

  public boolean hasShortOpt() {
    return shortOpt != null;
  }

  public String getLongOpt() {
    return longOpt;
  }

  public boolean hasLongOpt() {
    return longOpt != null;
  }

  public String getDescription() {
    return description;
  }

  public int getArgNumber() {
    return argNumber;
  }

  public Option(Builder builder) {
    optionName = builder.optionName;
    shortOpt = builder.shortOpt;
    longOpt = builder.longOpt;
    description = builder.description;
    argNumber = builder.argNumber;
  }

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

  public String[] getArgs() {
    String[] argsArr = args.toArray(new String[0]);
    return argsArr;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private String optionName;
    private String shortOpt;
    private String longOpt;
    private String description;
    private int argNumber;

    public Builder optionName(String optionName) {
      this.optionName = optionName;
      return this;
    }

    public Builder shortOpt(String shortOpt) {
      this.shortOpt = shortOpt;
      return this;
    }

    public Builder longOpt(String longOpt) {
      this.longOpt = longOpt;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder argNumber(int argNumber) {
      this.argNumber = argNumber;
      return this;
    }

    public Option build() {
      return new Option(this);
    }
  }
}
