package org.nand2tetris.parser.utils;

import java.util.ArrayList;
import java.util.List;

public class Joiner<T> {

  private final T delimiter;

  public Joiner(T delimiter) {
    this.delimiter = delimiter;
  }

  public List<T> join(List<T> list, T delimiter) {
    List<T> joined = new ArrayList<>();
    for (T el : list) {
      if (!joined.isEmpty()) {
        joined.add(delimiter);
      }
      joined.add(el);
    }
    return joined;
  }

  public List<T> join(List<T> list) {
    List<T> joined = new ArrayList<>();
    for (T el : list) {
      if (!joined.isEmpty()) {
        joined.add(delimiter);
      }
      joined.add(el);
    }
    return joined;
  }

}
