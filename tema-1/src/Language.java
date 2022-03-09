public class Language {
  private final int id;
  private final String code;
  private final String name;

  public Language(int id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }
}
