package kuznetsov.analyzer;

import static kuznetsov.analyzer.service.Analyzer.analyze;

import java.util.List;
import kuznetsov.analyzer.domain.FoundFile;

public class Application {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.exit(0);
    }

    final String patternsFileName = args[1];
    final String folderName = "D:\\YandexDisk\\repos\\zz";

    List<FoundFile> results = analyze(patternsFileName, folderName);

    for (FoundFile r : results) {
      System.out.printf("%s: %s\n",
          r.getFileName(),
          r.getTypeName() == null ? "Unknown file type" : r.getTypeName()
      );
    }
  }

}
