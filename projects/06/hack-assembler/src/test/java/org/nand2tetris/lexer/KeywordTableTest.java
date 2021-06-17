package org.nand2tetris.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class KeywordTableTest {

  @Test
  public void isKeyword() throws Exception {
    // do not test every combination
    assertEquals(TokenType.A, KeywordTable.lookup("A"));
    assertEquals(TokenType.JEQ, KeywordTable.lookup("JEQ"));
  }

  @Test
  public void isNotKeyword() throws Exception {
    // case sensitive
    assertNull(KeywordTable.lookup("a"));

    assertNull(KeywordTable.lookup(""));
    assertNull(KeywordTable.lookup(null));
    assertNull(KeywordTable.lookup("\n"));

    // not exact match
    assertNull(KeywordTable.lookup("AMDA"));
    assertNull(KeywordTable.lookup("A "));
  }

}