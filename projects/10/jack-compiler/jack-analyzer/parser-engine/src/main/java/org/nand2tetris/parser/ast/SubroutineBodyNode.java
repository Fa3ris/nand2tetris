package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.parser.utils.TagNames;

public class SubroutineBodyNode extends AbstractNode {

  @Override
  protected String parentTag() {
    return TagNames.subroutineBody;
  }

  @Override
  protected List<String> childrenTags() {
    return Arrays.asList(
        openBraceTag(),
        closeBraceTag()
    );
  }

}
