package org.nand2tetris.parser.ast;

import java.util.List;
import org.nand2tetris.parser.utils.TagNames;

public class ExpressionListNode extends AbstractNode {

  @Override
  protected String parentTag() {
    return TagNames.expressionListTag;
  }

  @Override
  protected List<String> childrenTags() {
    return super.childrenTags();
  }
}
