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
  private final List<Node> classVarDecs = new ArrayList<>();
  private final List<Node> subRoutineDecs = new ArrayList<>();

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

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visit(this);
    for (Node classVarDec : classVarDecs) {
      classVarDec.accept(visitor);
    }
    for (Node subroutineDec : subRoutineDecs) {
      subroutineDec.accept(visitor);
    }
  }

  public String getClassName() {
    return className.getLexeme();
  }
}
