package org.nand2tetris.parser;

import static com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl.INDENT_NUMBER;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

public class XmlCompareTest {


  @Test
  public void compare() throws Exception {

    URL url2 = getClass().getResource("test.xml");
    File file = new File(url2.getFile());

    String actual = "<struct><int>3</int><boolean>false</boolean></struct>";
    Source actualSource = Input.fromString(actual).build();
    Source expectedSource = Input.fromFile(file).build();
    Diff myDiff = DiffBuilder.compare(expectedSource)
        .withTest(actualSource)
        .normalizeWhitespace()
        .build();

    Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
  }

  @Test
  public void compare2() throws Exception {
    URL url2 = getClass().getResource("test-2.xml");
    File file = new File(url2.getFile());

    String actual = "<struct><int/></struct>";
    Source actualSource = Input.fromString(actual).build();
    Source expectedSource = Input.fromFile(file).build();
    Diff myDiff = DiffBuilder.compare(expectedSource)
        .withTest(actualSource)
        .normalizeWhitespace()
        .build();

    Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
  }

  @Test
  public void stringToDocument() throws Exception {
    String actual = "<struct>      <int> 3 </int><boolean> false </boolean></struct>";

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document doc;
    builder = factory.newDocumentBuilder();

    doc = builder.parse(new InputSource(new StringReader(actual)));

    removeNodesEmptyOrWhiteSpace(doc);

    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute(INDENT_NUMBER, 15);
    Transformer transformer;
    transformer = tf.newTransformer();
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

    StringWriter writer = new StringWriter();

    //transform document to string
    transformer.transform(new DOMSource(doc), new StreamResult(writer));

    String xmlString = writer.getBuffer().toString();
    System.out.println(xmlString);
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
