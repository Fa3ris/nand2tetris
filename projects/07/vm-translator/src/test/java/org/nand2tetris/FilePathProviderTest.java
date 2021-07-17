package org.nand2tetris;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FilePathProviderTest {

  @Rule
  public TemporaryFolder folder= new TemporaryFolder();

  private final FilePathProvider filePathProvider = new FilePathProvider();

  private final String ASM_EXT = ".asm";
  private final String VM_EXT = ".vm";

  @Test
  public void getOutputFilePathFromFolder() throws Exception {
    Path rootPath = folder.getRoot().toPath();
    Path expectedPath = rootPath.resolve(rootPath.getFileName() + ASM_EXT);

    filePathProvider.setInputPath(rootPath);
    Path actualPath = filePathProvider.getOutputFilePath();

    assertEquals(expectedPath, actualPath);
  }

  @Test
  public void getSrcFilePathsFromFolder() throws Exception {
    Path rootPath = folder.getRoot().toPath();

    Path fooPath = folder.newFile("foo.vm").toPath();
    Path buzzPath = folder.newFile("buzz.vm").toPath();

    folder.newFile("bye.txt");
    folder.newFile("bar.out");
    folder.newFile("baz.tst");
    folder.newFile("hello.cmp");

    filePathProvider.setInputPath(rootPath);
    List<Path> actualSrcFilePaths = filePathProvider.getSourceFilePaths();

    assertEquals(2, actualSrcFilePaths.size());
    Set<Path> actualPathSet = new HashSet<>(actualSrcFilePaths);
    Set<Path> expectedPathSet = new HashSet<>(Arrays.asList(fooPath, buzzPath));
    assertEquals(expectedPathSet, actualPathSet);

  }

  @Test
  public void getOutputFilePathFromSingleVMFile() throws Exception {
    String inputBasename = "foo";
    String inputFileName = inputBasename + VM_EXT;
    Path vmPath = folder.newFile(inputFileName).toPath();
    String expectedOutputFileName = inputBasename + ASM_EXT;
    Path expectedPath = vmPath.getParent().resolve(expectedOutputFileName);

    filePathProvider.setInputPath(vmPath);
    Path actualPath = filePathProvider.getOutputFilePath();

    assertEquals(expectedPath, actualPath);
  }
  
  @Test
  public void getSrcFilePathFromSingleVMFile() throws Exception {
    String inputBasename = "foo";
    String inputFileName = inputBasename + VM_EXT;
    Path vmPath = folder.newFile(inputFileName).toPath();

    filePathProvider.setInputPath(vmPath);
    List<Path> actualSrcFilePath = filePathProvider.getSourceFilePaths();

    assertEquals(1, actualSrcFilePath.size());
    assertEquals(vmPath, actualSrcFilePath.get(0));
  }

}