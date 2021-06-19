package org.nand2tetris.parser;

import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.nand2tetris.lexer.Token;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Factor implements Node {

  @Getter
  private final Token token;

  @Override
  public List<Node> getChildren() {
    return Collections.emptyList();
  }

  @Override
  public String toString() {
    return "Factor{" +
        "token=" + token +
        '}';
  }
}
