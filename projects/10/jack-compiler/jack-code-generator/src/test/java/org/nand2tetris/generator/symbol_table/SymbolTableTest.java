package org.nand2tetris.generator.symbol_table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nand2tetris.generator.Scope;
import org.nand2tetris.generator.Type;

public class SymbolTableTest {

  private SymbolTable table;

  @Before
  public void setUp() throws Exception {
    table = new SymbolTable();
  }

  @Test
  public void intField() throws Exception {
    String name = "x";
    table.define(name, Type.INT, Scope.FIELD);
    TableEntry expectedEntry = new TableEntry(name, Type.INT, Scope.FIELD, 0);
    TableEntry actualEntry = table.get(name);
    assertEquals(expectedEntry, actualEntry);
  }

  @Test
  public void booleanField() throws Exception {
    String name = "isOK";
    table.define(name, Type.BOOLEAN, Scope.FIELD);
    TableEntry expectedEntry = new TableEntry(name, Type.BOOLEAN, Scope.FIELD, 0);
    TableEntry actualEntry = table.get(name);
    assertEquals(expectedEntry, actualEntry);
  }

  @Test
  public void charField() throws Exception {
    String name = "key";
    table.define(name, Type.CHAR, Scope.FIELD);
    TableEntry expectedEntry = new TableEntry(name, Type.CHAR, Scope.FIELD, 0);
    TableEntry actualEntry = table.get(name);
    assertEquals(expectedEntry, actualEntry);
  }

  @Test
  public void classField() throws Exception {
    String name = "point";
    Type pointType = new Type("Point");
    table.define(name, pointType, Scope.FIELD);
    TableEntry expectedEntry = new TableEntry(name, pointType, Scope.FIELD, 0);
    TableEntry actualEntry = table.get(name);
    assertEquals(expectedEntry, actualEntry);
  }
  
  
  @Test
  public void twoFields() throws Exception {
    table.define("f1", Type.INT, Scope.FIELD);
    table.define("f2", Type.INT, Scope.FIELD);

    TableEntry expectedEntry = new TableEntry("f2", Type.INT, Scope.FIELD, 1);

    TableEntry actualEntry = table.get("f2");
    assertEquals(expectedEntry, actualEntry);
  }

  @Test
  public void intStatic() throws Exception {
    table.define("pointCount", Type.INT, Scope.STATIC);

    TableEntry expectedEntry = new TableEntry("pointCount", Type.INT, Scope.STATIC, 0);

    TableEntry actualEntry = table.get("pointCount");
    assertEquals(expectedEntry, actualEntry);
  }


  @Test
  public void staticAndField() throws Exception {

    table.define("pointCount", Type.INT, Scope.STATIC);
    table.define("f1", Type.INT, Scope.FIELD);

    TableEntry expectedStaticEntry = new TableEntry("pointCount", Type.INT, Scope.STATIC, 0);
    TableEntry expectedFieldEntry = new TableEntry("f1", Type.INT, Scope.FIELD, 0);

    TableEntry actualStaticEntry = table.get("pointCount");
    assertEquals(expectedStaticEntry, actualStaticEntry);

    TableEntry actualFieldEntry = table.get("f1");
    assertEquals(expectedFieldEntry, actualFieldEntry);

  }

  @Test
  public void doNotResetClass() throws Exception {
    table.define("pointCount", Type.INT, Scope.STATIC);
    table.define("f1", Type.INT, Scope.FIELD);

    table.resetSubroutine();

    assertNotNull(table.get("f1"));
  }

  @Test
  public void charArgument() throws Exception {
    table.define("key", Type.CHAR, Scope.ARGUMENT);

    TableEntry expectedEntry = new TableEntry("key", Type.CHAR, Scope.ARGUMENT, 0);

    TableEntry actualEntry = table.get("key");
    assertEquals(expectedEntry, actualEntry);
  }

  @Test
  public void booleanLocal() throws Exception {
    table.define("isOK", Type.BOOLEAN, Scope.LOCAL);

    TableEntry expectedEntry = new TableEntry("isOK", Type.BOOLEAN, Scope.LOCAL, 0);

    TableEntry actualEntry = table.get("isOK");
    assertEquals(expectedEntry, actualEntry);
  }


  @Test
  public void twoLocals() throws Exception {
    table.define("isOK", Type.BOOLEAN, Scope.LOCAL);
    table.define("isDead", Type.BOOLEAN, Scope.LOCAL);

    TableEntry expectedEntry = new TableEntry("isDead", Type.BOOLEAN, Scope.LOCAL, 1);
    TableEntry actualEntry = table.get("isDead");
    assertEquals(expectedEntry, actualEntry);
  }

  @Test
  public void localAndArg() throws Exception {
    table.define("isOK", Type.BOOLEAN, Scope.LOCAL);
    table.define("key", Type.CHAR, Scope.ARGUMENT);

    TableEntry expectedLocalEntry = new TableEntry("isOK", Type.BOOLEAN, Scope.LOCAL, 0);
    TableEntry actualLocalEntry = table.get("isOK");
    assertEquals(expectedLocalEntry, actualLocalEntry);

    TableEntry expectedArgEntry = new TableEntry("key", Type.CHAR, Scope.ARGUMENT, 0);
    TableEntry actualArgEntry = table.get("key");
    assertEquals(expectedArgEntry, actualArgEntry);
  }
  
  @Test
  public void resetArgumentAndLocal() throws Exception {

    table.define("key", Type.CHAR, Scope.ARGUMENT);
    table.define("isOK", Type.BOOLEAN, Scope.LOCAL);

    table.resetSubroutine();

    assertNull(table.get("key"));
    assertNull(table.get("isOK"));

  }
  
  @Test
  public void resetAndRefill() throws Exception {

    table.define("key", Type.CHAR, Scope.ARGUMENT);
    table.define("isOK", Type.BOOLEAN, Scope.LOCAL);

    table.resetSubroutine();

    table.define("boom", Type.CHAR, Scope.ARGUMENT);
    Type rectType = new Type("Rect");
    table.define("rect", rectType, Scope.LOCAL);

    TableEntry expectedEntry = new TableEntry("boom", Type.CHAR, Scope.ARGUMENT, 0);
    assertEquals(expectedEntry, table.get("boom"));

    TableEntry expectedLocalEntry = new TableEntry("rect", rectType, Scope.LOCAL, 0);
    assertEquals(expectedLocalEntry, table.get("rect"));
  }

}