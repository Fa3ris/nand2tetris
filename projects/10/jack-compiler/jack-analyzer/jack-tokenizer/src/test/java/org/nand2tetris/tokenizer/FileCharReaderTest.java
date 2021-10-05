package org.nand2tetris.tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileCharReaderTest {

  @Rule
  public TemporaryFolder folder= new TemporaryFolder();


  private Path path;

  @Before
  public void setup() throws Exception {
    File createdFile= folder.newFile("myFile.txt");
    path = createdFile.toPath();
  }

  @Test
  public void shouldHaveCharToRead() throws Exception {
    Files.write(path, "Hello".getBytes(StandardCharsets.UTF_8));
    CharReader reader = new FileCharReader(path);
    assertFalse(reader.isEOF());
  }

  @Test
  public void shouldNotHaveCharToReadIfFileIsEmpty() throws Exception {
    CharReader reader = new FileCharReader(path);
    assertTrue(reader.isEOF());
  }

  @Test
  public void shouldNotHaveCharToReadIfNoFile() throws Exception {
    CharReader reader = new FileCharReader(null);
    assertTrue(reader.isEOF());
  }

  @Test
  public void shouldReturnFirstChar() throws Exception {
    Files.write(path, "H".getBytes(StandardCharsets.UTF_8));
    CharReader reader = new FileCharReader(path);
    reader.advance();
    assertEquals('H', reader.peekChar());
  }
  
  @Test
  public void shouldHaveExhaustedChars() throws Exception {
    Files.write(path, "H".getBytes(StandardCharsets.UTF_8));
    CharReader reader = new FileCharReader(path);
    reader.advance();
    reader.advance();
    assertTrue(reader.isEOF());
  }
  
  @Test
  public void shouldReturnAllChars() throws Exception {
    Files.write(path, "Hello".getBytes(StandardCharsets.UTF_8));
    CharReader reader = new FileCharReader(path);
    StringBuilder sb = new StringBuilder();
    reader.advance();
    sb.append(reader.peekChar());
    reader.advance();
    sb.append(reader.peekChar());
    reader.advance();
    sb.append(reader.peekChar());
    reader.advance();
    sb.append(reader.peekChar());
    reader.advance();
    sb.append(reader.peekChar());
    assertEquals("Hello", sb.toString());
  }

}