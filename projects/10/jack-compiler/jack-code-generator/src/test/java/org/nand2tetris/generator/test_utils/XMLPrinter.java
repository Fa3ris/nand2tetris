package org.nand2tetris.generator.test_utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLPrinter {

  private static final int INDENT = 2;
  private static final String indentNumberPropertyName = "indent-number";
  private static final String XML_OUTPUT_VALUE = "yes";

  public static void printXml(Path path, String xmlString) {
    try (Writer w =  Files.newBufferedWriter(path)) {
      w.write(prettyFormatXml(xmlString));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private static String prettyFormatXml(String s) throws Exception {
    return docToString(stringToDoc(s));
  }

  private static Document stringToDoc(String s) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    builder = factory.newDocumentBuilder();
    Document document;
    try {
      document = builder.parse(new InputSource(new StringReader(s)));
    } catch (Exception e) {
      System.out.println(s);
      throw e;
    }
    addLFPlusPaddingToEmptyNodes(document);
    return document;
  }

  private static String docToString(Document document) throws Exception {
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute(indentNumberPropertyName, INDENT);
    Transformer transformer;
    transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, XML_OUTPUT_VALUE);
    transformer.setOutputProperty(OutputKeys.INDENT, XML_OUTPUT_VALUE);
    transformer.setOutputProperty(OutputKeys.STANDALONE, XML_OUTPUT_VALUE);
    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(document), new StreamResult(writer));
    return writer.getBuffer().toString();
  }

  /**
   * add empty textNode to print empty xml element on 2 lines
   * ex:
   * <p>
   * {@code <parameterList></parameterList>}
   * </p>
   * is rendered as
   * <pre>
   * {@code
   *  <parameterList>
   *  </parameterList>
   * }</pre>
   *
   * and indentation is respected
   */
  private static void addLFPlusPaddingToEmptyNodes(Node node) {
    NodeList list = node.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      addLFPlusPaddingToEmptyNodes(list.item(i));
    }
    boolean emptyElement = node.getNodeType() == Node.ELEMENT_NODE
        && node.getChildNodes().getLength() == 0;
    if (emptyElement) {
      int level = 0;
      Node parent = node.getParentNode();
      while (!(parent instanceof Document)) { // stop when reach root document
        level++;
        parent = parent.getParentNode();
      }
      String leftPadding = String.format("%" + (level * INDENT) + "s", "");
      node.appendChild(node.getOwnerDocument().createTextNode("\n" + leftPadding));
    }
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
