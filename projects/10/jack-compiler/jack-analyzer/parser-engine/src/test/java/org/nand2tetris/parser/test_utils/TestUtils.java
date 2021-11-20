package org.nand2tetris.parser.test_utils;

import static com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl.INDENT_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.parser.utils.XMLUtils.varTag;
import static org.nand2tetris.tokenizer.Token.comma;
import static org.nand2tetris.tokenizer.Token.semicolon;
import static org.nand2tetris.tokenizer.Token.varToken;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.Assert;
import org.nand2tetris.parser.ParserEngine;
import org.nand2tetris.parser.ast.AST;
import org.nand2tetris.parser.stubs.TokenizerStub;
import org.nand2tetris.parser.utils.Joiner;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

public abstract class TestUtils {

  public static void assertASTXML(List<Token> tokens,  String expectedXml) {
    AST ast = parse(tokens);
    assertEquals(expectedXml, ast.toXMLString());

  }

  public static void assertASTXML(List<Token> tokens,  List<String> expectedTags) {
    AST ast = parse(tokens);
    assertEquals(concat(expectedTags), ast.toXMLString());

  }

  public static void assertASTXML(List<Token> tokens,  File file) {
    String actual = parse(tokens).toXMLString();
    Source actualSource = Input.fromString(actual).build();
    Source expectedSource = Input.fromFile(file).build();
    try {
      assertEqualSources(expectedSource, actualSource);
    } catch (AssertionError e) {
      printXML(actual);
//      System.out.println(actual);
      throw e;
    }
  }

  private static void assertEqualSources(Source expected, Source actual) {
    Diff myDiff = DiffBuilder.compare(expected)
        .withTest(actual)
        .normalizeWhitespace()
        .build();

    Assert.assertFalse(myDiff.fullDescription(), myDiff.hasDifferences());
  }

  public static AST parse(List<Token> tokens) {
    return new ParserEngine(new TokenizerStub(tokens)).parse();
  }

  public static List<String> joinTags(List<String> identifierTags, String delimiterTag) {
    Joiner<String> tagJoiner = new Joiner<>(delimiterTag);
    return tagJoiner.join(identifierTags);
  }

  public static List<String> encloseInTag(String parentTag, List<String> tags) {
    List<String> enclosed = new ArrayList<>();
    enclosed.add(openTag(parentTag));
    enclosed.addAll(tags);
    enclosed.add(closeTag(parentTag));
    return enclosed;
  }

  public static List<Token> joinTokens(List<Token> identifierTokens, Token delimiter) {
    Joiner<Token> tokenJoiner = new Joiner<>(delimiter);
    return tokenJoiner.join(identifierTokens);
  }

  public static List<Token> varDecTokens(Token type, String identifier) {
    return varDecTokens(type, Collections.singletonList(identifier));
  }

  public static List<Token> varDecTokens(Token type, List<String> identifiers) {
    List<Token> tokens = new ArrayList<>();
    tokens.add(varToken());
    tokens.add(type);
    List<Token> joinedIdentifiers = TestUtils.joinTokens(identifiers.stream()
        .map(Token::identifierToken)
        .collect(Collectors.toList()), comma());
    tokens.addAll(joinedIdentifiers);
    tokens.add(semicolon());
    return tokens;
  }


  public static List<String> varDecTags(String type, String identifier) {
    return varDecTags(type, Collections.singletonList(identifier));
  }

  public static List<String> varDecTags(String type, List<String> identifiers) {
    List<String> tags = new ArrayList<>();
    tags.add(varTag());
    tags.add(type);
    List<String> joinedIdentifiers = TestUtils.joinTags(identifiers.stream()
        .map(XMLUtils::identifierTag)
        .collect(Collectors.toList()), commaTag());
    tags.addAll(joinedIdentifiers);
    tags.add(semicolonTag());
    return XMLUtils.encloseInTag(TagNames.varDec, tags);
  }

  private static void printXML(String s) {
    try {
      System.out.println(docToString(stringToDoc(s)));
    } catch (Exception e) {

    }
  }
  private static Document stringToDoc(String s) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    builder = factory.newDocumentBuilder();
    Document document = builder.parse(new InputSource(new StringReader(s)));
    removeNodesEmptyOrWhiteSpace(document);
    return document;
  }

  private static final int INDENT = 2;
  private static final String XML_OUTPUT_VALUE = "yes";
  private static String docToString(Document document) throws Exception {
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute(INDENT_NUMBER, INDENT);
    Transformer transformer;
    transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, XML_OUTPUT_VALUE);
    transformer.setOutputProperty(OutputKeys.INDENT, XML_OUTPUT_VALUE);
    transformer.setOutputProperty(OutputKeys.STANDALONE, XML_OUTPUT_VALUE);

    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(document), new StreamResult(writer));
    return writer.getBuffer().toString();
  }

  private static void removeNodesEmptyOrWhiteSpace(Node node) {
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
