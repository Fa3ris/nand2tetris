package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.classTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;

import java.util.ArrayList;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;

public class ClassNode extends AbstractNode {

  private Token className;
  public final List<Node> classVarDecs = new ArrayList<>();
  public final List<Node> subRoutineDecs = new ArrayList<>();

  public void setClassName(Token name) {
    className = name;
  }

  @Override
  protected String parentTag() {
    return TagNames.classTag;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> tags = new ArrayList<>();
    tags.add(classTag());
    tags.add(formatTag(className));
    tags.add(openBraceTag());
    tags.addAll(XMLUtils.formatNodes(classVarDecs));
    tags.addAll(XMLUtils.formatNodes(subRoutineDecs));
    tags.add(closeBraceTag());
    return tags;
  }

  public void addClassVarDec(Node node) {
    classVarDecs.add(node);
  }

  public void addSubroutineDec(Node node) {
    subRoutineDecs.add(node);
  }
}
