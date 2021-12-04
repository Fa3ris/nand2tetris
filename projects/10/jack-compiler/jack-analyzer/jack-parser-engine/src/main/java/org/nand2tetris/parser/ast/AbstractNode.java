package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.encloseInTag;

import java.util.Collections;
import java.util.List;

public abstract class AbstractNode implements Node {

  @Override
  public String toXMLString() {
    if (parentTag().isEmpty()) {
      return concat(childrenTags());
    }
    return concat(encloseInTag(parentTag(), childrenTags()));
  }

  @Override
  public void accept(NodeVisitor visitor) {
    
  }

  protected String parentTag() { return "";}
  protected List<String> childrenTags() { return Collections.emptyList(); }

}
