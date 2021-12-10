package org.nand2tetris.generator.command;

import java.io.PrintWriter;
import org.nand2tetris.generator.Operation;
import org.nand2tetris.generator.Segment;
import org.nand2tetris.generator.symbol_table.TableEntry;

public class Command implements AutoCloseable {

  private final PrintWriter writer;

  public Command(PrintWriter writer) {
    this.writer = writer;
  }

  public void push(Segment segment, int index) {
    writer.println(String.format("push %s %s", segment.value(), index));

  }

  public void push(TableEntry entry) {
    push(Segment.resolve(entry.getScope()), entry.getIndex());
  }

  public void pop(Segment segment, int index) {
    writer.println(String.format("pop %s %s", segment.value(), index));
  }

  public void pop(TableEntry entry) {
    pop(Segment.resolve(entry.getScope()), entry.getIndex());
  }

  public void operation(Operation op) {
    switch (op) {
      case ADD:
        writer.println("add");
        break;
      case SUB:
        writer.println("sub");
        break;
      case NEG:
        writer.println("neg");
        break;
    }

  }

  public void label(String label) {

  }

  public void goTo(String label) {

  }

  public void ifGoTo(String label) {

  }

  public void call(String name, int nArgs) {
    writer.println(String.format("call %s %s", name, nArgs));
  }

  public void function(String name, int nLocals) {
    writer.println(String.format("function %s %s", name, nLocals));
  }

  public void _return() {
    writer.println("return");
  }

  @Override
  public void close() throws Exception {
    this.writer.close();
  }

  public void bindThis() {
    push(Segment.ARG, 0);
    pop(Segment.POINTER, 0);
  }
}
