package kuznetsov.analyzer.service;

import static kuznetsov.analyzer.service.KmpAlg.searchOccurrencesByKmpAlg;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import kuznetsov.analyzer.domain.FileType;
import kuznetsov.analyzer.domain.FoundFile;

public class Analyzer {

  public static List<FoundFile> analyze(final String patternsFileName,
                                        final String folderName) {

    final File[] files = new File(folderName).listFiles();
    final ExecutorService executor = Executors.newCachedThreadPool();
    final List<Future<FoundFile>> futures = new LinkedList<>();
    FileType[] fileTypesPrioritySorted = parseFileTypesPrioritySorted(patternsFileName);

    for (File file : files) {
      futures.add(
          executor.submit(() -> findFileType(file, fileTypesPrioritySorted))
      );
    }

    List<FoundFile> result = convertFuturesListToList(futures);
    executor.shutdown();
    return result;
  }

  private static FoundFile findFileType(File file, final FileType[] fileTypes) {
    FileType foundFileType = null;

    try (InputStream input = new BufferedInputStream(
        new FileInputStream(file)
    )) {

      String fileContent = new String(input.readAllBytes());

      boolean isTypeFound;
      for (FileType type : fileTypes) {
        isTypeFound = searchOccurrencesByKmpAlg(fileContent, type.getPattern()).size() > 0;
        if (isTypeFound) {
          foundFileType = type;
          break;
        }
      }

    } catch (IOException exc) {
      foundFileType = null;
      exc.printStackTrace();
    }

    return new FoundFile(file.getName(), foundFileType);
  }

  private static FileType[] parseFileTypesPrioritySorted(final String patternsFileName) {

    FileType[] fileTypes;

    try (InputStream input = new BufferedInputStream(
        new FileInputStream(patternsFileName)
    )) {

      final String[] fileTypesLines = new String(input.readAllBytes()).split("\n");
      Arrays.sort(fileTypesLines, Collections.reverseOrder());

      fileTypes = new FileType[fileTypesLines.length];
      for (int i = 0; i < fileTypes.length; i++) {
        fileTypes[i] = parseFileType(fileTypesLines[i]);
      }

    } catch (IOException exc) {
      fileTypes = null;
      exc.printStackTrace();
    }

    return fileTypes;
  }

  private static FileType parseFileType(final String line) {
    final String[] fileTypeFields = line.split(";");
    fileTypeFields[1] = fileTypeFields[1].substring(1, fileTypeFields[1].length() - 1);
    fileTypeFields[2] = fileTypeFields[2].substring(1, fileTypeFields[2].length() - 1);

    return new FileType(Integer.parseInt(fileTypeFields[0]), fileTypeFields[1], fileTypeFields[2]);
  }

  private static <T> List<T> convertFuturesListToList(List<Future<T>> futures) {
    List<T> list = new ArrayList<>(futures.size());

    for (Future<T> future : futures) {
      try {
        list.add(future.get());
      } catch (InterruptedException | ExecutionException exc) {
        exc.printStackTrace();
      }
    }

    return list;
  }
}
