package org.nand2tetris.toolchain;

import java.nio.file.Path;

public class Toolchain {
  
  public void process(Path input, Path output) {
    System.out.printf("process%ninput %s%noutput %s%n", input.toAbsolutePath(), output.toAbsolutePath());

  }

}
