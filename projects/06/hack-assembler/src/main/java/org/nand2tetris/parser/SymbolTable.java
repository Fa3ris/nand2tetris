package org.nand2tetris.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SymbolTable {

  private Map<String, Integer> table = new HashMap<>();

  private Integer nextAddress = 1;

//  private Map<String, List<AInstruction>> observers = new HashMap<>();

  // when finished scan
  // go overs unknown labels in order and notify with inc nextAddress
  private boolean finishedScan = false;

//  private void setFinishedScan() {
//    finishedScan = true;
//  }

  // remove unknown labels when found
  private List<String> unknownLabel = new LinkedList<>();

//  private void init() {
//    // defined labels for each put (label, nextAddres) nextAddress++
//  }

//  public void register(AInstruction aInstruction) {
//
//  }

  public Integer lookup(String value) {
    return null;
  }


}
