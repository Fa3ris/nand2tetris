package org.nand2tetris.parser;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CInstruction implements Node {

  private final Factor dest;
  private final Node comp;
  private final Factor jmp;

  @Override
  public List<Node> getChildren() {
    return Arrays.asList(dest, comp, jmp);
  }

  @Override
  public String toString() {
    return "CInstruction{" +
        "dest=" + dest +
        ", comp=" + comp +
        ", jmp=" + jmp +
        '}';
  }
}
