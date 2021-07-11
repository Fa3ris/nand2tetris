package org.nand2tetris;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BranchingOpGenerator extends AbstractGenerator {

  public List<String> label(Token labelToken) {
    String label = labelToken.getLexeme();
    return Collections.unmodifiableList(Arrays.asList(
        lineComment("label " + label),
        defineLabel(label)
    ));
  }
}
