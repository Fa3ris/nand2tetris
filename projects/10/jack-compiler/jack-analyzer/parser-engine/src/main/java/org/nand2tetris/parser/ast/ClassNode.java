package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.*;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.tokenizer.Token;

public class ClassNode extends AbstractNode {

  private Token className;

  public void setClassName(Token name) {
    className = name;
  }

  @Override
  protected String parentTag() {
    return TagNames.classTag;
  }

  @Override
  protected List<String> childrenTags() {
    return Arrays.asList(
        classTag(),
        formatTag(className),
        openBraceTag(),
        closeBraceTag());
  }
}
