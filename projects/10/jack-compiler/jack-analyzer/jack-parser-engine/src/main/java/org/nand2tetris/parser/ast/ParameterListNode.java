package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.commaTag;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.nand2tetris.parser.utils.Joiner;
import org.nand2tetris.parser.utils.TagNames;

public class ParameterListNode extends AbstractNode {

  private final List<Node> args = new LinkedList<>();


  @Override
  protected String parentTag() {
    return TagNames.parameterList;
  }

  @Override
  protected List<String> childrenTags() {
    List<String> argTags = args.stream().map(Node::toXMLString).collect(Collectors.toList());
    return new Joiner<>(commaTag()).join(argTags);
  }

  public void addArg(Node arg) {
    args.add(arg);
  }

  @Override
  public void accept(NodeVisitor visitor) {
    for (Node arg : args) {
      arg.accept(visitor);
    }
  }
}
