package org.nand2tetris.parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SymbolTableTest {

  private SymbolTable table;

  @Before
  public void setUp() throws Exception {
    table = new SymbolTable();
  }

  @Test
  public void predefinedSymbols() throws Exception {
    for (int i = 0; i < 16 ; i++) {
      String register = "R" + i;
      assertEquals(i, (int) table.lookup(register));
    }
    assertEquals(16384, (int) table.lookup("SCREEN"));
    assertEquals(24576, (int) table.lookup("KBD"));
    assertEquals(0, (int) table.lookup("SP"));
    assertEquals(1, (int) table.lookup("LCL"));
    assertEquals(2, (int) table.lookup("ARG"));
    assertEquals(3, (int) table.lookup("THIS"));
    assertEquals(4, (int) table.lookup("THAT"));
  }

  @Test
  public void label() throws Exception {
    String label = "FOO";
    int addr = 21;
    assertNull(table.lookup(label));

    assertNull(table.addLabel(label, addr));
    assertEquals(addr, (int) table.lookup(label));
  }

  @Test(expected = SymbolAlreadyDefinedException.class)
  public void cannotAdd2SameLabel() throws Exception {
    String label = "FOO";
    table.addLabel(label, 0);
    table.addLabel(label, 0);
  }

  @Test
  public void variable() throws Exception {
    String variable = "foo";
    String variable2 = "bar";
    int expectedAddr = 16;

    assertNull(table.lookup(variable));
    assertNull(table.addVariable(variable));
    assertEquals(expectedAddr, (int) table.lookup(variable));

    expectedAddr++;
    assertNull(table.lookup(variable2));
    assertNull(table.addVariable(variable2));
    assertEquals(expectedAddr, (int) table.lookup(variable2));
  }

  @Test(expected = SymbolAlreadyDefinedException.class)
  public void cannotAdd2SameVariable() throws Exception {
    String variable = "foo";
    assertNull(table.addVariable(variable));
    assertNull(table.addVariable(variable));
  }

}