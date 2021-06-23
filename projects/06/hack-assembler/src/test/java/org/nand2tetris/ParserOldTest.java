package org.nand2tetris;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ParserOldTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private final static String ADD = "Add.asm";

  private ParserOld parserOld;

  private Path addPath;

  @Before
  public void setUp() throws Exception {
    addPath = getPathFromResource(ADD);
    parserOld = new ParserOld();
  }

  @After
  public void tearDown() throws Exception {
    parserOld.clean();
  }

  @Test
  public void getLines() throws Exception {
    parserOld.parse(addPath);
    List<String> lines = parserOld.getLines();

    assertEquals(13, lines.size());

    String firstLine = "// This file is part of www.nand2tetris.org";
    String actualFirst = lines.get(0);
    assertEquals(firstLine, actualFirst);

    String lastLine = "M=D";
    String actualLast = lines.get(lines.size() - 1);
    assertEquals(lastLine, actualLast);
  }

  @Test
  public void hasMoreCommand() throws Exception {
    parserOld.parse(addPath);
    assertTrue(parserOld.hasMoreCommand());
    assertEquals("@2", parserOld.getCurrentLine());
  }

  @Test
  public void aInstruction() throws Exception {
    final File tempFile = temporaryFolder.newFile("a.txt");
    Path path = tempFile.toPath();
    Files.write(path, Collections.singleton("@2"));
    parserOld.parse(path);
    assertEquals("@2", parserOld.getCurrentLine());
  }

  @Test
  public void lInstruction() throws Exception {
    final File tempFile = temporaryFolder.newFile("l.txt");
    Path path = tempFile.toPath();
    Files.write(path, Collections.singleton("(LABEL)"));
    parserOld.parse(path);
    assertEquals("(LABEL)", parserOld.getCurrentLine());
  }

  @Test
  public void cInstruction() throws Exception {
    final File tempFile = temporaryFolder.newFile("c.txt");
    Path path = tempFile.toPath();
    Files.write(path, Collections.singleton("D=A"));
    parserOld.parse(path);
    assertEquals("D=A", parserOld.getCurrentLine());
  }

  private Path getPathFromResource(String resourceName) throws Exception {
    URL addUrl = HackAssemblerTest.class.getClassLoader().getResource(resourceName);
    if (addUrl == null) {
      String msg = String.format("file %s not found", ADD);
      throw new RuntimeException(msg);
    }
    return Paths.get(addUrl.toURI());
  }
}