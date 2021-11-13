package org.nand2tetris.parser.ast;

import static org.nand2tetris.parser.utils.XMLUtils.commaTag;
import static org.nand2tetris.parser.utils.XMLUtils.formatTag;
import static org.nand2tetris.parser.utils.XMLUtils.semicolonTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.nand2tetris.parser.utils.Joiner;
import org.nand2tetris.parser.utils.TagNames;
import org.nand2tetris.parser.utils.XMLUtils;
import org.nand2tetris.tokenizer.Token;

public class ClassVarDecNode extends AbstractNode {

  private Token scope;
  private Token type;
  private final List<Token> varNames = new ArrayList<>();

  public void setScope(Token scope) {
    this.scope = scope;
  }

  public void setType(Token type) {
    this.type = type;
  }

  @Override
  protected String parentTag() {
    return TagNames.classVarDec;
  }

  @Override
  protected List<String> childrenTags() {
    Joiner<String> joiner = new Joiner<>(commaTag());
    List<String> joined = joiner.join(varNames.stream()
        .map(XMLUtils::formatTag).collect(Collectors.toList()));
    List<String> tags = new ArrayList<>();
    tags.add(formatTag(scope));
    tags.add(formatTag(type));
    tags.addAll(joined);
    tags.add(semicolonTag());
    return tags;
  }

  public void addVarNames(List<Token> tokens) {
    varNames.addAll(tokens);
  }
}
