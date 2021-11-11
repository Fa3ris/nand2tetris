package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.encloseInTag;

import java.util.Collections;
import java.util.List;

public abstract class AbstractNode implements Node {

  @Override
  public String toXMLString() {
    List<String> enclosed = encloseInTag(parentTag(), childrenTags());
    return concat(enclosed);
  }

  protected String parentTag() { return "";}
  protected List<String> childrenTags() { return Collections.emptyList(); }

}
