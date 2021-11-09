package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.leafTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

import java.util.Arrays;

public class ParameterListNode extends AbstractNode {


  @Override
  public String toXMLString() {
    return concat(Arrays.asList(
        openTag("parameterList"),
        closeTag("parameterList")
    ));
  }
}
