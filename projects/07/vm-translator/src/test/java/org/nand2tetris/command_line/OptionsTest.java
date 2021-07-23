package org.nand2tetris.command_line;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

public class OptionsTest {


  Option option = Mockito.mock(Option.class);

  @Test
  public void addShortOption() throws Exception {
    String shortOpt = "v";
    when(option.getShortOpt()).thenReturn(shortOpt);
    Options options = new Options();
    options.addOption(option);
    Option actual = options.get(shortOpt);
    assertEquals(option, actual);
  }

  @Test
  public void addLongOption() throws Exception {
    String longOpt = "verbose";
    when(option.getLongOpt()).thenReturn(longOpt);
    Options options = new Options();
    options.addOption(option);
    Option actual = options.get(longOpt);
    assertEquals(option, actual);
  }

}