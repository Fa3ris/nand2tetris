package org.nand2tetris.parser;

import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTokens;
import static org.nand2tetris.tokenizer.Token.booleanToken;
import static org.nand2tetris.tokenizer.Token.charToken;
import static org.nand2tetris.tokenizer.Token.closeBrace;
import static org.nand2tetris.tokenizer.Token.closeParen;
import static org.nand2tetris.tokenizer.Token.identifierToken;
import static org.nand2tetris.tokenizer.Token.openBrace;
import static org.nand2tetris.tokenizer.Token.openParen;
import static org.nand2tetris.tokenizer.Token.semicolon;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.nand2tetris.tokenizer.Token;

public class SubroutineBodyTest {


  /**
   * {
   *       var char key;
   *       var boolean exit;
   *  }
   */
  @Test
  public void onlyVarDec() throws Exception {
    List<Token> tokens = new ArrayList<>();
    String first = "key";
    String second = "exit";
    tokens.add(openBrace());
    tokens.addAll(varDecTokens(charToken(), first));
    tokens.addAll(varDecTokens(booleanToken(), second));
    tokens.add(closeBrace());

    URL url = getClass().getResource("subroutinebody-2.xml");
    File file = new File(url.getFile());

    assertASTXML(tokens, file);
  }

  /**
   * {
   *   let game = game;
   * }
   */
  @Test
  public void oneStatement() throws Exception {
    List<Token> tokens = new ArrayList<>();
    String id = "game";
    tokens.add(openBrace());
    tokens.add(Token.letToken());
    tokens.add(identifierToken(id));
    tokens.add(Token.equalToken());
    tokens.add(identifierToken(id));
    tokens.add(semicolon());
    tokens.add(closeBrace());

    URL url = getClass().getResource("subroutinebody-1.xml");
    File file = new File(url.getFile());

    assertASTXML(tokens, file);
  }

  /**
   *      {
   *         return;
   *      }
   */
  @Test
  public void returnVoid() throws Exception {
    List<Token> tokens = new ArrayList<>();
    tokens.add(openBrace());
    tokens.add(Token.returnToken());
    tokens.add(semicolon());
    tokens.add(closeBrace());

    URL url = getClass().getResource("subroutinebody-3.xml");
    File file = new File(url.getFile());

    assertASTXML(tokens, file);
  }

  /**
   * {
   *       var char key;
   *       var boolean exit;
   *       let game = game;
   *       return;
   *  }
   */
  @Test
  public void varLetReturn() throws Exception {
    List<Token> tokens = new ArrayList<>();
    String first = "key";
    String second = "exit";
    tokens.add(openBrace());
    tokens.addAll(varDecTokens(charToken(), first));
    tokens.addAll(varDecTokens(booleanToken(), second));

    String id = "game";
    tokens.add(Token.letToken());
    tokens.add(identifierToken(id));
    tokens.add(Token.equalToken());
    tokens.add(identifierToken(id));
    tokens.add(semicolon());

    tokens.add(Token.returnToken());
    tokens.add(semicolon());
    tokens.add(closeBrace());

    URL url = getClass().getResource("subroutinebody-4.xml");
    File file = new File(url.getFile());

    assertASTXML(tokens, file);

  }

  /**
   * {
   *       let game = game;
   *       let win = win;
   *  }
   */
  @Test
  public void letlet() throws Exception {
    List<Token> tokens = new ArrayList<>();
    tokens.add(openBrace());
    String id = "game";
    tokens.add(Token.letToken());
    tokens.add(identifierToken(id));
    tokens.add(Token.equalToken());
    tokens.add(identifierToken(id));
    tokens.add(semicolon());

    String id2= "win";
    tokens.add(Token.letToken());
    tokens.add(identifierToken(id2));
    tokens.add(Token.equalToken());
    tokens.add(identifierToken(id2));
    tokens.add(semicolon());

    tokens.add(closeBrace());

    URL url = getClass().getResource("subroutinebody-5.xml");
    File file = new File(url.getFile());

    assertASTXML(tokens, file);
  }

  /**
   *     {
   *         var SquareGame game;
   *         let game = game;
   *         do game.run();
   *         do game.dispose();
   *         return;
   *     }
   */
  @Test
  public void varLetDoReturn() throws Exception {
    List<Token> tokens = new ArrayList<>();
    tokens.add(openBrace());
    tokens.addAll(varDecTokens(identifierToken("SquareGame"), "game"));
    String id = "game";
    tokens.add(Token.letToken());
    tokens.add(identifierToken(id));
    tokens.add(Token.equalToken());
    tokens.add(identifierToken(id));
    tokens.add(semicolon());

    tokens.add(Token.doToken());
    tokens.add(identifierToken(id));
    tokens.add(Token.dot());
    tokens.add(identifierToken("run"));
    tokens.add(openParen());
    tokens.add(closeParen());
    tokens.add(semicolon());

    tokens.add(Token.doToken());
    tokens.add(identifierToken(id));
    tokens.add(Token.dot());
    tokens.add(identifierToken("dispose"));
    tokens.add(openParen());
    tokens.add(closeParen());
    tokens.add(semicolon());

    tokens.add(Token.returnToken());
    tokens.add(semicolon());

    tokens.add(closeBrace());

    URL url = getClass().getResource("subroutinebody-6.xml");
    File file = new File(url.getFile());

    assertASTXML(tokens, file);
  }

  /**
   *     {
   *         var boolean b;
   *         if (b) {
   *         }
   *         else {
   *         }
   *         return;
   *     }
   */
  @Test
  public void ifElse() throws Exception {
    List<Token> tokens = new ArrayList<>();
    String identifier = "b";
    tokens.add(openBrace());
    tokens.addAll(varDecTokens(booleanToken(), identifier));
    tokens.add(Token.ifToken());
    tokens.add(openParen());
    tokens.add(identifierToken(identifier));
    tokens.add(closeParen());
    tokens.add(openBrace());
    tokens.add(closeBrace());
    tokens.add(Token.elseToken());
    tokens.add(openBrace());
    tokens.add(closeBrace());

    tokens.add(Token.returnToken());
    tokens.add(semicolon());

    tokens.add(closeBrace());

    URL url = getClass().getResource("subroutinebody-7.xml");
    File file = new File(url.getFile());

    assertASTXML(tokens, file);
  }

}
