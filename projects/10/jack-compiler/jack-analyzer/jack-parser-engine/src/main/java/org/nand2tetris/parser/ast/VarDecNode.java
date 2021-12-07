package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;
import static org.nand2tetris.parser.utils.XMLUtils.varTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.nand2tetris.parser.utils.Joiner;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;

public class VarDecNode extends AbstractNode {

  private Token type;

  private final List<Token> varNames = new ArrayList<>();

  @Override
  protected String parentTag() {
    return TagNames.varDec;
  }

  @Override
  protected List<String> childrenTags() {
    Joiner<String> joiner = new Joiner<>(commaTag());
    List<String> joined = joiner.join(varNames.stream()
        .map(XMLUtils::formatTag).collect(Collectors.toList()));
    List<String> tags = new ArrayList<>();
    tags.add(varTag());
    tags.add(formatTag(type));
    tags.addAll(joined);
    tags.add(semicolonTag());
    return tags;
  }

  public void setType(Token token) {
    type = token;
  }

  public void addVarNames(List<Token> tokens) {
    varNames.addAll(tokens);
  }

  @Override
  public void accept(NodeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return String.format("%s %s", type.getLexeme(), varNames.stream().map(Token::getLexeme).collect(
        Collectors.joining(",")));
  }

  public String getType() {
    return type.getLexeme();
  }

  public List<String> getNames() {
    return varNames.stream().map(Token::getLexeme).collect(Collectors.toList());
  }
}
