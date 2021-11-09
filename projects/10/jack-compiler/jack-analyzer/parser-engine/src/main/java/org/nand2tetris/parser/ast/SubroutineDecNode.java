package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.closeBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.closeTag;
import static org.nand2tetris.parser.utils.XMLUtils.concat;
import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.openBraceTag;
import static org.nand2tetris.parser.utils.XMLUtils.openParenTag;
import static org.nand2tetris.parser.utils.XMLUtils.openTag;

import java.util.Arrays;
import java.util.List;
import org.nand2tetris.tokenizer.Token;

public class SubroutineDecNode extends AbstractNode {
  private Token routineType, returnType, routineName;

  private Node parameterListNode, subroutineBodyNode;

  private static final String parentTag = "subroutineDec";

  @Override
  protected String parentTag() {
    return parentTag;
  }

  @Override
  protected List<String> childrenTags() {
    return Arrays.asList(
        formatTag(routineType),
        formatTag(returnType),
        formatTag(routineName),
        openParenTag(),
        parameterListNode.toXMLString(),
        closeParenTag(),
        openBraceTag(),
        subroutineBodyNode.toXMLString(),
        closeBraceTag());
  }

  public void setRoutineType(Token routineType) {
    this.routineType = routineType;
  }

  public void setReturnType(Token returnType) {
    this.returnType = returnType;
  }

  public void setRoutineName(Token routineName) {
    this.routineName = routineName;
  }

  public void setParameterListNode(Node parameterListNode) {
    this.parameterListNode = parameterListNode;
  }

  public void setSubroutineBodyNode(Node subroutineBodyNode) {
    this.subroutineBodyNode = subroutineBodyNode;
  }
}
