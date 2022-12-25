package kuznetsov.analyzer.domain;

public class FileType {

  private final int priority;
  private final String pattern;
  private final String typeName;

  public FileType(int priority, String pattern, String typeName) {
    this.priority = priority;
    this.pattern = pattern;
    this.typeName = typeName;
  }

  public int getPriority() {
    return priority;
  }

  public String getPattern() {
    return pattern;
  }

  public String getTypeName() {
    return typeName;
  }

}
