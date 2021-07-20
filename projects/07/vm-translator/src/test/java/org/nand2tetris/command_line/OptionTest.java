package org.nand2tetris.command_line;

import static org.junit.Assert.*;

import org.junit.Test;

public class OptionTest {

  @Test
  public void optionMatchesToken() throws Exception {
    Option option = new Option(
        "v",
        "verbose",
        "print message during processing",
        0);

    assertTrue(option.matchesShort("v"));
    assertTrue(option.matchesLong("verbose"));
  }

  @Test
  public void noArgOption_expectsNoArg() throws Exception {
    Option option = new Option(
        "n",
        "dry-run",
        "dry run, no modification made",
        0);

    assertFalse(option.expectsArg());
  }

  @Test(expected = IllegalArgumentException.class)
  public void noArgOption_cannotPassArg() throws Exception {
    Option option = new Option(
        "f",
        "force",
        "force action",
        0);

    option.addArg("unexpectedArg");
  }

  @Test
  public void cannotPassMoreArgThanRequired() throws Exception {
    Option option = new Option(
        "f",
        "force",
        "force action",
        10);

    for (int i = 0; i <10 ; i++) {
      option.addArg("allowedArg");
    }
    try {
      option.addArg("unexpectedArg");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      assertEquals(
          String.format("too many arguments, expects only %s arg", 10),
          e.getMessage());
    }
  }

  @Test
  public void oneArgOption() throws Exception {
    Option option = new Option(
        "c",
        "comment",
        "display comments in output asm file",
        1);

    assertTrue(option.expectsArg());
    option.addArg("toto");
    assertFalse(option.expectsArg());
  }

}