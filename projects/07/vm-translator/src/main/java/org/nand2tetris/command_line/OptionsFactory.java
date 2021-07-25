package org.nand2tetris.command_line;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class OptionsFactory {

  private final static String xmlPath = "options-definitions.xml";

  public Options getDefaultOptions() {
    Document document = defaultDocument();
    NodeList optionTags = document.getElementsByTagName("option");
    List<Option> optionList = optionList(optionTags);
    Options options = new Options();
    for (Option option : optionList) {
      options.addOption(option);
    }
    return options;
  }

  private List<Option> optionList(NodeList optionTags) {
    List<Option> optionList = new LinkedList<>();
    for (int i = 0; i < optionTags.getLength() ; i++) {
      Node optionTag = optionTags.item(i);
      if (optionTag.getNodeType() == Node.ELEMENT_NODE) {
        Element optionElt = (Element) optionTag;
        Option option = convertOptionElement(optionElt);
        optionList.add(option);
      }
    }
    return optionList;
  }

  private Option convertOptionElement(Element optionElt) {
    Option.Builder builder = Option.builder();
    String name = childElementTextContent(optionElt, "name");
    if (validTextContent(name)) {
      builder.optionName(name);
    }
    String shortOpt = childElementTextContent(optionElt, "short");
    if (validTextContent(shortOpt)) {
      builder.shortOpt(shortOpt);
    }
    String longOpt = childElementTextContent(optionElt, "long");
    if (validTextContent(longOpt)) {
      builder.longOpt(longOpt);
    }
    String description = childElementTextContent(optionElt, "description");
    if (validTextContent(description)) {
      builder.description(description);
    }
    String argNumber = childElementTextContent(optionElt, "argNumber");
    if (validTextContent(argNumber)) {
      int args = Integer.parseInt(argNumber);
      builder.argNumber(args);
    }
    return builder.build();
  }

  private boolean validTextContent(String content) {
    return content != null && !content.isEmpty();
  }

  private String childElementTextContent(Element element, String tagName) {
    NodeList list = element.getElementsByTagName(tagName);
    if (list != null && list.getLength() > 0) {
      return list.item(0).getTextContent();
    }
    return null;
  }

  private Document defaultDocument() {
    try (InputStream is = OptionsFactory.class.getClassLoader().getResourceAsStream(xmlPath)) {
      DocumentBuilder documentBuilder = documentBuilder();
      return documentBuilder.parse(is);
    } catch (IOException e) {
      throw new RuntimeException("io error");
    } catch (SAXException e) {
      throw new RuntimeException("cannot parse input stream");
    }
  }

  private DocumentBuilder documentBuilder() {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    try {
      return dbFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException("cannot create document builder");
    }
  }

}
