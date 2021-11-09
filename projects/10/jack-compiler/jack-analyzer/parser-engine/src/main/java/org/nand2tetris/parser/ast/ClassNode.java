package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.*;

import java.util.Arrays;
import org.nand2tetris.tokenizer.Token;

public class ClassNode extends AbstractNode {

  private Token className;

  public void setClassName(Token name) {
    className = name;
  }

  @Override
  public String toXMLString() {
    return concat(Arrays.asList(
        openTag("class"),
        classTag(),
        formatTag(className),
        openBraceTag(),
        closeBraceTag(),
        closeTag("class")));
  }
}
