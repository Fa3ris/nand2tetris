package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

import java.util.Arrays;
import org.nand2tetris.parser.utils.TagNames;

public class SubroutineBodyNode extends AbstractNode {

  @Override
  public String toXMLString() {
    return concat(Arrays.asList(
        openTag(TagNames.subroutineBody),
        closeTag(TagNames.subroutineBody)
    ));
  }

}
