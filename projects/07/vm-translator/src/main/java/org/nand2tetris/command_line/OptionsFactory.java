package org.nand2tetris.command_line;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OptionsFactory {

  public static Options defaultOptions() {

    // GET DOCUMENT
    InputStream is = OptionsFactory.class.getClassLoader().getResourceAsStream("options-definitions.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder;
    try {
      dBuilder = dbFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException("cannot create document builder");
    }
    Document doc;
    try {
      doc = dBuilder.parse(is);
    } catch (Exception e) {
      throw new RuntimeException("cannot parse input stream");
    }

    String[] tagNames = {"name", "short", "long", "description"};

    // GET OPTION TAGS
    NodeList optionList = doc.getElementsByTagName("option");

    for (int i = 0; i < optionList.getLength() ; i++) {
      Node option = optionList.item(i);
      // PRINT OPTION CHILDREN
      if (option.getNodeType() == Node.ELEMENT_NODE) {
        Element optionElt = (Element) option;
        for (String tagName : tagNames) {
          NodeList list = optionElt.getElementsByTagName(tagName);
          if (list != null && list.getLength() > 0) {
            String tagValue = list.item(0).getTextContent();
            System.out.printf("%-10s %s%n", tagName, tagValue);
          }
        }
      }
      System.out.println();
    }

    Options options = new Options();

    Option verbose = Option.builder()
        .optionName("verbose")
        .shortOpt("v")
        .longOpt("verbose")
        .description("print messages during translation")
        .build();

    options.addOption(verbose);

    Option comment = Option.builder()
        .optionName("comment")
        .shortOpt("c")
        .longOpt("comment")
        .description("print comments in result ASM file")
        .build();

    options.addOption(comment);

    return options;
  }

}
