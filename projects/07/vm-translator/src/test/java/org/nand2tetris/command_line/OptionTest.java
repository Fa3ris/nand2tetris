package org.nand2tetris.command_line;

import static org.junit.Assert.*;

import org.junit.Test;

public class OptionTest {

  @Test
  public void matchesShort() throws Exception {
    Option option = new Option(
        "v",
        "verbose",
        "print message during processing",
        0);

    assertTrue(option.matchesShort("v"));
  }

  @Test
  public void matchesLong() throws Exception {
    Option option = new Option(
        "v",
        "verbose",
        "print message during processing",
        0);

    assertTrue(option.matchesLong("verbose"));
  }

  @Test
  public void cannotAddMoreArgThanRequired() throws Exception {
    int argNumber = 10;
    Option option = new Option(
        "f",
        "force",
        "force action",
        argNumber);

    for (int i = 0; i < argNumber; i++) {
      option.addArg("allowedArg");
    }
    try {
      option.addArg("unexpectedArg");
    } catch (Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
      assertEquals(
          String.format("too many arguments, expects only %s arg", argNumber),
          e.getMessage());
    }
  }

  @Test
  public void expectsArg() throws Exception {

    int argNumber = 5;
    Option option = new Option(
        "c",
        "comment",
        "display comments in output asm file",
        argNumber);

    for (int i = 0; i < argNumber; i++) {
      assertTrue(option.expectsArg());
      option.addArg("allowedArg");
    }
    assertFalse(option.expectsArg());
  }

}