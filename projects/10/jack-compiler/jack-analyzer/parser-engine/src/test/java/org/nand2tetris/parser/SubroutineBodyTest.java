package org.nand2tetris.parser;

import static com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl.INDENT_NUMBER;
import static org.nand2tetris.parser.test_utils.TestUtils.assertASTXML;
import static org.nand2tetris.parser.test_utils.TestUtils.encloseInTag;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTags;
import static org.nand2tetris.parser.test_utils.TestUtils.varDecTokens;
import static org.nand2tetris.parser.utils.XMLUtils.booleanTag;
import static org.nand2tetris.parser.utils.XMLUtils.charTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.tokenizer.Token.booleanToken;
import static org.nand2tetris.tokenizer.Token.charToken;
import static org.nand2tetris.tokenizer.Token.closeBrace;
import static org.nand2tetris.tokenizer.Token.identifierToken;
import static org.nand2tetris.tokenizer.Token.openBrace;
import static org.nand2tetris.tokenizer.Token.semicolon;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.Test;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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

  private String docToString(Document document) throws Exception {
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute(INDENT_NUMBER, 15);
    Transformer transformer;
    transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

    StringWriter writer = new StringWriter();

    transformer.transform(new DOMSource(document), new StreamResult(writer));

    return writer.getBuffer().toString();
  }

  private Document stringToDoc(String s) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    builder = factory.newDocumentBuilder();
    Document document = builder.parse(new InputSource(new StringReader(s)));
    removeNodesEmptyOrWhiteSpace(document);
    return document;
  }

  public static void removeNodesEmptyOrWhiteSpace(Node node) {
    NodeList list = node.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      removeNodesEmptyOrWhiteSpace(list.item(i));
    }
    boolean emptyElement = node.getNodeType() == Node.ELEMENT_NODE
        && node.getChildNodes().getLength() == 0;
    boolean emptyText = node.getNodeType() == Node.TEXT_NODE
        && node.getNodeValue().trim().isEmpty();
    if (emptyElement || emptyText) {
      node.getParentNode().removeChild(node);
    }
  }

}
