package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.utils.TagNames.varDec;
import static org.nand2tetris.parser.utils.XMLUtils.booleanTag;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.encloseInTag;
import static org.nand2tetris.parser.utils.XMLUtils.identifierTag;
import static org.nand2tetris.parser.utils.XMLUtils.intTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.parser.utils.XMLUtils.varTag;
import static org.nand2tetris.tokenizer.Token.booleanToken;
import static org.nand2tetris.tokenizer.Token.charToken;
import static org.nand2tetris.tokenizer.Token.comma;
import static org.nand2tetris.tokenizer.Token.identifierToken;
import static org.nand2tetris.tokenizer.Token.intToken;
import static org.nand2tetris.tokenizer.Token.semicolon;
import static org.nand2tetris.tokenizer.Token.varToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;
import org.nand2tetris.parser.test_utils.TestUtils;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;

public class VarDecTest {


  /**
   * var char key;
   */
  @Test
  public void singleCharDec() throws Exception {
    String varName = "key";
    List<Token> tokens = varDecTokens(charToken(), identifierToken(varName));
    List<String> tags = varDecTags(charTag(), identifierTag(varName));
    assertXML(tokens, tags);
  }

  /**
   * var char foo, bar;
   */
  @Test
  public void twoCharDec() throws Exception {
    String foo = "foo";
    String bar = "bar";
    List<Token> tokens = varDecTokens(charToken(),
        Arrays.asList(identifierToken(foo), identifierToken(bar)
    ));
    List<String> tags = varDecTags(charTag(),
        Arrays.asList(identifierTag(foo), identifierTag(bar)));
    assertXML(tokens, tags);

  }
  /**
   * var int n;
   */
  @Test
  public void intVarDec() throws Exception {
    String n = "n";
    List<Token> tokens = varDecTokens(intToken(), identifierToken(n));
    List<String> tags = varDecTags(intTag(), identifierTag(n));
    assertXML(tokens, tags);
  }

  /**
   * var boolean isKO;
   */
  @Test
  public void booleanVarDec() throws Exception {
    String id = "idKO";
    List<Token> tokens = varDecTokens(booleanToken(), identifierToken(id));
    List<String> tags = varDecTags(booleanTag(), identifierTag(id));
    assertXML(tokens, tags);
  }

  /**
   * var MyClass test1;
   */
  @Test
  public void classVarDec() throws Exception {
    String type = "MyClass";
    String id = "test1";
    List<Token> tokens = varDecTokens(identifierToken(type), identifierToken(id));
    List<String> tags = varDecTags(identifierTag(type), identifierTag(id));
    assertXML(tokens, tags);
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

    List<Token> identifiers = idNames.stream()
        .map(Token::identifierToken)
        .collect(Collectors.toList());

    List<Token> tokens = varDecTokens(charToken(), identifiers);

    List<String> identifierTags = idNames.stream()
        .map(XMLUtils::identifierTag)
        .collect(Collectors.toList());

    List<String> tags = varDecTags(charTag(), identifierTags);

    assertXML(tokens, tags);

  }

  private List<Token> varDecTokens(Token type, Token identifier) {
    return varDecTokens(type, Collections.singletonList(identifier));
  }

  private List<Token> varDecTokens(Token type, List<Token> identifiers) {
    List<Token> tokens = new ArrayList<>();
    tokens.add(varToken());
    tokens.add(type);
    List<Token> joinedIdentifiers = TestUtils.joinTokens(identifiers, comma());
    tokens.addAll(joinedIdentifiers);
    tokens.add(semicolon());
    return tokens;
  }


  private List<String> varDecTags(String type, String identifier) {
    return varDecTags(type, Collections.singletonList(identifier));
  }

  private List<String> varDecTags(String type, List<String> identifiers) {
    List<String> tags = new ArrayList<>();
    tags.add(varTag());
    tags.add(type);
    List<String> joinedIdentifiers = TestUtils.joinTags(identifiers, commaTag());
    tags.addAll(joinedIdentifiers);
    tags.add(semicolonTag());
    return tags;
  }

  private void assertXML(List<Token> tokens, List<String> tags) {
    List<String> enclosed = encloseInTag(varDec, tags);
    String expectedXML = concat(enclosed);
    assertASTXML(tokens, expectedXML);
  }
}
