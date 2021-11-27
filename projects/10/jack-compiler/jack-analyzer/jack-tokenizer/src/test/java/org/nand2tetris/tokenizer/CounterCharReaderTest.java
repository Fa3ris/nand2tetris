package org.nand2tetris.tokenizer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CounterCharReaderTest {

  private CounterCharReader reader;

  @Rule
  public TemporaryFolder folder= new TemporaryFolder();

  @Before
  public void setup() {
    reader = createReader();
  }

  private final int firstLineLineFeed = 45;
  private final int secondLineFirstChar = 46;
  private final int secondLineLineFeed = 98;
  @Test
  public void LineNumberStartAt0() throws Exception {
    assertEquals(0, reader.getLineNumber());
  }

  @Test
  public void LineNumberFirstLine() throws Exception {
    advanceNTimes(30);
    assertEquals(0, reader.getLineNumber());
  }

  @Test
  public void LineNumberSecondLine() throws Exception {
    advanceNTimes(secondLineFirstChar);
    assertEquals(1, reader.getLineNumber());
  }

  @Test
  public void LineNumberThirdLine() throws Exception {
    advanceNTimes(secondLineLineFeed); // line feed of second line
    assertEquals(2, reader.getLineNumber());
  }

  @Test
  public void LineNumberEOF() throws Exception {
    toEOF();
    assertEquals(3, reader.getLineNumber());
  }

  @Test
  public void columnNumberStartsAt0() throws Exception {
    assertEquals(0, reader.getColumnNumber());
  }

  @Test
  public void columnNumberRead1() throws Exception {
    advanceNTimes(1);
    assertEquals(1, reader.getColumnNumber());
  }

  @Test
  public void columnNumberLine1() throws Exception {
    advanceNTimes(29);
    assertEquals(29, reader.getColumnNumber());
  }

  @Test
  public void columnNumberResetOnNewLine() throws Exception {
    advanceNTimes(firstLineLineFeed);
    assertEquals(0, reader.getColumnNumber());
  }

  @Test
  public void columnNumberSecondLine() throws Exception {
    advanceNTimes(secondLineFirstChar);
    assertEquals(1, reader.getColumnNumber());
  }

  @Test
  public void columnNumberEOF() throws Exception {
    String content = "abc";
    Path path = writeToTempFile(content);
    reader = new CounterCharReader(new FileCharReader(path));
    toEOF();
    assertEquals(content.length(), reader.getColumnNumber());
  }

  private Path writeToTempFile(String content) throws Exception {
    File createdFile= folder.newFile("temp.txt");
    Path path = createdFile.toPath();
    Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    return path;
  }

  private void toEOF() {
    do {
      reader.advance();
    } while (!reader.isEOF());
  }
  private CounterCharReader createReader() {
    return createReader("test-line-count.txt");
  }
  private CounterCharReader createReader(String fileName) {
    File file  = new File( getClass().getResource(fileName).getFile());
    return new CounterCharReader(new FileCharReader(file.toPath()));
  }

  private StringBuilder advanceNTimes(int n) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      reader.advance();
      sb.append(reader.peekChar());
    }
    return sb;
  }

}