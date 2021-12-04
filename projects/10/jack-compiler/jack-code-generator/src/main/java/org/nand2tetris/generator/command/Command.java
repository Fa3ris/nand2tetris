package org.nand2tetris.generator.command;

import java.io.PrintWriter;
import org.nand2tetris.generator.Operation;
import org.nand2tetris.generator.Segment;

public class Command implements AutoCloseable {

  private final PrintWriter writer;

  public Command(PrintWriter writer) {
    this.writer = writer;
  }

  public void push(Segment segment, int index) {

  }

  public void pop(Segment segment, int index) {

  }

  public void operation(Operation op) {

  }

  public void label(String label) {

  }

  public void goTo(String label) {

  }

  public void ifGoTo(String label) {

  }

  public void call(String name, int nArgs) {

  }

  public void function(String name, int nLocals) {

  }

  public void _return() {

  }

  @Override
  public void close() throws Exception {
    this.writer.close();
  }
}
