package org.nand2tetris;

import static org.junit.Assert.*;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class VMTranslatorTest {

  private VMTranslator translator;

  @Before
  public void setUp() throws Exception {
    translator = new VMTranslator();
  }

  @Test
  public void add() throws Exception {
    translate("arithmetic/add.vm");
  }

  @Test
  public void sub() throws Exception {
    translate("arithmetic/sub.vm");
  }

  @Test
  public void neg() throws Exception {
    translate("arithmetic/neg.vm");
  }

  @Test
  public void pushConstant() throws Exception {
    translate("stack/push-constant.vm");
  }

  @Test
  public void eq() throws Exception {
    translate("relational/eq.vm");
  }

  @Test
  public void lt() throws Exception {
    translate("relational/lt.vm");
  }

  @Test
  public void gt() throws Exception {
    translate("relational/gt.vm");
  }

  @Test
  public void and() throws Exception {
    translate("logical/and.vm");
  }

  @Test
  public void or() throws Exception {
    translate("logical/or.vm");
  }

  @Test
  public void not() throws Exception {
    translate("logical/not.vm");
  }

  private void translate(String pathStr) throws Exception {
    Path path = getPath(pathStr);
    translator.translate(path);
  }

  private Path getPath(String path) throws Exception {
    URL url = VMTranslatorTest.class.getClassLoader().getResource(path);
    if (url == null) {
      String msg = String.format("file %s not found", path);
      throw new RuntimeException(msg);
    }
    return Paths.get(url.toURI());
  }

}