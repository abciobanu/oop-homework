public class Country {
  private final int id;
  private final String countryCode;

  public Country(int id, String countryCode) {
    this.id = id;
    this.countryCode = countryCode;
  }

  public int getId() {
    return this.id;
  }

  public String getCountryCode() {
    return this.countryCode;
  }
}
