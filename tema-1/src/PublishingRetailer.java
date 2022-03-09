import java.util.HashMap;

public class PublishingRetailer {
  private final int id;
  private final String name;
  private final HashMap<Integer, IPublishingArtifact> publishingArtifacts;
  private final HashMap<Integer, Country> countries;

  public PublishingRetailer(int id, String name) {
    this.id = id;
    this.name = name;
    this.publishingArtifacts = new HashMap<>();
    this.countries = new HashMap<>();
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public HashMap<Integer, IPublishingArtifact> getPublishingArtifacts() {
    return this.publishingArtifacts;
  }

  public HashMap<Integer, Country> getCountries() {
    return this.countries;
  }

  public void addCountry(Country country) {
    this.countries.put(country.getId(), country);
  }

  public void addArtifact(IPublishingArtifact artifact) {
    if (artifact instanceof Book book) {
      this.publishingArtifacts.put(book.getId(), book);
    } else if (artifact instanceof EditorialGroup editorialGroup) {
      this.publishingArtifacts.put(editorialGroup.getId(), editorialGroup);
    } else if (artifact instanceof PublishingBrand publishingBrand) {
      this.publishingArtifacts.put(publishingBrand.getId(), publishingBrand);
    }
  }
}
