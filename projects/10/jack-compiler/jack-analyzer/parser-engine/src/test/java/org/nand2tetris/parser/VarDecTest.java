package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTags;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTokens;
import static org.nand2tetris.parser.utils.XMLUtils.booleanTag;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.intTag;
import static org.nand2tetris.tokenizer.Token.booleanToken;
import static org.nand2tetris.tokenizer.Token.charToken;
import static org.nand2tetris.tokenizer.Token.identifierToken;
import static org.nand2tetris.tokenizer.Token.intToken;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.nand2tetris.tokenizer.Token;

public class VarDecTest {


  /**
   * var char key;
   */
  @Test
  public void singleCharDec() throws Exception {
    String varName = "key";
    List<Token> tokens = varDecTokens(charToken(), varName);
    List<String> tags = varDecTags(charTag(), varName);
    assertASTXML(tokens, tags);
  }

  /**
   * var char foo, bar;
   */
  @Test
  public void twoCharDec() throws Exception {
    String foo = "foo";
    String bar = "bar";
    List<Token> tokens = varDecTokens(charToken(),
        Arrays.asList(foo, bar));
    List<String> tags = varDecTags(charTag(),
        Arrays.asList(foo, bar));
    assertASTXML(tokens, tags);

  }
  /**
   * var int n;
   */
  @Test
  public void intVarDec() throws Exception {
    String n = "n";
    List<Token> tokens = varDecTokens(intToken(), n);
    List<String> tags = varDecTags(intTag(), n);
    assertASTXML(tokens, tags);
  }

  /**
   * var boolean isKO;
   */
  @Test
  public void booleanVarDec() throws Exception {
    String id = "idKO";
    List<Token> tokens = varDecTokens(booleanToken(), id);
    List<String> tags = varDecTags(booleanTag(), id);
    assertASTXML(tokens, tags);
  }

  /**
   * var MyClass test1;
   */
  @Test
  public void classVarDec() throws Exception {
    String type = "MyClass";
    String id = "test1";
    List<Token> tokens = varDecTokens(identifierToken(type), id);
    List<String> tags = varDecTags(identifierTag(type), id);
    assertASTXML(tokens, tags);
  }

  /**
   * var char 1, 2, ..., n-1, n;
   */
  @Test
  public void nCharDecRefactor() throws Exception {

    int n = 10;
    List<String> idNames = IntStream.rangeClosed(1, n)
        .mapToObj(Objects::toString)
        .collect(Collectors.toList());

    List<Token> tokens = varDecTokens(charToken(), idNames);

    List<String> tags = varDecTags(charTag(), idNames);

    assertASTXML(tokens, tags);

  }
}
