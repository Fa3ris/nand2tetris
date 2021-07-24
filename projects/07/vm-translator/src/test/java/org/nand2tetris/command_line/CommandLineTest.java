package org.nand2tetris.command_line;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandLineTest {

  @Test
  public void printCommandLine() throws Exception {

    CommandLine commandLine = new CommandLine();

    commandLine.addOption("compactTresLong");
    commandLine.addOption("verbose");
    commandLine.addOption("list", "pathToDir");
    commandLine.addOption("table", "14", "58");

    commandLine.addArg("hello");
    commandLine.addArg("world");
    commandLine.addArg("foo");

    commandLine.print();
  }

}